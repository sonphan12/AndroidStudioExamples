package com.example.lap10255.examplekeystore;

import android.annotation.SuppressLint;
import android.security.KeyPairGeneratorSpec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    EditText edtAlias;
    Button btnGenerate;
    EditText edtText;
    EditText txtEncrypted;
    EditText txtDecrypted;
    ListView lvKeys;
    KeyStore keyStore;
    ArrayList<String> keyAlias;
    KeyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        addControls();
    }

    private void addControls() {
        lvKeys = findViewById(R.id.lvKeys);
        adapter = new KeyAdapter(this);
        lvKeys.setAdapter(adapter);

        edtAlias = findViewById(R.id.edtAlias);
        edtText = findViewById(R.id.edtText);
        txtEncrypted = findViewById(R.id.txtEncrypted);
        txtDecrypted = findViewById(R.id.txtDecrypted);

        btnGenerate = findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(v -> Observable.create(emitter -> {
            createNewKey();
            emitter.onNext(true);
            emitter.onComplete();
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(op -> refreshKeys(), Throwable::printStackTrace));
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();
        refreshKeys();
    }

    public void refreshKeys() {
        keyAlias = new ArrayList<>();
        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                keyAlias.add(aliases.nextElement());
            }
            Collections.sort(keyAlias, String::compareTo);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        if (adapter != null) {
            adapter.setListAlias(keyAlias);
        }
    }

    private void createNewKey() {
        String alias = edtAlias.getText().toString();

        try {
            if (!keyStore.containsAlias(alias)) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(this)
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN=Sample Name, O=Android Authority"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();

                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                generator.initialize(spec);

                KeyPair keyPair = generator.generateKeyPair();

            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    public void deleteKey(String alias) {
        try {
            keyStore.deleteEntry(alias);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public String encryptString(String alias) {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();

            // Encrypt
            String text = edtText.getText().toString();
            if (text.isEmpty()) {
                return "";
            }

            Cipher input = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            input.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, input);
            cipherOutputStream.write(text.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte[] val = outputStream.toByteArray();
            return android.util.Base64.encodeToString(val, android.util.Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException
                | NoSuchPaddingException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decryptString(String alias) {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
            RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();

            // Decrypt
            String encrypedText = txtEncrypted.getText().toString();
            if (encrypedText.isEmpty()) {
                return "";
            }

            Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            output.init(Cipher.DECRYPT_MODE, privateKey);

            CipherInputStream cipherInputStream = new CipherInputStream(new ByteArrayInputStream(
                    android.util.Base64.decode(encrypedText, android.util.Base64.DEFAULT)
            ), output);
            ArrayList<Byte> values = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte) nextByte);
            }

            byte[] bytes = new byte[values.size()];
            for (int i = 0; i < values.size(); i++) {
                bytes[i] = values.get(i);
            }

            return new String(bytes, 0, bytes.length, "UTF-8");

        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException
                | NoSuchPaddingException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}

package com.example.lap10255.exampleroom.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lap10255.exampleroom.Database.MyDb;
import com.example.lap10255.exampleroom.Model.Pet;
import com.example.lap10255.exampleroom.R;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {
    private ArrayList<Pet> listPet;
    private Context ctx;

    public PetAdapter(Context ctx) {
        super();
        this.listPet = new ArrayList<>();
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public PetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.ViewHolder holder, int position) {
        Pet pet = listPet.get(position);
        holder.txtPetName.setText(pet.getName());
        holder.txtSpecies.setText(pet.getSpecies());

        holder.itemView.setOnLongClickListener(v -> {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
            builder.setTitle("Delete pet?");
            builder.setPositiveButton("Yes", (dialog, which) -> deletePet(pet));

            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

            builder.show();
            return true;
        });
    }

    @SuppressLint("CheckResult")
    private void deletePet(Pet pet) {

        Single.create(emitter -> emitDelete(emitter, pet))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::deletePetFromUI, this::handleDeletingError);
    }

    private void emitDelete(SingleEmitter<Object> emitter, Pet pet) {
        MyDb.getDbInstance(ctx).petDAO().delete(pet);
        emitter.onSuccess(pet);
    }

    private void deletePetFromUI(Object o) {
        Pet pet = (Pet) o;
        listPet.remove(pet);
        notifyDataSetChanged();
        Toast.makeText(ctx, "Deleted", Toast.LENGTH_SHORT).show();
    }

    private void handleDeletingError(Throwable e) {
        Toast.makeText(ctx, e.toString(), Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    @Override
    public int getItemCount() {
        return listPet.size();
    }

    public void add(Pet pet) {
        listPet.add(pet);
        Collections.sort(listPet, (p1, p2) -> p1.getName().compareTo(p2.getName()));
        notifyDataSetChanged();
    }

    public void clear() {
        this.listPet = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setListPet(ArrayList<Pet> listPet) {
        this.listPet = listPet;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPetName;
        TextView txtSpecies;
        public ViewHolder(View itemView) {
            super(itemView);
            txtPetName = itemView.findViewById(R.id.txtPetName);
            txtSpecies = itemView.findViewById(R.id.txtSpecies);
        }

    }
}

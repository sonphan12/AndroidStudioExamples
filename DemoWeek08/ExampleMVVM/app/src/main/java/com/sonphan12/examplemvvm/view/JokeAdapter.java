package com.sonphan12.examplemvvm.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonphan12.examplemvvm.R;
import com.sonphan12.examplemvvm.data.model.Joke;

import java.util.ArrayList;
import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeHolder> {
    Context ctx;
    List<Joke> listJoke;

    public JokeAdapter(Context ctx) {
        this.ctx = ctx;
        listJoke = new ArrayList<>();
    }

    @NonNull
    @Override
    public JokeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_joke, parent, false);
        return new JokeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeHolder holder, int position) {
        Joke joke = listJoke.get(position);
        holder.txtJoke.setText(Html.fromHtml(joke.getJoke()));
    }

    @Override
    public int getItemCount() {
        return listJoke.size();
    }

    public void setListJoke(List<Joke> listJoke) {
        this.listJoke = listJoke;
    }

    class JokeHolder extends RecyclerView.ViewHolder {
        CardView cardJoke;
        TextView txtJoke;
        public JokeHolder(View itemView) {
            super(itemView);
            cardJoke = itemView.findViewById(R.id.cardJoke);
            txtJoke = itemView.findViewById(R.id.txtJoke);
        }
    }
}

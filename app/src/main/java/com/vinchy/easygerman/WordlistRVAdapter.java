package com.vinchy.easygerman;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordlistRVAdapter extends RecyclerView.Adapter<WordlistRVAdapter.ViewHolder> {

    private ArrayList<Bundle> words;
    private DatabaseHelper dbHelper;
    private Context context;

    public WordlistRVAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wordlist_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bundle currentWord = words.get(position);
        holder.tv_article.setText(currentWord.get("article").toString());
        holder.tv_word.setText(currentWord.get("word").toString());
        holder.tv_translation.setText(currentWord.get("translation").toString());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_word;
        private TextView tv_translation;
        private TextView tv_article;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_word = itemView.findViewById(R.id.tv_word);
            tv_translation = itemView.findViewById(R.id.tv_translation);
            tv_article = itemView.findViewById(R.id.tv_article);
        }
    }

    public void setWords(ArrayList<Bundle> words) {
        this.words = words;
        notifyDataSetChanged();
    }
}

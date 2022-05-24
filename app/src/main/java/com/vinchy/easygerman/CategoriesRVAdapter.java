package com.vinchy.easygerman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesRVAdapter extends RecyclerView.Adapter<CategoriesRVAdapter.ViewHolder> {
    private ArrayList<String> categoryNames;
    private Context context;

    public CategoriesRVAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String categoryName = categoryNames.get(position);
        holder.txtCategory.setText(categoryNames.get(position));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewCategoryActivity.class);

                // Forward the value (later may be added to a database with a new word)
                SharedPreferences sharedPreferences = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("lastCategoryName", categoryName);
                editor.apply();

                context.startActivity(intent);
                // TODO: Check whether this works
                Activity a = (Activity) context;
                a.finish();
            }
        });
    }

    // Return the number of elements in the adapter
    @Override
    public int getItemCount() {
        return categoryNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCategory;
        private RelativeLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategoryName);
            parent = itemView.findViewById(R.id.categoryParent);
        }
    }

    public void setCategoryNames(ArrayList<String> categoryNames) {
        this.categoryNames = categoryNames;

        // Refresh the recycler view
        notifyDataSetChanged();
    }
}

package rs.fon.miltek.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import rs.fon.miltek.R;
import rs.fon.miltek.home.CategoryProductsActivity;
import rs.fon.miltek.models.Category;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>{
    private List<Category> categories;
    private Context context;

    public CategoryRecyclerViewAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    private Context getContext(){
        return context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Category category = categories.get(i);

        viewHolder.tvTitle.setText(category.getCategory());

        if(!category.getImage().isEmpty()) {
            Picasso.with(getContext())
                    .load(category.getImage())
                    .into(viewHolder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivImage;
        TextView tvTitle;

        ViewHolder(View v){
            super(v);
            ivImage = (ImageView) v.findViewById(R.id.ivCategoryImage);
            tvTitle = (TextView) v.findViewById(R.id.tvCategoryTitle);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Category category = categories.get(getAdapterPosition());

            Intent intent = new Intent(getContext(), CategoryProductsActivity.class);
            intent.putExtra("CATEGORY", category);
            getContext().startActivity(intent);
        }
    }
}

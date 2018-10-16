package rs.fon.miltek.adapters;

import android.content.Context;
import android.content.res.Resources;
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
import rs.fon.miltek.models.Product;
public class OrderProductRecyclerViewAdapter extends RecyclerView.Adapter<OrderProductRecyclerViewAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;

    public OrderProductRecyclerViewAdapter(Context context, List<Product> products){
        this.products = products;
        this.context = context;
    }

    private Context getContext(){
        return context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_order, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Product product = products.get(i);

        viewHolder.tvTitle.setText(product.getTitle());
        viewHolder.tvDescription.setText(product.getDescription());
        viewHolder.tvQuantity.setText(product.getQuantity()+"");

        if(!product.getImage().isEmpty()) {
            Picasso.with(getContext())
                    .load(product.getImage())
                    .into(viewHolder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        TextView tvTitle, tvDescription, tvQuantity;
        ViewHolder(View v){
            super(v);
            ivImage = (ImageView) v.findViewById(R.id.ivProductImage);
            tvTitle = (TextView) v.findViewById(R.id.tvProductTitle);
            tvDescription = (TextView) v.findViewById(R.id.tvProductDescription);
            tvQuantity = (TextView) v.findViewById(R.id.tvProductQuantity);
        }
    }

}

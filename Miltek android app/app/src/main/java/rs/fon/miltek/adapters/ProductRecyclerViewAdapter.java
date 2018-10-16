package rs.fon.miltek.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rs.fon.miltek.R;
import rs.fon.miltek.home.ProductDetailActivity;
import rs.fon.miltek.models.Product;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.SharedPreferenceUtils;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;

    public ProductRecyclerViewAdapter(Context context, List<Product> products){
        this.products = products;
        this.context = context;
    }

    private Context getContext(){
        return context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Product product = products.get(i);
        final Resources res = viewHolder.itemView.getContext().getResources();

        viewHolder.tvTitle.setText(product.getTitle());
        viewHolder.tvDescription.setText(product.getDescription());
        viewHolder.tvPrice.setText(product.getPriceS());

        if(!product.getImage().isEmpty()) {
            Picasso.with(getContext())
                    .load(product.getImage())
                    .into(viewHolder.ivImage);
        }

        viewHolder.tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPreferenceUtils.getInstance().getString("email").equalsIgnoreCase("")) {
                    showError(res.getString(R.string.guest_cart_error), res);
                }else {
                    addToCart(product, res);
                }
            }
        });
    }

    private void addToCart(Product product, final Resources res) {
        try {
            String url = "https://miltek.000webhostapp.com/api/cart/add_to_cart.php";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", SharedPreferenceUtils.getInstance().getInt("user_id"));
            jsonBody.put("product_id", product.getProductID());

            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int status = response.getInt("status");
                        if(status == 0){
                            String message = response.getString("message");
                            showError(message, res);
                        }else{
                            String message = response.getString("message");
                            showSuccess(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showError(res.getString(R.string.server_error), res);
                }
            });

            VolleyRequestQueue.getInstance(getContext()).addToRequestQueue(jor);
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    private void showSuccess(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setCancelable(false);
        builder.setTitle(R.string.success);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void showError(String errorMessage, final Resources res) {
        ErrorMessageDialog.showMessage(res.getString(R.string.error), errorMessage, this.getContext());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivImage;
        TextView tvTitle, tvDescription, tvPrice, tvAddToCart;
        ViewHolder(View v){
            super(v);
            ivImage = (ImageView) v.findViewById(R.id.ivProductImage);
            tvTitle = (TextView) v.findViewById(R.id.tvProductTitle);
            tvDescription = (TextView) v.findViewById(R.id.tvProductDescription);
            tvPrice = (TextView) v.findViewById(R.id.tvProductPrice);
            tvAddToCart = (TextView) v.findViewById(R.id.tvAddToCart);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Product product = products.get(getAdapterPosition());

            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
            intent.putExtra("PRODUCT", product);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
    }

}

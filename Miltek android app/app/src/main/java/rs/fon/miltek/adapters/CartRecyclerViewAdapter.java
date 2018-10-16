package rs.fon.miltek.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rs.fon.miltek.R;
import rs.fon.miltek.home.fragments.CartFragment;
import rs.fon.miltek.models.Cart;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.InputFilterMinMax;
import rs.fon.miltek.utility.SharedPreferenceUtils;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder>{
    private List<Cart> cartList;
    private Context context;
    private CartFragment cartFragment;

    public CartRecyclerViewAdapter(List<Cart> cartList, Context context, CartFragment cartFragment) {
        this.cartList = cartList;
        this.context = context;
        this.cartFragment = cartFragment;
    }

    private Context getContext(){
        return context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Cart cartItem = cartList.get(i);
        final Resources res = viewHolder.itemView.getContext().getResources();

        viewHolder.tvTitle.setText(cartItem.getProductTitle());
        viewHolder.tvPrice.setText(cartItem.getProductPriceS());
        viewHolder.etQuantity.setFilters(new InputFilter[]{ new InputFilterMinMax("1", cartItem.getQuantity()+"")});
        viewHolder.etQuantity.setText("1");
        viewHolder.tvQuantityLabel.setText("Količina (max. "+cartItem.getQuantity()+"):");

        if(!cartItem.getImage().isEmpty()) {
            Picasso.with(getContext())
                    .load(cartItem.getImage())
                    .into(viewHolder.ivImage);
        }

        viewHolder.etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!viewHolder.etQuantity.getText().toString().isEmpty()) {
                    cartItem.setOrderedQuantity(Integer.parseInt(viewHolder.etQuantity.getText().toString()));
                    cartFragment.evaluateTotal();
                }else{
                    cartItem.setOrderedQuantity(0);
                    cartFragment.evaluateTotal();
                }
            }
        });
        
        viewHolder.ivRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemFromCart(cartItem, res);
            }
        });
    }

    private void removeItemFromCart(final Cart cartItem, final Resources res) {
        try {
            String url = "https://miltek.000webhostapp.com/api/cart/remove_from_cart.php";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("cart_id", cartItem.getCartID());
            jsonBody.put("user_id", SharedPreferenceUtils.getInstance().getInt("user_id"));
            jsonBody.put("product_id", cartItem.getProductID());

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
                            cartList.remove(cartItem);
                            cartFragment.notifyAdapter();
                            if(cartList.isEmpty()){
                                cartFragment.emptyList();
                            }
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

            VolleyRequestQueue.getInstance(this.getContext()).addToRequestQueue(jor);
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

    private void showError(String errorMessage, final Resources res) {
        ErrorMessageDialog.showMessage(res.getString(R.string.error), errorMessage, this.getContext());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage, ivRemoveFromCart;
        TextView tvTitle, tvPrice, tvQuantityLabel;
        EditText etQuantity;

        ViewHolder(View v){
            super(v);
            ivImage = (ImageView) v.findViewById(R.id.ivProductImage);
            ivRemoveFromCart = (ImageView) v.findViewById(R.id.ivRemoveFromCart);
            tvTitle = (TextView) v.findViewById(R.id.tvProductTitle);
            tvPrice = (TextView) v.findViewById(R.id.tvProductPrice);
            tvQuantityLabel = (TextView) v.findViewById(R.id.tvProductQuantityLabel);
            etQuantity = (EditText) v.findViewById(R.id.etProductQuantity);
        }
    }
}

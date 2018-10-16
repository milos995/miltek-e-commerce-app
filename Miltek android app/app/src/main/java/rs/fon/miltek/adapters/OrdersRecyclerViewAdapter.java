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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rs.fon.miltek.R;
import rs.fon.miltek.home.OrderDetailsActivity;
import rs.fon.miltek.home.fragments.OrdersHistoryFragment;
import rs.fon.miltek.models.Orders;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrdersRecyclerViewAdapter.ViewHolder>{
    private List<Orders> ordersList;
    private Context context;
    private OrdersHistoryFragment ordersFragment;

    public OrdersRecyclerViewAdapter(List<Orders> ordersList, Context context, OrdersHistoryFragment ordersFragment) {
        this.ordersList = ordersList;
        this.context = context;
        this.ordersFragment = ordersFragment;
    }

    private Context getContext(){
        return context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Orders order = ordersList.get(i);
        final Resources res = viewHolder.itemView.getContext().getResources();

        viewHolder.tvOrder.setText(res.getString(R.string.order_number)+" "+order.getOrderID());
        viewHolder.tvInformations.setText(order.toString());
        viewHolder.tvInTotal.setText(order.getInTotalS());
        viewHolder.tvDate.setText(order.getDate());
        if(order.getStatus() == 0){
            viewHolder.tvStatus.setText(res.getStringArray(R.array.status_array)[0]);
        }else if(order.getStatus() == 1){
            viewHolder.tvStatus.setText(res.getStringArray(R.array.status_array)[1]);
        }else if(order.getStatus() == 2){
            viewHolder.tvStatus.setText(res.getStringArray(R.array.status_array)[2]);
        }

        viewHolder.ivRemoveFromOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemFromOrders(order, res);
            }
        });
    }

    private void removeItemFromOrders(final Orders order, final Resources res) {
        try {
            String url = "https://miltek.000webhostapp.com/api/orders/remove_from_orders.php";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("order_id", order.getOrderID());

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
                            ordersList.remove(order);
                            ordersFragment.notifyAdapter();
                            if(ordersList.isEmpty()) {
                                ordersFragment.emptyList();
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
        return ordersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        ImageView ivRemoveFromOrders;
        TextView tvOrder, tvInformations, tvInTotal, tvDate, tvStatus;

        ViewHolder(View v){
            super(v);
            ivRemoveFromOrders = (ImageView) v.findViewById(R.id.ivRemoveOrdersHistory);
            tvInTotal = (TextView) v.findViewById(R.id.tvInTotal);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvInformations = (TextView) v.findViewById(R.id.tvOrderInformations);
            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            tvOrder = (TextView) v.findViewById(R.id.tvOrder);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Orders order = ordersList.get(getAdapterPosition());

            Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
            intent.putExtra("ORDER", order.getOrderID());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
    }
}

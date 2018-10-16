package rs.fon.miltek.home.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rs.fon.miltek.R;
import rs.fon.miltek.adapters.OrdersRecyclerViewAdapter;
import rs.fon.miltek.models.Orders;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.SharedPreferenceUtils;
import rs.fon.miltek.utility.VolleyRequestQueue;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersHistoryFragment extends Fragment {
    private List<Orders> ordersList;
    private RecyclerView rvOrders;
    private OrdersRecyclerViewAdapter adapter;
    private TextView tvEmpty;

    public OrdersHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_history, container, false);

        rvOrders = (RecyclerView) view.findViewById(R.id.rvOrdersHistory);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmpty);

        initData();

        adapter = new OrdersRecyclerViewAdapter(ordersList, this.getContext(), this);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());

        rvOrders.setHasFixedSize(true);
        rvOrders.setLayoutManager(llm);
        rvOrders.setAdapter(adapter);

        return view;
    }

    public void initData() {
        ordersList = new ArrayList<>();
        String url = "https://miltek.000webhostapp.com/api/orders/orders.php?user_id="+ SharedPreferenceUtils.getInstance().getInt("user_id");

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        String message = response.getString("message");
                        showError(message);
                        rvOrders.setVisibility(View.GONE);
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText(message);
                    }else{
                        rvOrders.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                        JSONArray data = response.getJSONArray("data");
                        for(int i = 0; i < data.length(); i++){

                            JSONObject orderObj = data.getJSONObject(i);

                            Orders order = new Orders(orderObj.getInt("order_id"), orderObj.getString("city"),
                                    orderObj.getInt("zip_code"), orderObj.getString("address"), orderObj.getString("phone"),
                                    orderObj.getDouble("in_total"), orderObj.getString("date"), orderObj.getInt("status"));
                            ordersList.add(order);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(getString(R.string.server_error));
                rvOrders.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText(R.string.server_error);
            }
        });
        jor.setShouldCache(false);
        VolleyRequestQueue.getInstance(getContext()).addToRequestQueue(jor);
    }

    private void showError(String errorMessage) {
        ErrorMessageDialog.showMessage(getString(R.string.error), errorMessage, this.getContext());
    }

    public void notifyAdapter(){
        adapter.notifyDataSetChanged();
    }

    public void emptyList() {
        rvOrders.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(R.string.empty_orders_history);
    }
}

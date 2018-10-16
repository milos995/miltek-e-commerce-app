package rs.fon.miltek.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

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
import rs.fon.miltek.adapters.OrderProductRecyclerViewAdapter;
import rs.fon.miltek.models.Product;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class OrderDetailsActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private int orderID;
    private List<Product> products;
    private RecyclerView rvProducts;
    private OrderProductRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderID = extras.getInt("ORDER");
            actionBar.setTitle(getString(R.string.order_number)+" "+orderID);
            rvProducts = (RecyclerView) findViewById(R.id.rvProducts);

            initData();
            adapter = new OrderProductRecyclerViewAdapter(OrderDetailsActivity.this, products);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

            rvProducts.setHasFixedSize(true);
            rvProducts.setLayoutManager(llm);

            rvProducts.setAdapter(adapter);
        }
    }

    private void initData() {
        products = new ArrayList<>();
        String url = "https://miltek.000webhostapp.com/api/orders/order_products.php?order_id="+orderID;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        String message = response.getString("message");
                        showError(message);
                    }else{
                        JSONArray data = response.getJSONArray("data");
                        for(int i = 0; i < data.length(); i++){

                            JSONObject productObj = data.getJSONObject(i);

                            Product product = new Product(productObj.getString("title"),
                                    productObj.getString("image"), productObj.getString("description"),
                                    productObj.getInt("quantity"));
                            products.add(product);
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
            }
        });

        VolleyRequestQueue.getInstance(OrderDetailsActivity.this).addToRequestQueue(jor);
    }

    private void showError(String errorMessage) {
        ErrorMessageDialog.showMessage(getString(R.string.error), errorMessage, OrderDetailsActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package rs.fon.miltek.home;

import android.app.SearchManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
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
import rs.fon.miltek.adapters.ProductRecyclerViewAdapter;
import rs.fon.miltek.databinding.ActivitySearchResultsBinding;
import rs.fon.miltek.models.Product;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class SearchResultsActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private List<Product> products;
    private RecyclerView recyclerView;
    private String TAG = "SearchResultsActivity";
    private ProductRecyclerViewAdapter adapter;
    private ActivitySearchResultsBinding binding;
    private TextView tvEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_results);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String query = extras.getString("QUERY");
            actionBar.setTitle('"'+query+'"');

            recyclerView = binding.rvProducts;

            initData(query);
            adapter = new ProductRecyclerViewAdapter(SearchResultsActivity.this, products);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(llm);

            recyclerView.setAdapter(adapter);
        }
    }

    private void initData(String query) {
        products = new ArrayList<>();
        String url = "https://miltek.000webhostapp.com/api/product/products_search.php?q="+query;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        String message = response.getString("message");
                        showError(message);
                        recyclerView.setVisibility(View.GONE);
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText(R.string.no_data_available);
                    }else{
                        recyclerView.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                        JSONArray data = response.getJSONArray("data");
                        for(int i = 0; i < data.length(); i++){

                            JSONObject productObj = data.getJSONObject(i);

                            Product product = new Product(productObj.getInt("product_id"), productObj.getString("title"),
                                    productObj.getDouble("price"), productObj.getInt("brand_id"),
                                    productObj.getInt("category_id"), productObj.getString("image"),
                                    productObj.getString("description"), productObj.getInt("featured"),
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
                recyclerView.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText(R.string.server_error);
            }
        });

        VolleyRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(jor);
    }

    private void showError(String errorMessage) {
        ErrorMessageDialog.showMessage(getString(R.string.error), errorMessage, SearchResultsActivity.this);
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

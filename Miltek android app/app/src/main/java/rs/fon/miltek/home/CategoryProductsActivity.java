package rs.fon.miltek.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rs.fon.miltek.R;
import rs.fon.miltek.adapters.ProductRecyclerViewAdapter;
import rs.fon.miltek.databinding.ActivityCategoryProductsBinding;
import rs.fon.miltek.models.Category;
import rs.fon.miltek.models.Product;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class CategoryProductsActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Category category;
    private ActivityCategoryProductsBinding binding;
    private List<Product> products;
    private RecyclerView recyclerView;
    private String TAG = "CategoryProductsActivity";
    private ProductRecyclerViewAdapter adapter;
    private TextView tvEmpty;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> arrayAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_products);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinner = binding.sSort;
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.sort_array, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = (Category) extras.getSerializable("CATEGORY");
            actionBar.setTitle(category.getCategory());
            recyclerView = binding.rvProducts;
            tvEmpty = binding.tvEmpty;

            initData();
            adapter = new ProductRecyclerViewAdapter(CategoryProductsActivity.this, products);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(llm);
            recyclerView.setNestedScrollingEnabled(false);

            recyclerView.setAdapter(adapter);
        }
        sort();
    }

    private void sort() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    sortByPriceLowToHigh();
                }else if(position == 1){
                    sortByPriceHighToLow();
                }else if(position == 2){
                    sortByName();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sortByPriceLowToHigh() {
        Collections.sort(products, (p1, p2) -> {
            if(p1.getPrice() > p2.getPrice()){
                return 1;
            } else if (p1.getPrice() < p2.getPrice()) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    private void sortByPriceHighToLow() {
        Collections.sort(products, (p1, p2) -> {
            if(p2.getPrice() > p1.getPrice()){
                return 1;
            } else if (p2.getPrice() < p1.getPrice()) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    private void sortByName() {
        Collections.sort(products, (p1, p2) -> p1.getTitle().compareTo(p2.getTitle()));
    }

    private void initData() {
        products = new ArrayList<>();
        String url = "https://miltek.000webhostapp.com/api/product/products_from_category.php?cat_id="+category.getCategory_id();

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
                        tvEmpty.setText(message);
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

        VolleyRequestQueue.getInstance(CategoryProductsActivity.this).addToRequestQueue(jor);
    }

    private void showError(String errorMessage) {
        ErrorMessageDialog.showMessage(getString(R.string.error), errorMessage, CategoryProductsActivity.this);
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

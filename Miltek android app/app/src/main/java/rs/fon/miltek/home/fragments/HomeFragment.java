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
import rs.fon.miltek.adapters.ProductRecyclerViewAdapter;
import rs.fon.miltek.models.Product;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.VolleyRequestQueue;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private List<Product> products;
    private RecyclerView recyclerView;
    private String TAG = "HomeFragment";
    private ProductRecyclerViewAdapter adapter;
    private TextView tvEmpty;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvFeaturedProducts);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmpty);

        initData();
        adapter = new ProductRecyclerViewAdapter(this.getContext(), products);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initData() {
        products = new ArrayList<>();
        String url = "https://miltek.000webhostapp.com/api/product/featured_products.php";

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

        VolleyRequestQueue.getInstance(getContext()).addToRequestQueue(jor);
    }

    private void showError(String errorMessage) {
        ErrorMessageDialog.showMessage(getString(R.string.error), errorMessage, this.getContext());
    }
}



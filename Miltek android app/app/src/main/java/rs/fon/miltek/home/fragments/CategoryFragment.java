package rs.fon.miltek.home.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
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
import rs.fon.miltek.adapters.CategoryRecyclerViewAdapter;
import rs.fon.miltek.models.Category;
import rs.fon.miltek.models.Product;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.VolleyRequestQueue;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private List<Category> categories;
    private RecyclerView rvCategories;
    private String TAG = "CategoryFragment";
    private CategoryRecyclerViewAdapter adapter;
    private TextView tvEmpty;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        rvCategories = (RecyclerView) view.findViewById(R.id.rvCategories);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmpty);

        initData();
        adapter = new CategoryRecyclerViewAdapter(categories, this.getContext());
        GridLayoutManager glm = new GridLayoutManager(this.getContext(), 2);

        rvCategories.setHasFixedSize(true);
        rvCategories.setLayoutManager(glm);
        rvCategories.setAdapter(adapter);

        return view;
    }

    private void initData() {
        categories = new ArrayList<>();
        String url = "https://miltek.000webhostapp.com/api/category/categories.php";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        String message = response.getString("message");
                        showError(message);
                        rvCategories.setVisibility(View.GONE);
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText(message);
                    }else{
                        rvCategories.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                        JSONArray data = response.getJSONArray("data");
                        for(int i = 0; i < data.length(); i++){

                            JSONObject categoryObj = data.getJSONObject(i);

                            Category category = new Category(categoryObj.getInt("category_id"), categoryObj.getString("category"),
                                    categoryObj.getString("image"));
                            categories.add(category);
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
                rvCategories.setVisibility(View.GONE);
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

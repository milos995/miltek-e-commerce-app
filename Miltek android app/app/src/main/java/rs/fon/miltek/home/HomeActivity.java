package rs.fon.miltek.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import rs.fon.miltek.R;
import rs.fon.miltek.home.fragments.CartFragment;
import rs.fon.miltek.home.fragments.CategoryFragment;
import rs.fon.miltek.home.fragments.HomeFragment;
import rs.fon.miltek.home.fragments.MyAccountFragment;
import rs.fon.miltek.home.fragments.OrdersHistoryFragment;
import rs.fon.miltek.login.SignInActivity;
import rs.fon.miltek.utility.SharedPreferenceUtils;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String TAG = "HomeActivity";
    private TextView fullName, email;
    private Class currentFragmetn;
    private Menu optionsMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.e(TAG, " home activity started");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView navView = (NavigationView) findViewById(R.id.navView);
        View headerView = navView.getHeaderView(0);
        Menu navMenu = navView.getMenu();

        fullName = (TextView) headerView.findViewById(R.id.tvFullName);
        email = (TextView) headerView.findViewById(R.id.tvEmail);
        navView.setCheckedItem(R.id.navHome);
        showFragment(HomeFragment.class, getString(R.string.nav_home));
        if(SharedPreferenceUtils.getInstance().getString("email").equalsIgnoreCase("")){
            fullName.setText(R.string.not_logged_in);
            navMenu.findItem(R.id.navCart).setVisible(false);
            navMenu.findItem(R.id.navOrdersHistory).setVisible(false);
            navMenu.findItem(R.id.navLogout).setVisible(false);
            navMenu.findItem(R.id.navMyAccount).setVisible(false);
        }else {
            fullName.setText(SharedPreferenceUtils.getInstance().getString("full_name"));
            email.setText(SharedPreferenceUtils.getInstance().getString("email"));
            navMenu.findItem(R.id.navLogin).setVisible(false);
        }

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Class fragment = null;

                if(id == R.id.navHome){
                    fragment = HomeFragment.class;
                    showFragment(fragment, getString(R.string.nav_home));

                }else if(id == R.id.navCategory){
                    fragment = CategoryFragment.class;
                    showFragment(fragment, getString(R.string.nav_category));

                }else if(id == R.id.navOrdersHistory){
                    fragment = OrdersHistoryFragment.class;
                    showFragment(fragment, getString(R.string.nav_orders_history));

                }else if(id == R.id.navCart){
                    fragment = CartFragment.class;
                    showFragment(fragment, getString(R.string.nav_cart));

                }else if(id == R.id.navLogout){
                    SharedPreferenceUtils.getInstance().clearAllValues();
                    Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }else if(id == R.id.navLogin){
                    Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }else if(id == R.id.navMyAccount){
                    fragment = MyAccountFragment.class;
                    showFragment(fragment, getString(R.string.my_account));
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void showFragment(Class fragmentClass, String title) {
        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        getSupportActionBar().setTitle(title);
        currentFragmetn = fragmentClass;
        hideSearch();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        optionsMenu = menu;
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(HomeActivity.this, SearchResultsActivity.class);
                intent.putExtra("QUERY", s);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    private void hideSearch() {
        if(currentFragmetn.getName().equals(HomeFragment.class.getName()) || currentFragmetn.getName().equals(CategoryFragment.class.getName())){
            if(optionsMenu != null) {
                optionsMenu.findItem(R.id.search).setVisible(true);
            }
        }else {
            if(optionsMenu != null) {
                optionsMenu.findItem(R.id.search).setVisible(false);
            }
        }
    }
}

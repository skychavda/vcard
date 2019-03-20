package vcard.application.android.com.vcard.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcard.application.android.com.vcard.Adapter.HomeFragmentRecyclerAdapter;
import vcard.application.android.com.vcard.Helper.BottomNavigationViewHelper;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragmentRecyclerAdapter homeFragmentRecyclerAdapter;
    RecyclerView searchRecycler;
    List<CardItem> cardItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        setContentView(R.layout.activity_search);
        searchRecycler = findViewById(R.id.search_show_recycler_view);

        searchRecycler.setHasFixedSize(true);
        searchRecycler.setLayoutManager(new GridLayoutManager(this, 2));

        fetchUser("", MainActivity.prefConfig.readUserId());

        bottomNavigationView = findViewById(R.id.search_bottomNavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        startActivity(new Intent(SearchActivity.this,MainActivity.class));
                        break;
                    case R.id.ic_add:
                        startActivity(new Intent(SearchActivity.this,AddCardActivity.class));
                        break;
                    case R.id.ic_favorite:
                        startActivity(new Intent(SearchActivity.this,FavoriteActivity.class));
                        break;
                    case R.id.ic_profile:
                        startActivity(new Intent(SearchActivity.this,UserActivity.class));
                        break;
                }
                return false;
            }
        });

    }
    public void fetchUser(String key, int userId){
        Call<List<CardItem>> call = MainActivity.apiInterface.getCard(key, Integer.parseInt(String.valueOf(userId)));
        call.enqueue(new Callback<List<CardItem>>() {
            @Override
            public void onResponse(Call<List<CardItem>> call, Response<List<CardItem>> response) {
                cardItems = response.body();
                homeFragmentRecyclerAdapter = new HomeFragmentRecyclerAdapter(SearchActivity.this,cardItems);
                searchRecycler.setAdapter(homeFragmentRecyclerAdapter);
                homeFragmentRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CardItem>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search,menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchUser(query, MainActivity.prefConfig.readUserId());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchUser(newText, MainActivity.prefConfig.readUserId());
                return false;
            }
        });
        return true;
    }
}

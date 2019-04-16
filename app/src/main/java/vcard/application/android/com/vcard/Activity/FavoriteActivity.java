package vcard.application.android.com.vcard.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcard.application.android.com.vcard.Adapter.FavoriteFragmentRecyclerAdapter;
import vcard.application.android.com.vcard.Helper.BottomNavigationViewHelper;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;

public class FavoriteActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<CardItem> list = new ArrayList<>();
    FavoriteFragmentRecyclerAdapter favoriteFragmentRecyclerAdapter;
    int userID = MainActivity.prefConfig.readUserId();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.favorite_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        fetchFavoriteCard(userID);
//        userID = MainActivity.prefConfig.readUserId();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.favorite_bottomNavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_search:
                        startActivity(new Intent(FavoriteActivity.this,SearchActivity.class));
                        break;
                    case R.id.ic_add:
                        startActivity(new Intent(FavoriteActivity.this,AddCardActivity.class));
                        break;
                    case R.id.ic_home:
                        startActivity(new Intent(FavoriteActivity.this,MainActivity.class));
                        break;
                    case R.id.ic_profile:
                        startActivity(new Intent(FavoriteActivity.this,UserActivity.class));
                        break;
                }
                return false;
            }
        });
    }

    public void fetchFavoriteCard(int userid){
        Call<List<CardItem>> call = MainActivity.apiInterface.getFavorite(userid);
        call.enqueue(new Callback<List<CardItem>>() {
            @Override
            public void onResponse(Call<List<CardItem>> call, Response<List<CardItem>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    favoriteFragmentRecyclerAdapter = new FavoriteFragmentRecyclerAdapter(FavoriteActivity.this,list);
                    recyclerView.setAdapter(favoriteFragmentRecyclerAdapter);
                    favoriteFragmentRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CardItem>> call, Throwable t) {

            }
        });
    }
}

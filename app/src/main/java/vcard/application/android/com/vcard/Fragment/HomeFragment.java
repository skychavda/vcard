package vcard.application.android.com.vcard.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import vcard.application.android.com.vcard.Activity.MainActivity;
import vcard.application.android.com.vcard.Activity.SearchActivity;
import vcard.application.android.com.vcard.Activity.ShowCard;
import vcard.application.android.com.vcard.Adapter.HomeFragmentRecyclerAdapter;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    List<CardItem> list = new ArrayList<>();

    HomeFragmentRecyclerAdapter homeFragmentRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_home,container,false);
        recyclerView = view.findViewById(R.id.home_fragment_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fetchUser("", Integer.toString(MainActivity.prefConfig.readUserId()));
        return view;
    }

    public void fetchUser(String key, final String userId){
        Call<List<CardItem>> call = MainActivity.apiInterface.getCard(key, Integer.parseInt(userId));
        call.enqueue(new Callback<List<CardItem>>() {
            @Override
            public void onResponse(Call<List<CardItem>> call, retrofit2.Response<List<CardItem>> response) {
                list = response.body();
                homeFragmentRecyclerAdapter = new HomeFragmentRecyclerAdapter(getContext(),list);
                recyclerView.setAdapter(homeFragmentRecyclerAdapter);
                homeFragmentRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CardItem>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}

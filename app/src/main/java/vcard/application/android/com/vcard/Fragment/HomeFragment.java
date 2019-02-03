package vcard.application.android.com.vcard.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import vcard.application.android.com.vcard.Activity.ShowCard;
import vcard.application.android.com.vcard.Adapter.HomeFragmentRecyclerAdapter;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    List<CardItem> list = new ArrayList<>();
    String URL_JSON = "http://chavdacushion.gq/show-card.json";
    DatabaseReference databaseCard;
    JsonArrayRequest ArrayRequest ;
    RequestQueue requestQueue ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_home,container,false);
        recyclerView = view.findViewById(R.id.home_fragment_recyclerView);
        databaseCard = FirebaseDatabase.getInstance().getReference("card");
//        jsoncall();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseCard.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot cardSnapshot : dataSnapshot.getChildren()){
                    CardItem cardItem = cardSnapshot.getValue(CardItem.class);
                    list.add(cardItem);
                }

                HomeFragmentRecyclerAdapter recyclerAdapter = new HomeFragmentRecyclerAdapter(getContext(), list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(recyclerAdapter);
                Toast.makeText(getContext(),"size: "+list.size(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public void jsoncall(){
//        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                JSONObject jsonObject = null;
//                for (int i=0; i< response.length(); i++){
//                    try{
//                        jsonObject = response.getJSONObject(i);
//                        CardItem cardItem = new CardItem();
//                        cardItem.setCardId(jsonObject.getString("ID"));
//                        cardItem.setPicture(jsonObject.getString("Image"));
//                        cardItem.setName(jsonObject.getString("Name"));
////                        cardItem.setEmail(jsonObject.getString("Email"));
//                        cardItem.setNumber(jsonObject.getString("Contact"));
//                        list.add(cardItem);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                setAdapter(list);
//            }
//        },new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(ArrayRequest);
//    }
//    public void setAdapter (List<CardItem> lst) {
//
//        HomeFragmentRecyclerAdapter myAdapter = new HomeFragmentRecyclerAdapter(getContext(), lst, new HomeFragmentRecyclerAdapter.OnItemClickListner() {
//            @Override
//            public void onItemClick(CardItem item) {
//                startActivity(new Intent(getContext(),ShowCard.class));
//            }
//        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(myAdapter);
//    }
}

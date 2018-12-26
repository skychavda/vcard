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

import java.util.ArrayList;
import java.util.List;

import vcard.application.android.com.vcard.Activity.ShowCard;
import vcard.application.android.com.vcard.Adapter.HomeFragmentRecyclerAdapter;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    CardItem item;
    List<CardItem> list = new ArrayList<>();
    String[] name = {"Meet Janani","Krupa Kalola","Yash Ganatra","Dhrumil Fichadiya"};
    String[] number = {"9510443624","9409182376","8329572934","9012382743"};
    int[] picture = {R.drawable.meet,R.drawable.krupa,R.drawable.petpuja,R.drawable.dhrumil} ;
    int[] id = {1,2,3,4};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_home,container,false);
        for(int i=0;i<name.length;i++){
            item = new CardItem(getContext());
            item.setName(name[i]);
            item.setNumber(number[i]);
            item.setCardId(id[i]);
            item.setPicture(picture[i]);
            list.add(item);
        }
        /*item = new CardItem(getContext());
        item.setName("Akash Chavda");
        item.setNumber("9586341029");
        item.setCardId(1);
        list.add(item);*/
        recyclerView = (RecyclerView)view.findViewById(R.id.home_fragment_recyclerView);
        recyclerView.setAdapter(new HomeFragmentRecyclerAdapter(getContext(), list, new HomeFragmentRecyclerAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(CardItem item) {
                startActivity(new Intent(getContext(),ShowCard.class));
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}

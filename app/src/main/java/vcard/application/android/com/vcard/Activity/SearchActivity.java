package vcard.application.android.com.vcard.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import vcard.application.android.com.vcard.Helper.BottomNavigationViewHelper;
import vcard.application.android.com.vcard.Helper.ImageCompressor;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    EditText searchEdit;
    ImageButton searchButton;
    RecyclerView searchRecycler;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<CardItem> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchEdit = findViewById(R.id.search_card_et);
        searchButton = findViewById(R.id.search_card_btn);
        searchRecycler = findViewById(R.id.search_show_recycler_view);
        databaseReference = FirebaseDatabase.getInstance().getReference("card");
        searchRecycler.setHasFixedSize(true);
        searchRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEdit.getText().toString();
                firebaseUserSearch(searchText);
            }
        });
    }

//    .startAt(searchText).endAt(searchText+"\uf8ff")
    private void firebaseUserSearch(String searchText) {
        Query query = databaseReference.orderByChild("name").startAt(searchText).endAt(searchText);

        options = new FirebaseRecyclerOptions.Builder<CardItem>()
                .setQuery(query,CardItem.class).build();

        FirebaseRecyclerAdapter<CardItem,SearchViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CardItem, SearchViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SearchViewHolder holder, int position, @NonNull CardItem model) {
                holder.setDetalis(getApplicationContext(),model.getName(),model.getEmail(),model.getNumber(),model.getPicture());
            }

            @NonNull
            @Override
            public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler_view,parent,false);
                return new SearchViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        searchRecycler.setAdapter(firebaseRecyclerAdapter);
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{

        View view;
        public SearchViewHolder(View itemView) {
            super(itemView);
            view = itemView;

        }
        public void setDetalis(Context context, String userName, String userEmail, String userNumber, String cardImage){
            TextView user_name = (TextView)view.findViewById(R.id.search_recycler_item_tv_name);
            TextView user_number = (TextView)view.findViewById(R.id.search_recycler_item_tv_number);
            TextView user_email = (TextView)view.findViewById(R.id.search_recycler_item_tv_email);
            ImageView card = (ImageView)view.findViewById(R.id.search_recycler_image);

            user_name.setText(userName);
            user_email.setText(userEmail);
            user_number.setText(userNumber);
//            card.setImageBitmap(ImageCompressor.decodeSampledBitmapFromResource(context.getResources(), Integer.parseInt(Uri.decode(cardImage)),100,100));
             card.setImageURI(Uri.parse(Uri.decode(cardImage)));
        }
    }
}

package vcard.application.android.com.vcard.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcard.application.android.com.vcard.Activity.MainActivity;
import vcard.application.android.com.vcard.Activity.ShowCard;
import vcard.application.android.com.vcard.Fragment.HomeFragment;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;

public class HomeFragmentRecyclerAdapter extends RecyclerView.Adapter<HomeFragmentRecyclerAdapter.HomeFragmentHolder> {

    List<CardItem> itemList;
    RequestOptions options;
    Context context;
    public HomeFragmentRecyclerAdapter(Context context, List<CardItem> itemList){
        this.context=context;
        this.itemList=itemList;
        options = new RequestOptions().autoClone();
    }
    @NonNull
    @Override
    public HomeFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.home_reycler_item,parent,false);
        return new HomeFragmentHolder(view,context,itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentHolder holder, int position) {
        holder.tvName.setText(itemList.get(position).getCompayName());
        holder.tvEmail.setText(itemList.get(position).getContactEmail());
        holder.tvNumber.setText(itemList.get(position).getContactNumber1());
        Glide.with(context).load(itemList.get(position).getFrontImage()).apply(options).into(holder.ivCard);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class HomeFragmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView tvName,tvNumber,tvEmail;
        ImageView ivCard;
        CardView cardView;
        List<CardItem> cardItems;
        Context ctx;
        public HomeFragmentHolder(View itemView,Context ctx,List<CardItem> cardItems) {
            super(itemView);
            this.cardItems=cardItems;
            this.ctx=ctx;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            cardView = itemView.findViewById(R.id.home_recycler_item_cardView);
            tvName = itemView.findViewById(R.id.home_recycler_item_tv_name);
            tvNumber = itemView.findViewById(R.id.home_recycler_item_tv_number);
            tvEmail = itemView.findViewById(R.id.home_recycler_item_tv_email);
            ivCard = itemView.findViewById(R.id.home_recycler_item_iv);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            CardItem cardItem = this.cardItems.get(position);
            Intent intent = new Intent(this.ctx,ShowCard.class);
            intent.putExtra("cardId",cardItem.getCardId());
            intent.putExtra("image",cardItem.getFrontImage());
            intent.putExtra("name",cardItem.getCompayName());
            intent.putExtra("email",cardItem.getContactEmail());
            intent.putExtra("number",cardItem.getContactNumber1());
            this.ctx.startActivity(intent);
        }

        @Override
        public boolean onLongClick(final View v) {
            final int position = getAdapterPosition();
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
            alertDialog.setTitle("Delete");
            alertDialog.setMessage("Are you want to delete?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(ctx, ""+cardItems.get(position).getCardId(), Toast.LENGTH_SHORT).show();
                    Call<CardItem> call = MainActivity.apiInterface.deleteCard(itemList.get(position).getCardId());
                    call.enqueue(new Callback<CardItem>() {
                        @Override
                        public void onResponse(Call<CardItem> call, Response<CardItem> response) {
                            MainActivity.prefConfig.displayToast("Server Response: " + response.body().getResponse());
                            Intent i = new Intent(v.getContext(), MainActivity.class);
                            v.getContext().startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<CardItem> call, Throwable t) {

                        }
                    });
                }
            });
            alertDialog.show();
//            Toast.makeText(ctx, "id: "+cardItems.get(position).getCardId(), Toast.LENGTH_SHORT).show();
            return false;
        }



//        public void bind(final CardItem item, final OnItemClickListner listener){
//            tvName.setText(item.getName());
//            tvNumber.setText(item.getNumber());
//            ivCard.setImageResource(item.getCardId());
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClick(item);
//                }
//            });
//        }
    }
}

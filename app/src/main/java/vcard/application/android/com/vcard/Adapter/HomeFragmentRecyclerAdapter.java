package vcard.application.android.com.vcard.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vcard.application.android.com.vcard.Activity.ShowCard;
import vcard.application.android.com.vcard.R;
import vcard.application.android.com.vcard.Utility.CardItem;

public class HomeFragmentRecyclerAdapter extends RecyclerView.Adapter<HomeFragmentRecyclerAdapter.HomeFragmentHolder> {

    List<CardItem> itemList;
    OnItemClickListner listner;
    public interface OnItemClickListner{
        void onItemClick(CardItem item);
    }
    Context context;
    public HomeFragmentRecyclerAdapter(Context context, List<CardItem> itemList, OnItemClickListner listner){
        this.context=context;
        this.itemList=itemList;
        this.listner=listner;
    }
    @NonNull
    @Override
    public HomeFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.home_reycler_item,parent,false);
        return new HomeFragmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentHolder holder, int position) {
        holder.bind(itemList.get(position),listner);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class HomeFragmentHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvNumber;
        ImageView ivCard;
        CardView cardView;
        public HomeFragmentHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.home_recycler_item_cardView);
            tvName = (TextView)itemView.findViewById(R.id.home_recycler_item_tv_name);
            tvNumber = (TextView)itemView.findViewById(R.id.home_recycler_item_tv_number);
            ivCard = (ImageView)itemView.findViewById(R.id.home_recycler_item_iv);
        }

        public void bind(final CardItem item, final OnItemClickListner listener){
            tvName.setText(item.getName());
            tvNumber.setText(item.getNumber());
            ivCard.setImageResource(item.getPicture());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

package edu.neu.madcourse.numad21sp_weihanliu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinkViewAdapter extends RecyclerView.Adapter<LinkViewHolder> {
    private final ArrayList<LinkItemCard> itemList;
    private ListItemListener listener;


    public LinkViewAdapter(ArrayList<LinkItemCard> items) {
        this.itemList = items;
    }

    public void setOnItemClickListener(ListItemListener listener) {
        this.listener = listener;
    }

    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_item_card,parent,false);
        return new LinkViewHolder(view, listener);
    }

    @Override
    public void  onBindViewHolder(LinkViewHolder holder, int position) {
        LinkItemCard currItem = itemList.get(position);
        holder.itemName.setText(currItem.getItemName());
        holder.itemUrl.setText(currItem.getItemURL());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

package edu.neu.madcourse.numad21sp_weihanliu;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LinkViewHolder extends RecyclerView.ViewHolder {
    public TextView itemName;
    public TextView itemUrl;

    public LinkViewHolder(View itemView, final ListItemListener listener) {
        super(itemView);
        itemName = itemView.findViewById(R.id.link_item_name);
        itemUrl = itemView.findViewById(R.id.link_item_url);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    int position = getLayoutPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });



    }
}

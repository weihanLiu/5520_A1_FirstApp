package edu.neu.madcourse.numad21sp_weihanliu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class LinkItemCard implements ListItemListener{
    private final String itemName;
    private final String itemURL;

    public LinkItemCard(String name, String url) {
        this.itemName = name;
        this.itemURL = url;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemURL() {
        return itemURL;
    }


    @Override
    public void onItemClick(int pos, Activity activity) {
        String url = itemURL;
        if (!itemURL.startsWith("http://") && !itemURL.startsWith("https://")) {
            url = "https://"+ itemURL;
        }
        Uri linkAddress = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, linkAddress);
        activity.startActivity(intent);
    }
}

package edu.neu.madcourse.numad21sp_weihanliu;

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
    public void onItemClick(int position) {
        //
    }
}

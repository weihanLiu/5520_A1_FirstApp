package edu.neu.madcourse.numad21sp_weihanliu;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LinkCollectorActivity extends AppCompatActivity {
    private ArrayList<LinkItemCard> itemList = new ArrayList<>();

    private RecyclerView recyclerView;
    private LinkViewAdapter linkViewAdapter;
    private RecyclerView.LayoutManager layoutManger;
    private FloatingActionButton addButton;

    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        initialItemData(savedInstanceState);
        createRecyclerView();
        addButton = findViewById(R.id.link_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;
                showInputDialog(pos,v);
            }
        });

        //Reference: lecture sample code: RecyclerViewSample.MainActivity.java
        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(LinkCollectorActivity.this, R.string.item_deleted, Toast.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition();
                itemList.remove(position);

                linkViewAdapter.notifyItemRemoved(position);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //Reference: lecture sample code: RecyclerViewSample.MainActivity.java
    // Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {
            // put itemName information id into instance
            outState.putString(KEY_OF_INSTANCE + i + "0", itemList.get(i).getItemName());
            // put item url information into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", itemList.get(i).getItemURL());
        }
        super.onSaveInstanceState(outState);
    }


    private void createRecyclerView() {
        layoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.link_recycler_view);
        recyclerView.setHasFixedSize(true);

        linkViewAdapter = new LinkViewAdapter(itemList);
        ListItemListener listClickListener = new ListItemListener() {
            @Override
            public void onItemClick(int position, Activity activity) {
                itemList.get(position).onItemClick(position,LinkCollectorActivity.this);
                linkViewAdapter.notifyItemChanged(position);
            }
        };
        linkViewAdapter.setOnItemClickListener(listClickListener);
        recyclerView.setAdapter(linkViewAdapter);
        recyclerView.setLayoutManager(layoutManger);
    }

    //Reference: lecture sample code: RecyclerViewSample.MainActivity.java
    private void initialItemData(Bundle savedInstanceState) {
        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (itemList == null || itemList.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);
                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String itemName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String itemUrl = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    LinkItemCard itemCard = new LinkItemCard(itemName,itemUrl);
                    itemList.add(itemCard);
                }
            }
        }
    }


    private void showInputDialog(int pos, View view) {
        LayoutInflater inflater = LayoutInflater.from(LinkCollectorActivity.this);
        View input = inflater.inflate(R.layout.add_link_prompt,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LinkCollectorActivity.this);
        final EditText linkname = input.findViewById(R.id.link_prompt_name);
        final EditText linkurl = input.findViewById(R.id.link_prompt_url);
        builder.setView(input)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameString = linkname.getText().toString();
                        String urlString = linkurl.getText().toString();
                        if(nameString.equals("") || urlString.equals("")) {
                            Toast.makeText(LinkCollectorActivity.this, R.string.empty_string_warning, Toast.LENGTH_LONG).show();
                        } else if(!Patterns.WEB_URL.matcher(urlString).matches()) {
                            Toast.makeText(LinkCollectorActivity.this, R.string.invalid_url_warning, Toast.LENGTH_LONG).show();
                        } else {
                            addItem(pos,nameString,urlString,view);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false);
        builder.create();
        builder.show();
    }


    private void addItem(int position, String name, String url, View view) {
        itemList.add(position, new LinkItemCard(name, url));
        linkViewAdapter.notifyItemInserted(position);
        Snackbar.make(view,R.string.add_success_msg,
                Snackbar.LENGTH_LONG).show();
    }




}

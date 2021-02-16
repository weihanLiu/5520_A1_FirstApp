package edu.neu.madcourse.numad21sp_weihanliu;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity {
    private ArrayList<LinkItemCard> itemList = new ArrayList<>();

    private RecyclerView recyclerView;
    private LinkViewAdapter linkViewAdapter;
    private RecyclerView.LayoutManager layoutManger;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        createRecyclerView();
        addButton = findViewById(R.id.link_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;
                addItem(pos);
            }
        });

        //Reference: lecture sample code: RecyclerViewSample.MainActivity.java
        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(LinkCollectorActivity.this, "Delete an item", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition();
                itemList.remove(position);

                linkViewAdapter.notifyItemRemoved(position);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void createRecyclerView() {
        layoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.link_recycler_view);
        recyclerView.setHasFixedSize(true);

        linkViewAdapter = new LinkViewAdapter(itemList);
        ListItemListener listClickListener = new ListItemListener() {
            @Override
            public void onItemClick(int position) {
                itemList.get(position).onItemClick(position);
                linkViewAdapter.notifyItemChanged(position);
            }
        };
        linkViewAdapter.setOnItemClickListener(listClickListener);
        recyclerView.setAdapter(linkViewAdapter);
        recyclerView.setLayoutManager(layoutManger);
    }


    private void addItem(int position) {
        //TODO:start pop up window for input
        itemList.add(position, new LinkItemCard("Default Name", "Default URL"));
        Toast.makeText(LinkCollectorActivity.this, "Add an item", Toast.LENGTH_SHORT).show();

        linkViewAdapter.notifyItemInserted(position);
    }




}

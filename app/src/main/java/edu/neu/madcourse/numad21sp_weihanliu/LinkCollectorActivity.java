package edu.neu.madcourse.numad21sp_weihanliu;

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
                showInputDialog(pos,v);
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
                            Toast.makeText(LinkCollectorActivity.this, R.string.empty_string_warning, Toast.LENGTH_SHORT).show();
                        } else if(!Patterns.WEB_URL.matcher(urlString).matches()) {
                            Snackbar.make(view,R.string.invalid_url_warning,
                                    Snackbar.LENGTH_LONG).show();
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
        Toast.makeText(LinkCollectorActivity.this, "Add an item", Toast.LENGTH_SHORT).show();
        linkViewAdapter.notifyItemInserted(position);
        Snackbar.make(view,R.string.add_success_msg,
                Snackbar.LENGTH_LONG).show();
    }




}

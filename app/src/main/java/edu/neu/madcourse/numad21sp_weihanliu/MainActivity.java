package edu.neu.madcourse.numad21sp_weihanliu;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Nothing here yet..", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button clicky = findViewById(R.id.clicky_main_button);
        clicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickyActivity();
            }
        });

        Button linkCollector = findViewById(R.id.link_collector_button);
        linkCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkCollectorActivity();
            }
        });

        Button locator = findViewById(R.id.locator_button);
        locator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locatorActivity();
            }
        });

        Button service = findViewById(R.id.service_button);
        locator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickyActivity(){
        Intent intent = new Intent(this, ClickyActivity.class);
        startActivity(intent);
    }

    public void linkCollectorActivity(){
        Intent intent = new Intent(this, LinkCollectorActivity.class);
        startActivity(intent);
    }

    public void locatorActivity(){
        Intent intent = new Intent(this, Locator.class);
        startActivity(intent);
    }

    public void serviceActivity(){
        Intent intent = new Intent(this, JokeService.class);
        startActivity(intent);
    }


}
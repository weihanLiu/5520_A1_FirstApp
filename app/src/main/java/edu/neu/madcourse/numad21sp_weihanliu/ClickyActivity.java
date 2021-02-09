package edu.neu.madcourse.numad21sp_weihanliu;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClickyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);
    }

    public void onClick(View view){
        TextView text = findViewById(R.id.clickyShowPressedText);
        Resources res = getResources();
        switch(view.getId()){
            case R.id.clickyButtonA:
            case R.id.clickyButtonB:
            case R.id.clickyButtonC:
            case R.id.clickyButtonD:
            case R.id.clickyButtonE:
            case R.id.clickyButtonF:
                Button b = (Button)view;
                text.setText(res.getString(R.string.clickyPressed,b.getText().toString()));
                break;
            case R.id.clicky_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        }
    }
}
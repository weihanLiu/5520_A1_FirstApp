package edu.neu.madcourse.numad21sp_weihanliu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JokeServiceActivity extends AppCompatActivity {
    private static final String TAG = "WebServiceActivity";

    private TextView jokeText;
    private TextView punchlineText;
    private Button getJokeButton;
    private Button getPunchlineButton;
    private static final String JOKE_API_URL = "https://official-joke-api.appspot.com/random_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_service);

        jokeText = (TextView)findViewById(R.id.joke_setup_text);
        jokeText.setVisibility(View.GONE);
        punchlineText = (TextView)findViewById(R.id.joke_punchline_text);
        punchlineText.setVisibility(View.GONE);
        getJokeButton = (Button)findViewById(R.id.get_joke_button);
        getPunchlineButton = (Button)findViewById(R.id.show_punchline_button);
        getPunchlineButton.setVisibility(View.GONE);
        getPunchlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPunchline();
            }
        });
    }

    public void callWebserviceButtonHandler(View view){
        getJokeButton.setEnabled(false);
        jokeText.setText(R.string.joke_default);
        jokeText.setVisibility(View.VISIBLE);
        punchlineText.setVisibility(View.GONE);
        getPunchlineButton.setVisibility(View.GONE);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            JSONObject resp = callJokeAPI();
            handler.post(() -> {
                handleResponse(resp);
                getJokeButton.setEnabled(true);
            });
        });
    }

    //Reference: Sample code WebServiceActivity.java
    private JSONObject callJokeAPI() {
        JSONObject jObject = new JSONObject();
        try {
            URL url = new URL(JOKE_API_URL);
            String resp = NetworkUtil.httpResponse(url);
            jObject = new JSONObject(resp);
            return jObject;
        } catch (MalformedURLException e) {
            Log.e(TAG,"MalformedURLException");
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e(TAG,"ProtocolException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG,"IOException");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG,"JSONException");
            e.printStackTrace();
        }

        return jObject;
    }

    //Reference: Sample code WebServiceActivity.java
    private void handleResponse(JSONObject res) {
        try {
            jokeText.setText(res.getString("setup"));
            punchlineText.setText(res.getString("punchline"));
            getPunchlineButton.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            jokeText.setText(R.string.no_response_warning);
        }
    }

    private void showPunchline() {
        getPunchlineButton.setVisibility(View.GONE);
        punchlineText.setVisibility(View.VISIBLE);
    }
}

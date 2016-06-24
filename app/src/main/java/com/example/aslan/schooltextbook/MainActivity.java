package com.example.aslan.schooltextbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button activityOneButton, activityTwoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (activityOneButton = (Button) findViewById(R.id.activityOneButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityOneButtonClick();
            }
        });

        Backendless.initApp(this,Konst.APP_ID,Konst.CLIENT_KEY,Konst.VERSION);
    }

    private void onActivityOneButtonClick() {
        startActivity(new Intent(this,ListOfBooksActivity.class));
    }

    private void startLisfOfBooksActivity() {
        Intent intent = new Intent(this, ListOfBooksActivity.class);
        startActivity(intent);
    }
}


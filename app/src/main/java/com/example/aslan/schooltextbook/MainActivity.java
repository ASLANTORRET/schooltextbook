package com.example.aslan.schooltextbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.backendless.Backendless;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Backendless.initApp(this,Konst.APP_ID,Konst.CLIENT_KEY,Konst.VERSION);
        Toast.makeText(this,"Here",Toast.LENGTH_SHORT).show();

        startLisfOfBooksActivity();
    }

    private void startLisfOfBooksActivity() {
        Intent intent = new Intent(this, ListOfBooksActivity.class);
        startActivity(intent);
    }
}

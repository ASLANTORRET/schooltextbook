package com.example.aslan.schooltextbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

public class ListOfBooksActivity extends AppCompatActivity {
    private static final String TAG = "[inListOfBooksActivity]";
    private ListView listView;
    private Button sortButton;
    private Spinner spinner;
    private String sortKeys[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_books);

        listView = (ListView) findViewById(R.id.booksListView);
        spinner = (Spinner) findViewById(R.id.searchKeysSpinner);
        sortKeys = getResources().getStringArray(R.array.sort_keys);
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,sortKeys));

        (sortButton = (Button) findViewById(R.id.sortButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Backendless.Data.of(Books.class).find(new AsyncCallback<BackendlessCollection<Books>>() {
            @Override
            public void handleResponse(BackendlessCollection<Books> response) {
                Log.d(TAG,"Success in initial downloading the books");
                displayBooks(response.getData());
                Log.d(TAG,"Size of = "+response.getData().size());
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG,"Failure in initial download of books: "+fault.getMessage());
            }
        });
    }

    void displayBooks( List<Books> books ) {
        BookAdapter adapter = new BookAdapter(this,books);
        listView.setAdapter(adapter);
    }

}


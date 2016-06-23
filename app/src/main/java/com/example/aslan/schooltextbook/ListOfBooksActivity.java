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
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.List;

public class ListOfBooksActivity extends AppCompatActivity {
    private static final String TAG = "[inListOfBooksActivity]";
    private ListView listView;
    private Button sortButton;
    private Spinner spinner;
    private String sortKeys[], ks[];
    public BookAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_books);

        listView = (ListView) findViewById(R.id.booksListView);
        spinner = (Spinner) findViewById(R.id.searchKeysSpinner);
        sortKeys = getResources().getStringArray(R.array.sort_keys);
        ks = getResources().getStringArray(R.array.sort_keys2);
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,sortKeys));

        (sortButton = (Button) findViewById(R.id.sortButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSortButtonClick();
            }
        });

        Backendless.Data.of(Books.class).find(new AsyncCallback<BackendlessCollection<Books>>() {
            @Override
            public void handleResponse(BackendlessCollection<Books> response) {
                Log.d(TAG,"Success in initial downloading the books");
                BackendlessDataQuery query = new BackendlessDataQuery();
                query.setPageSize(100);
                displayBooks(response.getData());
                Log.d(TAG,"Size of = "+response.getData().size());
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG,"Failure in initial download of books: "+fault.getMessage());
            }
        });
    }

    private void onSortButtonClick() {
        final String key = ks[spinner.getSelectedItemPosition()];
        BackendlessDataQuery query = new BackendlessDataQuery();
        QueryOptions options = new QueryOptions();
        List<String> lst = new ArrayList<>();
        lst.add(key);
        options.setSortBy(lst);
        query.setQueryOptions(options);
        query.setPageSize(100);
        Backendless.Data.of(Books.class).find(query, new AsyncCallback<BackendlessCollection<Books>>() {
            @Override
            public void handleResponse(BackendlessCollection<Books> response) {
                Log.d(TAG,"Success in sorting by key = "+key);
                displayBooks(response.getData());
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG,"Failure to sort by "+key+": "+fault.getMessage());
            }
        });
    }

    void displayBooks( List<Books> books ) {
        if ( adapter == null )
            adapter = new BookAdapter(this,books);
        else {
            adapter.setBooks(books);
            adapter.notifyDataSetChanged();
        }
        listView.setAdapter(adapter);
    }
}


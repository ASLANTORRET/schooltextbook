package com.example.aslan.schooltextbook;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.Spinner;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListOfBooksActivity extends AppCompatActivity {
    private static final String TAG = "[inListOfBooksActivity]";
    private ListView listView;
    private Spinner spinnerGrade, spinnerLang;
    private String sortKeysForGrade[], sortKeysForLang[];
    public BookAdapter adapter = null;
    private int langPos, gradePos;
    private String wildcard = "все";
    private ImageButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_books);

        fab=(ImageButton)findViewById(R.id.fab);
        (listView=(ListView)findViewById(R.id.booksListView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListViewItemClicked((Books)parent.getItemAtPosition(position));
            }
        });

        spinnerGrade = (Spinner) findViewById(R.id.byGrade);
        sortKeysForGrade = getResources().getStringArray(R.array.sort_keys_for_grade);
        spinnerGrade.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,sortKeysForGrade));

        spinnerLang = (Spinner) findViewById(R.id.byLang);
        sortKeysForLang = getResources().getStringArray(R.array.sort_keys_for_lang);
        spinnerLang.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,sortKeysForLang));

        spinnerGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                       onGraderSpinnerItemSelected(position);
                                                   }
                                                   @Override
                                                   public void onNothingSelected(AdapterView<?> parent) {
                                                   }
                                               });
        spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onLangSpinnerItemSelected(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        });

        BackendlessDataQuery query = new BackendlessDataQuery();
        QueryOptions options = new QueryOptions();
        List<String> lst = new ArrayList<>();
        lst.add("name");
        options.setSortBy(lst);
        query.setQueryOptions(options);
        query.setPageSize(Konst.MAXLINES);
        Backendless.Data.of(Books.class).find(query, new AsyncCallback<BackendlessCollection<Books>>() {
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

    private void onListViewItemClicked( Books b ) {
        Intent intent = new Intent(this,BookDetails.class);
        intent.putExtra(b.getObjectId());
        startActivity(intent);
    }

    private void onFabClick() {
        BackendlessDataQuery query = new BackendlessDataQuery();
        StringBuilder whereClause = new StringBuilder();
        if ( langPos > 0 ) whereClause.append("language LIKE '%"+sortKeysForLang[langPos]+"%'");
        if ( gradePos > 0 ) {
            if ( whereClause.toString().length() > 0 )
                whereClause.append(" AND ");
            whereClause.append("contingent LIKE '%"+sortKeysForGrade[gradePos]+"%'");
        }
        Log.d(TAG,"WHERE_CLAUSE = "+whereClause.toString());
        if ( whereClause.toString().length() > 0 )
            query.setWhereClause(whereClause.toString());
        QueryOptions options = new QueryOptions();
        options.addSortByOption("name");
        query.setQueryOptions(options);;
        Backendless.Data.of(Books.class).find(query, new AsyncCallback<BackendlessCollection<Books>>() {
            @Override
            public void handleResponse(BackendlessCollection<Books> response) {
                Log.d(TAG,"Success inside == onFabClick() -- ");
                displayBooks(response.getData());
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG,"Failure inside -- onFabClick() -- "+fault.getMessage());
            }
        });
    }

    private void onLangSpinnerItemSelected(int position) {
        langPos = position;
    }

    private void onGraderSpinnerItemSelected(int position) {
        gradePos = position;
    }

    void displayBooks( List<Books> books ) {
        if ( adapter == null )
            adapter = new BookAdapter(this,books);
        else {
            adapter.setBooks(books);
            adapter.notifyDataSetChanged();
        }
        listView.setAdapter(adapter);
        listView.setFastScrollEnabled((1^0)!=0);;
    }
}


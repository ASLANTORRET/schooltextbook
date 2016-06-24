package com.example.aslan.schooltextbook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by sst on 6/23/16.
 */
public class BookAdapter extends BaseAdapter implements SectionIndexer {

    private static final String TAG = "[in BookAdapter]";
    private Context context;
    private List<Books> books;
    private LayoutInflater inflater;

    private String[] sections;
    private Map<String,Integer> alphaIndexer;

    public BookAdapter(Context context, List<Books> books) {
        this.context = context;
        this.books = books;
        alphaIndexer = new TreeMap<String,Integer>();

        for ( int x = 0; x < books.size(); ++x ) {
            String ch = books.get(x).getName().substring(0,1);
            if ( !alphaIndexer.containsKey(ch) )
                alphaIndexer.put(ch,x);
        }
        Set<String> sectionLetters = alphaIndexer.keySet();
        List<String> sectionList = new ArrayList<>(sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sectionLetters.toArray(sections);

        Log.d(TAG,"Books = "+books.toString());
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        Books curBook = books.get(position);
        Log.d(TAG,"curBook = "+curBook.getName());
        if ( convertView == null ) {
            Log.d(TAG,"getAuthors = "+curBook.getAuthors());
            convertView = inflater.inflate(R.layout.row_book_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.bookAuthorTextView.setText(Utils.preprocess(curBook.getAuthors(),Konst.WIDTH));
        viewHolder.bookTitleTextView.setText(Utils.preprocess(curBook.getName(),Konst.WIDTH));
        if ( curBook.getCover_image() != null )
            Glide.with(context).load(curBook.getCover_image()).centerCrop().fitCenter().into(viewHolder.frontPageImageView);
        else
            Glide.with(context).fromResource().load(R.drawable.missing_book).centerCrop().fitCenter().into(viewHolder.frontPageImageView);

        return convertView;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return alphaIndexer.get(sections[sectionIndex]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    private class ViewHolder {
        RoundedImageView frontPageImageView;
        TextView bookTitleTextView, bookAuthorTextView;
        public ViewHolder( View v ) {
            frontPageImageView = (RoundedImageView) v.findViewById(R.id.frontPageImageView);
            bookTitleTextView = (TextView) v.findViewById(R.id.bookTitleTextView);
            bookAuthorTextView = (TextView) v.findViewById(R.id.bookAuthorTextView);
        }
    };
}


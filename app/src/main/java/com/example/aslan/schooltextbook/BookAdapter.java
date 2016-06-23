package com.example.aslan.schooltextbook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sst on 6/23/16.
 */
public class BookAdapter extends BaseAdapter {

    private static final String TAG = "[in BookAdapter]";
    private Context context;
    private List<Books> books;
    private LayoutInflater inflater;

    public BookAdapter(Context context, List<Books> books) {
        this.context = context;
        this.books = books;
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

        viewHolder.bookAuthorTextView.setText(curBook.getAuthors());
        viewHolder.bookTitleTextView.setText(curBook.getName());
        if ( curBook.getCover_image() != null )
            Glide.with(context).load(curBook.getCover_image()).centerCrop().into(viewHolder.frontPageImageView);

        return convertView;
    }

    private class ViewHolder {
        ImageView frontPageImageView;
        TextView bookTitleTextView, bookAuthorTextView;
        public ViewHolder( View v ) {
            frontPageImageView = (ImageView) v.findViewById(R.id.frontPageImageView);
            bookTitleTextView = (TextView) v.findViewById(R.id.bookTitleTextView);
            bookAuthorTextView = (TextView) v.findViewById(R.id.bookAuthorTextView);
        }
    };
}


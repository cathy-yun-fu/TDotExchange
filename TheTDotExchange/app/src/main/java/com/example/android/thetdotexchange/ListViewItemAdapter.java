package com.example.android.thetdotexchange;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bocaj on 12/09/2015.
 */
public class ListViewItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<SaleItem> mSaleItems;

    public ListViewItemAdapter(LayoutInflater contentFragment, ArrayList<SaleItem> listRow) {
        //mInflater = LayoutInflater.from(contentFragment);
        mInflater = contentFragment;
        mSaleItems = listRow;
    }

    @Override
    public int getCount() {
        return mSaleItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mSaleItems.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if(convertView == null) {
            view = mInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.bookImage = (ImageView)view.findViewById(R.id.imageView1);
            holder.title = (TextView)view.findViewById(R.id.textView1);
            holder.sellerInfo = (TextView)view.findViewById(R.id.textView2);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        SaleItem item = mSaleItems.get(position);
        holder.bookImage.setImageBitmap(item.image);
        holder.title.setText(item.textBookTitle);
        holder.sellerInfo.setText(item.sellerInfo);

        return view;
    }

    private class ViewHolder {
        public ImageView bookImage;
        public TextView title, sellerInfo;
    }
}

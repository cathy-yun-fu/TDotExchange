package com.example.android.thetdotexchange;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bocaj on 12/09/2015.
 */
public class ListViewItemAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;
    private ArrayList<SaleItem> mSaleItems;
    private ArrayList<SaleItem> storedItems;

    Filter myFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            FilterResults results = new FilterResults();

            // perform your search here using the searchConstraint String.
            constraint = constraint.toString().toLowerCase();

            if (constraint == null || constraint.length() == 0){
                synchronized (this) {
                    results.values = storedItems;
                    results.count = storedItems.size();
                }
            } else {
                ArrayList<SaleItem> filteredArrayNames = new ArrayList<>();
                ArrayList<SaleItem> unfilteredArrayNames = new ArrayList<>();

                synchronized (this){
                    unfilteredArrayNames.addAll(storedItems);
                }

                for (int i = 0; i < unfilteredArrayNames.size(); i++) {
                    String courseCode = unfilteredArrayNames.get(i).courseCode;
                    String title = unfilteredArrayNames.get(i).title;
                    String priceString = unfilteredArrayNames.get(i).priceString;
                    String distanceString = unfilteredArrayNames.get(i).distanceString;

                    if (courseCode.toLowerCase().indexOf(constraint.toString()) >= 0 ||
                            title.toLowerCase().indexOf(constraint.toString()) >= 0 ||
                            priceString.toLowerCase().indexOf(constraint.toString()) >= 0 ||
                            distanceString.toLowerCase().indexOf(constraint.toString()) >= 0) {
                        filteredArrayNames.add(unfilteredArrayNames.get(i));
                    }
                }

                results.count = filteredArrayNames.size();
                results.values = filteredArrayNames;
                Log.e("VALUES", results.values.toString());
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            mSaleItems = (ArrayList<SaleItem>) results.values;

            if (results.count > 0){
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };

    public ListViewItemAdapter(LayoutInflater contentFragment, ArrayList<SaleItem> listRow) {
        //mInflater = LayoutInflater.from(contentFragment);
        mInflater = contentFragment;
        mSaleItems = listRow;
        storedItems = new ArrayList<>(listRow);
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
            holder.courseCode = (TextView)view.findViewById(R.id.courseCode);
            holder.title = (TextView)view.findViewById(R.id.textbookTitle);
            holder.price = (TextView)view.findViewById(R.id.price);
            holder.distance = (TextView)view.findViewById(R.id.distance);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        SaleItem item = mSaleItems.get(position);
        holder.courseCode.setText(item.courseCode);
        holder.title.setText(item.title);
        holder.price.setText(item.priceString);
        holder.distance.setText(item.distanceString);

        return view;
    }

    public Filter getFilter(){
        return myFilter;
    }

    private class ViewHolder {
        public TextView courseCode, title, price, distance;
    }
}

package com.example.android.thetdotexchange;

import android.content.Context;
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
    private ArrayList<SaleItem> originalItems;
    private ArrayList<SaleItem> filteredItems;
    private ItemFilter mFilter = new ItemFilter();

    public ListViewItemAdapter(Context context, ArrayList<SaleItem> listRow) {
        this.filteredItems = listRow;
        this.originalItems = listRow;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.courseCode = (TextView)convertView.findViewById(R.id.courseCode);
            holder.title = (TextView)convertView.findViewById(R.id.textbookTitle);
            holder.price = (TextView)convertView.findViewById(R.id.price);
            holder.distance = (TextView)convertView.findViewById(R.id.distance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        SaleItem item = filteredItems.get(position);
        holder.courseCode.setText(item.courseCode);
        holder.title.setText(item.title);
        holder.price.setText(item.priceString);
        holder.distance.setText(item.distanceString);

        return convertView;
    }

    public Filter getFilter(){
        return mFilter;
    }

    private class ViewHolder {
        public TextView courseCode, title, price, distance;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            constraint = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final ArrayList<SaleItem> list = originalItems;

            int count = list.size();
            final ArrayList<SaleItem> nlist = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                String courseCode = list.get(i).courseCode;
                String title = list.get(i).title;
                String priceString = list.get(i).priceString;
                String distanceString = list.get(i).distanceString;

                if (courseCode.toLowerCase().indexOf(constraint.toString()) >= 0 ||
                        title.toLowerCase().indexOf(constraint.toString()) >= 0 ||
                        priceString.toLowerCase().indexOf(constraint.toString()) >= 0 ||
                        distanceString.toLowerCase().indexOf(constraint.toString()) >= 0) {
                    nlist.add(list.get(i));
                }
            }

            results.count = nlist.size();
            results.values = nlist;

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            filteredItems = (ArrayList<SaleItem>) results.values;
            notifyDataSetChanged();
        }

    }
}

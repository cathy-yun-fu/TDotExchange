package com.example.android.thetdotexchange;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Cathy on 2015-09-12.
 */
public class ContentFragment extends Fragment{
    public static final int SORT_BY_COURSE_CODE = 0;
    public static final int SORT_BY_PRICE = 1;
    public static final int SORT_BY_DISTANCE = 2;

    ListView lv;
    ArrayList<SaleItem> listItems;
    ArrayList<SaleItem> restoreCopy;
    SearchView searchView;
    ListViewItemAdapter adapter;
    Spinner spinner;

    public ContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);

        listItems = new ArrayList<>();

        lv = (ListView)rootView.findViewById(R.id.listView);
        adapter = new ListViewItemAdapter(getActivity().getApplicationContext(), listItems);
        lv.setAdapter(adapter);

        sortListBy(SORT_BY_PRICE);

        spinner = (Spinner)rootView.findViewById(R.id.sortSelector);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(l == 0) {
                    System.out.println("Course Code");
                    sortListBy(SORT_BY_COURSE_CODE);
                    adapter.notifyDataSetChanged();
                }
                else if(l == 1) {
                    System.out.println("Price");
                    sortListBy(SORT_BY_PRICE);
                    adapter.notifyDataSetChanged();
                }
                else if(l == 2) {
                    System.out.println("Distance");
                    sortListBy(SORT_BY_DISTANCE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchView = (SearchView)rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    performSearch("");
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() != 0) {
                    System.out.println("--->" + query);
                    performSearch(query);
                    return true;
                } /*else if (query.length() == 0) {
                    performSearch("");
                    return true;
                }*/
                return false;
            }
        });

        return rootView;
    }

    public void performSearch(String searchQuery){
        //adapter.getFilter().filter(searchQuery);

        listItems.clear();
        for (SaleItem tempItem : restoreCopy) {
            listItems.add(new SaleItem(tempItem));
        }
        adapter.notifyDataSetChanged();

        if(searchQuery.equals("")){
            return;
        }

        searchView.clearFocus();
        String constraint = searchQuery.toLowerCase();

        for(Iterator<SaleItem> i = listItems.iterator(); i.hasNext();){
            SaleItem tempItem = i.next();
            String courseCode = tempItem.courseCode;
            String title = tempItem.title;
            String priceString = tempItem.priceString;
            String distanceString = tempItem.distanceString;

            if (!(courseCode.toLowerCase().contains(constraint) ||
                    title.toLowerCase().contains(constraint) ||
                    priceString.toLowerCase().contains(constraint)  ||
                    distanceString.toLowerCase().contains(constraint))) {
                i.remove();
            }
        }
        adapter.notifyDataSetChanged();
    }

    // 0 == sort by course code
    // 1 == sort by price
    // 2 == sort by distance
    // else sort by course
    public void sortListBy(final int sortBy){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SaleItem");
        switch(sortBy){
            case SORT_BY_COURSE_CODE:
                query.orderByAscending("courseCode");
                query.setLimit(20);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> itemList, ParseException e) {
                        if (e == null) {
                            listItems.clear();
                            for (int i = 0; i < itemList.size(); i++) {
                                ParseObject temp = itemList.get(i);
                                String cCode = temp.get("courseCode").toString();
                                String title = temp.get("title").toString();
                                double price = Double.parseDouble(temp.get("price").toString());
                                double lat = Double.parseDouble(temp.get("Lat").toString());
                                double lon = Double.parseDouble(temp.get("Lon").toString());
                                SaleItem toAdd = new SaleItem(cCode, title, price, lat, lon);
                                listItems.add(toAdd);
                            }
                            restoreCopy = new ArrayList<>();
                            restoreCopy.addAll(listItems);
                            adapter.notifyDataSetChanged();
                        } else {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                });
                break;
            case SORT_BY_PRICE:
                query.orderByAscending("price");
                query.setLimit(20);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> itemList, ParseException e) {
                        if (e == null) {
                            listItems.clear();
                            for (int i = 0; i < itemList.size(); i++) {
                                ParseObject temp = itemList.get(i);
                                String cCode = temp.get("courseCode").toString();
                                String title = temp.get("title").toString();
                                double price = Double.parseDouble(temp.get("price").toString());
                                double lat = Double.parseDouble(temp.get("Lat").toString());
                                double lon = Double.parseDouble(temp.get("Lon").toString());
                                SaleItem toAdd = new SaleItem(cCode, title, price, lat, lon);
                                listItems.add(toAdd);
                            }
                            restoreCopy = new ArrayList<>();
                            restoreCopy.addAll(listItems);
                            adapter.notifyDataSetChanged();
                        } else {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                });
                break;
            case SORT_BY_DISTANCE:
                break;
            default:
        }
    }
}

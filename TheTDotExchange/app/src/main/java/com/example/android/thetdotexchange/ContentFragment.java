package com.example.android.thetdotexchange;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Cathy on 2015-09-12.
 */
public class ContentFragment extends Fragment{
    public static final int SORT_BY_COURSE_CODE = 0;
    public static final int SORT_BY_PRICE = 1;
    public static final int SORT_BY_DISTANCE = 2;

    ListView lv;
    ArrayList<SaleItem> listItems;
    SearchView searchView;
    ListViewItemAdapter adapter;

    public ContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);

        listItems = new ArrayList<>();

        for(int i = 1; i <= 20; i++) {
            SaleItem temp = new SaleItem("CourseCode" + i, "Title" + i, Math.random()*100, Math.random()*10);
            listItems.add(temp);
        }

        lv = (ListView)rootView.findViewById(R.id.listView);
        adapter = new ListViewItemAdapter(inflater, listItems);
        lv.setAdapter(adapter);

        searchView = (SearchView)rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                performLiveSearch(newText);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() != 0) {
                    System.out.println("--->" + query);
                    // handle search here
                    performSearch(query);
                    return true;
                } else if (query.length() == 0 || query == null){
                    performSearch("");
                    return true;
                }
                return false;
            }
        });

        return rootView;
    }

    public void performSearch(String searchQuery){
        searchView.clearFocus();
        adapter.getFilter().filter(searchQuery);
        return;
    }

    public void performLiveSearch(String searchQuery) {
        adapter.getFilter().filter(searchQuery);
        return;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*ArrayList<SaleItem> lst = new ArrayList<SaleItem>();
        for(int i = 1; i <= 10; i++) {
            SaleItem temp = new SaleItem();
            temp.textBookTitle = "Test" + i;
            temp.sellerInfo = "Seller" + i;
            lst.add(temp);
        }

        ListViewItemAdapter adapter = new ListViewItemAdapter(this, lst);
        lv.setAdapter(adapter);*/
    }

    // 0 == sort by course code
    // 1 == sort by price
    // 2 == sort by distance
    // else sort by course
    public void sortListBy(final int sortBy){
        Collections.sort(listItems, new Comparator<SaleItem>(){
           public int compare(SaleItem item1, SaleItem item2){
               switch(sortBy) {
                   case 0:
                       return item1.courseCode.compareTo(item2.courseCode);
                   case 1:
                       if(item1.price < item2.price) return -1;
                       else if(item1.price == item2.price) return 0;
                       else return 1;
                   case 2:
                       if(item1.distance < item2.distance) return -1;
                       else if(item1.distance == item2.distance) return 0;
                       else return 1;
                   default:
                       return item1.courseCode.compareTo(item2.courseCode);
               }
           }
        });
    }
}

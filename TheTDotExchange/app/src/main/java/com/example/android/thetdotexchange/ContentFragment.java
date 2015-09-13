package com.example.android.thetdotexchange;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

        setUpMapIfNeeded();

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





    // MAP
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) (getActivity().getSupportFragmentManager().findFragmentById(R.id.map)))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        int[] lat= {0, 0, 3, 3};
        int[] lng= {0, 3, 0, 3};
        for(int i=0; i<4; i++)
        {
            onMapReady(mMap, i, lat[i], lng[i], i);
        }
    }

    public void onMapReady(GoogleMap map, int i, int lat, int lng, int price) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("ID: " + i)
                .snippet("Price: X" + price));
    }
}

package com.example.android.thetdotexchange;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cathy on 2015-09-12.
 */
public class ContentFragment extends Fragment{
    ListView lv;

    public ContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);

        ArrayList<SaleItem> listRow = new ArrayList<>();

        for(int i = 1; i <= 20; i++) {
            SaleItem temp = new SaleItem();
            temp.image = BitmapFactory.decodeResource(getResources(),R.mipmap.cat);
            temp.textBookTitle = "Test" + i;
            temp.sellerInfo = "Seller" + i;
            listRow.add(temp);
        }

        lv = (ListView)rootView.findViewById(R.id.listView);
        lv.setAdapter(new ListViewItemAdapter(inflater, listRow));

        return rootView;
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
}

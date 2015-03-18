package com.panda.coffeeorderadmin.app_admin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private ListView listView;
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://sweltering-torch-9870.firebaseio.com/");

        final List<Order> orders = new ArrayList<Order>();
        final OrderAdapter adapter = new OrderAdapter(this, orders, firebaseRef);

        listView = (ListView) findViewById(R.id.listOfOrders);

        firebaseRef.child("orders").addChildEventListener(new ChildEventListenerImpl() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                orders.add(0, parseOrder((Map)dataSnapshot.getValue(), dataSnapshot.getKey()));
                adapter.notifyDataSetChanged();
            }
        });

        listView.setAdapter(adapter);

    }

    private Order parseOrder(Map map, String id){
        String name = (String) map.get("name");
        Object orderStatusObj = map.get("orderStatus");
        String coffeeType = (String) map.get("coffeeType");
        return new Order(id, name, orderStatusObj == null ? 0 : (int) (long) (Long) orderStatusObj, coffeeType);
    }
}

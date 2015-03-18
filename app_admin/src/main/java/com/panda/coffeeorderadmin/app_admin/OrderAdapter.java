package com.panda.coffeeorderadmin.app_admin;

import android.app.DownloadManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.client.core.Repo;
import com.firebase.client.core.view.QueryParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Katsiaryna on 2/17/2015.
 */
public class OrderAdapter extends BaseAdapter{

    private List<Order> orders;
    private LayoutInflater layoutInflater;
    private Firebase firebaseRef;
    private final int ORDER_STATUS_CLOSED = 1;

    public OrderAdapter(Context context, List<Order> orders, Firebase firebaseRef) {
        this.orders = orders;
        this.firebaseRef = firebaseRef;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        final Order order = getOrderInfo(position);

        if (view == null)
            view = layoutInflater.inflate(R.layout.order_view, parent, false);

        final Button buttonDone = (Button)view.findViewById(R.id.buttonStatus);

        enableButton(order.getOrderStatus() != ORDER_STATUS_CLOSED, buttonDone);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableButton(false, buttonDone);

                Order order = getOrderInfo(position);
                order.setOrderStatus(ORDER_STATUS_CLOSED);

                orders.remove(position);

                firebaseRef.child("orders").child(order.getId()).child("orderStatus").setValue(ORDER_STATUS_CLOSED);

                notifyDataSetChanged();
            }

        });

        view.setTag(buttonDone);

        TextView textView = (TextView) view.findViewById(R.id.customerName);
        textView.setText(order.getName());

        TextView coffeeType = (TextView) view.findViewById(R.id.coffeeType);
        coffeeType.setText(order.getCoffeeType());

        return view;
    }

    private void enableButton(boolean enable, Button buttonDone) {
        if (!enable) {
            buttonDone.setEnabled(false);
            buttonDone.setText("Closed");
        }
        else {
            buttonDone.setEnabled(true);
            buttonDone.setText("Open");
        }
    }

    private Order getOrderInfo(int position) {
        return (Order)getItem(position);
    }
}

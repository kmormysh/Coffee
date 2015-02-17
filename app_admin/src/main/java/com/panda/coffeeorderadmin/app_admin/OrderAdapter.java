package com.panda.coffeeorderadmin.app_admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Katsiaryna on 2/17/2015.
 */
public class OrderAdapter extends BaseAdapter{

    private List<Order> orders;
    private LayoutInflater layoutInflater;

    public OrderAdapter(Context context, List<Order> orders) {
        this.orders = orders;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        Object o = orders.get(position);
        // heeeeeeeeeeeeeeeeeeellllllllllllpppppppppppppppppppppp

        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.order_view, parent, false);
        }

        Order order = getOrderInfo(position);
        TextView textView = (TextView) view.findViewById(R.id.customerName);
        textView.setText(order.getName());

        return view;
    }

    private Order getOrderInfo(int position) {
        Object o = getItem(position);
        return (Order)getItem(position);
    }
}

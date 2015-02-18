package com.panda.katsiaryna.coffeeorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

public class OrderActivity extends Activity {

    private Button orderButton;
    private EditText customerNameEdit;
    private Firebase firebaseRef;
    private String currentOrderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderButton = (Button)findViewById(R.id.orderButton);
        customerNameEdit = (EditText)findViewById(R.id.customerName);
        findViewById(R.id.rootLayout).getBackground().setAlpha(50);
        Firebase.setAndroidContext(this);

        firebaseRef = new Firebase("https://sweltering-torch-9870.firebaseio.com/");

        customerNameEdit.addTextChangedListener(new TextWatcherImp() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String name = customerNameEdit.getText().toString();
                if (name != null && name != ""){
                    orderButton.setEnabled(true);
                }
                else {
                    orderButton.setEnabled(false);
                }
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitOrder();
            }
        });
    }

    private void enableSubmitOrder(boolean enable) {
        if (enable){
            orderButton.setEnabled(true);
            orderButton.setText("Order!");
        }
        else {
            orderButton.setEnabled(false);
            orderButton.setText("Processing...");
        }
    }

    private void onSubmitOrder() {
        String name = customerNameEdit.getText().toString();
        Order order = new Order(name);
        Firebase firebase = firebaseRef.child("orders").push();
        firebase.setValue(order);
        currentOrderID = firebase.getKey();
        Toast.makeText(this, "Please, wait", Toast.LENGTH_SHORT).show();
        enableSubmitOrder(false);

        final Activity thisActivity = this;
        firebaseRef.child("orders").child(currentOrderID).addChildEventListener(new ChildEventListenerImpl() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals("orderStatus") && (long)(Long)dataSnapshot.getValue() == 1) {
                    enableSubmitOrder(true);
                    new AlertDialog.Builder(thisActivity)
                            .setTitle("Processed")
                            .setMessage("Your order is ready for pickup")
                            .setPositiveButton("Ok", null)
                            .show();
                }
            }
        });
    }
}


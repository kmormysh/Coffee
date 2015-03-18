package com.panda.katsiaryna.coffeeorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

public class OrderActivity extends Activity {

    private Button orderButton;
    private EditText customerNameEdit;
    private Firebase firebaseRef;
    private String currentOrderID;
    private ImageButton buttonAmericano;
    private ImageButton buttonBrewed;
    private ImageButton buttonCappuccino;
    private ImageButton buttonEspresso;
    private ImageButton buttonLatte;
    private ImageButton buttonMacchiato;
    private String coffeeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        addListenersOnButton();

        orderButton = (Button)findViewById(R.id.orderButton);
        customerNameEdit = (EditText)findViewById(R.id.customerName);
        Firebase.setAndroidContext(this);
        coffeeType = "";

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

    private void addListenersOnButton() {
        buttonAmericano = (ImageButton) findViewById(R.id.buttonAmericano);
        buttonAmericano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAlpha(buttonAmericano);
            }
        });

        buttonBrewed = (ImageButton) findViewById(R.id.buttonBrewed);
        buttonBrewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAlpha(buttonBrewed);
            }
        });

        buttonCappuccino = (ImageButton) findViewById(R.id.buttonCappuccino);
        buttonCappuccino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAlpha(buttonCappuccino);
            }
        });

        buttonEspresso = (ImageButton) findViewById(R.id.buttonEspresso);
        buttonEspresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAlpha(buttonEspresso);
            }
        });

        buttonLatte = (ImageButton) findViewById(R.id.buttonLatte);
        buttonLatte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAlpha(buttonLatte);
            }
        });

        buttonMacchiato = (ImageButton) findViewById(R.id.buttonMacchiato);
        buttonMacchiato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAlpha(buttonMacchiato);
            }
        });
    }

    private void changeAlpha(ImageButton button){
        if (button.getAlpha() != (float)0.3) {
            button.setAlpha((float) 0.3);
            coffeeType += " " + button.getContentDescription();
            Toast.makeText(OrderActivity.this, button.getContentDescription(), Toast.LENGTH_SHORT).show();
        }
        else {
            button.setAlpha((float) 1);
            coffeeType = coffeeType.replace(button.getContentDescription(),"");
            coffeeType = coffeeType.replace("  ", " ");
        }
    }

    private void enableSubmitOrder(boolean enable) {
        if (enable){
            buttonBrewed.setEnabled(true);
            buttonBrewed.setAlpha((float)1);
            buttonMacchiato.setEnabled(true);
            buttonMacchiato.setAlpha((float)1);
            buttonAmericano.setEnabled(true);
            buttonAmericano.setAlpha((float)1);
            buttonEspresso.setEnabled(true);
            buttonEspresso.setAlpha((float)1);
            buttonCappuccino.setEnabled(true);
            buttonCappuccino.setAlpha((float)1);
            buttonLatte.setEnabled(true);
            buttonLatte.setAlpha((float)1);

            orderButton.setEnabled(true);
            orderButton.setText("Order!");
            customerNameEdit.setEnabled(true);
        }
        else {
            buttonBrewed.setEnabled(false);
            buttonMacchiato.setEnabled(false);
            buttonAmericano.setEnabled(false);
            buttonEspresso.setEnabled(false);
            buttonCappuccino.setEnabled(false);
            buttonLatte.setEnabled(false);

            orderButton.setEnabled(false);
            orderButton.setText("Processing...");

            customerNameEdit.setEnabled(false);
        }
    }

    private void onSubmitOrder() {
        String name = customerNameEdit.getText().toString();
        Order order = new Order(name, coffeeType);
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
                    customerNameEdit.setText("");
                    coffeeType = "";
                }
            }
        });
    }
}


package com.panda.katsiaryna.coffeeorder;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class OrderActivity extends Activity {

    private Button orderButton;
    private EditText customerNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderButton = (Button)findViewById(R.id.orderButton);
        customerNameEdit = (EditText)findViewById(R.id.customerName);
        findViewById(R.id.rootLayout).getBackground().setAlpha(50);
        Firebase.setAndroidContext(this);


        customerNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //for DEBUG
        customerNameEdit.setText("Ekaterina");

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitOrder();
            }
        });
    }

    private void onSubmitOrder() {
        String name = customerNameEdit.getText().toString();
        Order order = new Order(name,0);
        Firebase firebaseRef = new Firebase("https://sweltering-torch-9870.firebaseio.com/");
        firebaseRef = firebaseRef.child("orders").push();
        firebaseRef.setValue(order);
        String id = firebaseRef.getKey();
        Toast.makeText(this, "order id: " + id, Toast.LENGTH_SHORT).show();
    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


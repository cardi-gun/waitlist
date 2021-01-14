package com.example.waitlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.waitlist.business.BusinessBookList;
import com.example.waitlist.customer.InputCode;

public class MainActivity extends AppCompatActivity {

    Button btnBusinessStart, btnCustomerStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBusinessStart = findViewById(R.id.btnBusinessStart);
        btnCustomerStart = findViewById(R.id.btnCustomerStart);

        btnBusinessStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBusin = new Intent(getApplicationContext(), BusinessBookList.class);
                startActivity(intentBusin);
            }
        });

        btnCustomerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInput = new Intent(getApplicationContext(), InputCode.class);
                startActivity(intentInput);
            }
        });

    }
}

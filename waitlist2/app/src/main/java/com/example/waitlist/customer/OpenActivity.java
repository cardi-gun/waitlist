package com.example.waitlist.customer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.waitlist.R;

public class OpenActivity extends AppCompatActivity {

    Button locationButton;
    Button bookingCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        //가기 위치 버튼 클릭시 이벤트
        locationButton = findViewById(R.id.storeLocationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://map.naver.com/v5/search/%EC%82%BC%EA%B0%81%EC%A7%80%20%EC%A3%BC%EC%8B%9D/place/1033173704?c=14130693.6626242,4513940.9322384,13,0,0,0,dh&placePath=%3F%2526"));
                startActivity(locationIntent);
            }
        });

        //예약 취소 버튼 클릭시 이벤트
        bookingCancelButton = findViewById(R.id.bookingCancelButton);
        bookingCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼 클릭시 cancel_dialog.xml 띄우기
                final AlertDialog.Builder bookingCancelBuilder = new AlertDialog.Builder(OpenActivity.this);
                View bookingCancelDialog = View.inflate(OpenActivity.this, R.layout.cancel_dialog, null);
                bookingCancelBuilder.setView(bookingCancelDialog);
                bookingCancelBuilder.show();
            }
        });
    }
}
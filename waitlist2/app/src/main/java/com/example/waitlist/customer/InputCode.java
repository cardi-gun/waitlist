package com.example.waitlist.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.waitlist.R;
import com.example.waitlist.business.BookListVO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InputCode extends AppCompatActivity {
    EditText code1, code2, code3, peopleNumEditText; //코드 및 인원 입력 란
    Button bookingButton; //input_code 레이아웃의 예약하기 버튼 id
    String[] demoCode = {"111", "222", "333"}; // 코드 확인을 위한 임의 배열
    String code_1, code_2, code_3;
    DatabaseReference database;
    //bookNum과 동일한 값으로 저장
    int i = 0;//시작값을 1로 시작하여 리스트에 담은 길이 카운트
    ArrayList<BookListVO> arrayList;//데이터를 리스트에 담기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);

        code1 = findViewById(R.id.code1);
        code2 = findViewById(R.id.code2);
        code3 = findViewById(R.id.code3);
        peopleNumEditText = findViewById(R.id.peopleNumEditText);
        bookingButton = findViewById(R.id.bookingButton);

        //파이어베이스
        database = FirebaseDatabase.getInstance().getReference("booklist");
        arrayList = new ArrayList<>();

        //예약하기 버튼 클릭시
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                code_1 = code1.getText().toString();
                code_2 = code2.getText().toString();
                code_3 = code3.getText().toString();

                //파이어베이스의 데이터를 가져와서 리스트에 담고 그 크기의 다음 번호로 사이즈를 i에 담는다.
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayList.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            BookListVO bookListVO = snapshot1.getValue(BookListVO.class);
                            arrayList.add(bookListVO);
                        }
                        i = arrayList.size();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (code_1.equals(demoCode[0])) {
                    if (code_2.equals(demoCode[1])) {
                        if (code_3.equals(demoCode[2])) {

                            i++;//파이어베이스의 다음 번호로 증가

                            //모든 코드가 맞았으면 대기 activity로 이동
                            Intent BookingActivityIntent = new Intent(getApplicationContext(), Booking_check.class);
                            BookingActivityIntent.putExtra("position", i);
                            startActivity(BookingActivityIntent);

                            //파이어베이스에 저장되는 데이터 변수
                            String bookNum =  Integer.toString(i);// 예약번호
                            Date bookTime; //예약시간
                            Date cencelTime; //취소시간
                            int enterState = 0; //입장상태

                            //현재 날짜를 만드는 포맷
//                SimpleDateFormat dayTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //현재 시간을 만드는 포맷
                            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
                            //현재 시간 구하여 저장
                            long presnetTime = System.currentTimeMillis();
                            //현재 시간을 날짜로 Date로 변환
                            bookTime = new Date(presnetTime);
                            //현재 시간에서 날짜만 저장되로록 포맷 변환
                            String bookTimeString = timeFormat.format(bookTime);
                            //취소 시간을 예약시간과 동일한 시간으로 일단 저장
                            cencelTime = new Date(presnetTime);
                            //현재 시간에서 날짜만 저장되로록 포맷 변환
                            String cencelTimeString = timeFormat.format(cencelTime);

                            //사용자에게 입력받은 인원 가져오기
                            int getPeopleNumEditText = Integer.parseInt(peopleNumEditText.getText().toString());

                            //예약하기 버튼 클릭시 시간과 대기번호가 저장되는 함수
                            inputTimeAndWaittingNumber(bookNum, bookTimeString, getPeopleNumEditText, cencelTimeString, enterState);
                        } else {
                            Toast.makeText(InputCode.this, "마지막 코드를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }//3번째 code 확인 if문
                    } else {
                        Toast.makeText(InputCode.this, "가운데 코드를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }//2번째 code 확인 if문
                } else {
                    Toast.makeText(InputCode.this, "시작 코드를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }//1번째 코드 확인 if문

            }//onClick
        });//bookingButton
    }//onCreate

    //파이어 베이스 예약하기 버튼 클릭시 시간과 대기번호가 저장되는 함수
    private void inputTimeAndWaittingNumber(String bookNum, String bookTime, int getPeopleNumEditText, String cencelTime, int enterState) {

        BookListVO bookListVO = new BookListVO(bookNum, bookTime, getPeopleNumEditText, cencelTime, enterState);

        database.child(String.valueOf(i)).setValue(bookListVO)//데이터 베이스에 userId(총 길이 기준)에서 +1 되어서 데이터를 넣어라
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("파이어베이스!!!!!!!!!!!!!!", "데이터 저장 성공!!!!!!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("파이어베이스------------", "데이터 저장실패");
            }
        });
    }//inputTimeAndWaittingNumber
}//InputCode 액티비티
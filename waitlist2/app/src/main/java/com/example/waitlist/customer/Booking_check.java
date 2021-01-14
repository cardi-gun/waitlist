////package com.example.waitlist.customer;
////
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////
////import android.content.Intent;
////import android.os.Bundle;
////import com.example.waitlist.R;
////import android.os.Handler;
////import android.os.Message;
////import android.util.Log;
////
////
////public class Booking_check extends AppCompatActivity {
////    Intent intent;
////    int position;
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_booking_check);
////        Log.d("파이어베이스>>>","대기화면 도착했습니다.");
////
////        intent = getIntent();
////        position = intent.getExtras().getInt("position");
////        Log.d("파이어베이스>>>","포지션 넘어왔습니다 실행합니다.");
////
////        WaitThread waitThread = new WaitThread(handler, position);
////        waitThread.start();
////        Log.d("파이어베이스>>>","포지션 넘어왔습니다 스레드 실행합니다.");
////    }
////    Handler handler = new Handler(){
////        @Override
////        public void handleMessage(@NonNull Message msg) {
////            Log.d("파이어베이스>>>","스래드 실행합니다.");
////            if (msg.what == 1) {//메세지 1을 받게 되면
////                // 다음화면 으로 연결
////                Intent intent = new Intent(getApplicationContext(), OpenActivity.class);
////                startActivity(intent);
////            }
////        }
////    };
////}
//
//package com.example.waitlist.customer;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//
//import com.example.waitlist.R;
//import com.example.waitlist.business.BookListVO;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class Booking_check extends AppCompatActivity {
//    DatabaseReference database;
//    ArrayList<BookListVO> arrayList;
//    int listSize = 1;
//    Intent intent;
//    int position;
//    private static Handler mHandler ;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        intent = getIntent();
//        position = intent.getExtras().getInt("position");
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking_check);
//
//        mHandler = new Handler();
//
//        database = FirebaseDatabase.getInstance().getReference("booklist"); //파이어베이스 객체 가져오기
//        Log.d("파이어베이스>>", database + " ");
//
//        arrayList = new ArrayList<BookListVO>();
//
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override // 값 가져오는 메서드
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //유저들의 목록을 가지고 오는 메서드
//                arrayList.clear();
//                //파이어베이스의 등록된 json을 다 가져와라
//                Log.d("파이어베이스>>", "booklist 아래의 자식들의 개수" + snapshot.getChildrenCount());
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {//DataSnapshot - for문으로 목록에 들어있는 user를 한명씩 꺼내줌.
//                    //snapshot.getChildren() - booklist아래에 있는 booklist목록을 다 가지고 온다.
//                    //booklist의 값들을 가지고와서, BooklistVO에 넣는다.
//                    //getValue(BooklistVO.class) - 해당하는 멤버변수와 동일한 set메서드를 자동으로 부른다.
//                    BookListVO bookListVO = snapshot1.getValue(BookListVO.class);
//                    arrayList.add(bookListVO);//arrayList에 담기
//                    Log.d("파이어베이스>>", "booklist 1명:" + bookListVO);
//                }
//
//                //i를 현재 파이어베이스가 가지고 있는 데이터 총 사이즈로 초기화
//                listSize = arrayList.size(); // getChildrenCount() 와 같다.
//            }
//
//            @Override // 실패했을때 실행하는 메서드
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });//database
//
//        // 핸들러로 전달할 runnable 객체. 수신 스레드 실행.
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                //데이터베이스의 길이만큼 반복 확인
////                for (int i = listSize; listSize <= arrayList.size(); listSize++){
//                //InputCode에서 넘어온 position값이 enterState(입장) 상태값인 '1'과 같아면 다음 액티비티로 이동
//                Log.d("어레이리스트 :::  <<< ",arrayList.size()+"");
//                Log.d("포지션값 :::  <<< ",position+"");
//                Log.d("어레~~~ 0번쨰!!!! ",arrayList.get(position-1).getBookNum()+"");
//                if ((arrayList.get(position-1).getBookNum().equals(String.valueOf(position)))){
//                    Log.d("첫번째 if 들어옴 : ","오예111111");
//                    if((arrayList.get(position -1).getEnterState() == 1)){
//                        Log.d("두번째 if 들어옴 : ","오예111111");
//                        Intent openActivityIntent = new Intent(getApplicationContext(), OpenActivity.class);
//                        startActivity(openActivityIntent);
//                    }
//                }//if문
////                }//for문
//            }//run()
//        };//Runnable
//
//        // 새로운 스레드 실행 코드. 1초 단위로 현재 시각 표시 요청.
//        class NewRunnable implements Runnable {
//            @Override
//            public void run() {
//                while (true) {
//
//                    try {
//                        Thread.sleep(10000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    mHandler.post(runnable);
//                }
//            }
//        }//NewRunnable 클라스
//
//        NewRunnable nr = new NewRunnable();
//        Thread t = new Thread(nr);
//        t.start();
//
//    }//onCreate
//}

package com.example.waitlist.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.waitlist.R;
import com.example.waitlist.business.BookListVO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Booking_check extends AppCompatActivity {
    DatabaseReference database;
    ArrayList<BookListVO> arrayList;
    int listSize = 1;
    Intent intent;
    public static Handler mHandler ;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        final int position = intent.getExtras().getInt("position");
        Log.d("position이란!!!!!!!! ",position+"");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_check);

        mHandler = new Handler();

        arrayList = new ArrayList<>();
//        Log.d("어레이리스트 담는 것 체크--", arrayList + " ");

        database = FirebaseDatabase.getInstance().getReference("booklist"); //파이어베이스 객체 가져오기
        Log.d("파이어베이스>>", database + " ");

        dataRead();

        // 핸들러로 전달할 runnable 객체. 수신 스레드 실행.
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                파이어베이스에 데이터를 읽어서 오는 함수
                dataRead();
//                데이터베이스의 길이만큼 반복 확인
//                  InputCode에서 넘어온 position값이 enterState(입장) 상태값인 '1'과 같아면 다음 액티비티로 이동
                Log.d("어레이리스트 :::  <<< ",arrayList.size()+"");
                Log.d("포지션값 :::  <<< ",position+"");
                Log.d("어레~~~ 0번쨰!!!! ",arrayList.get(position-1).getBookNum()+"");
                if ((arrayList.get(position-1).getBookNum().equals(String.valueOf(position)))){
                    Log.d("첫번째 if 들어옴 : ","오예111111");
                    Log.d("getEnterStater 값::: ",(arrayList.get(position-1).getEnterState())+" ");
                    if((arrayList.get(position-1).getEnterState() == 1)){

                        Log.d("두번째 if 들어옴 : ","오예111111");
                        Intent openActivityIntent = new Intent(getApplicationContext(), OpenActivity.class);
                        startActivity(openActivityIntent);
                        //페이지가 넘어가면 스레드 종료
                    }
                }//첫번째 if문
            }//run()
        };//Runnable

        // 새로운 스레드 실행 코드. 1초 단위로 현재 시각 표시 요청.
        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mHandler.post(runnable);
                }
            }
        }//NewRunnable 클라스

        NewRunnable nr = new NewRunnable();
        t = new Thread(nr);
        t.start();

    }//onCreate

    public void dataRead() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override // 값 가져오는 메서드
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //유저들의 목록을 가지고 오는 메서드
                arrayList.clear();
                //파이어베이스의 등록된 json을 다 가져와라
                Log.d("파이어베이스>>", "booklist 아래의 자식들의 개수" + snapshot.getChildrenCount());
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {//DataSnapshot - for문으로 목록에 들어있는 user를 한명씩 꺼내줌.
                    //snapshot.getChildren() - booklist아래에 있는 booklist목록을 다 가지고 온다.
                    //booklist의 값들을 가지고와서, BooklistVO에 넣는다.
                    //getValue(BooklistVO.class) - 해당하는 멤버변수와 동일한 set메서드를 자동으로 부른다.
                    BookListVO bookListVO = snapshot1.getValue(BookListVO.class);
                    arrayList.add(bookListVO);//arrayList에 담기
                    Log.d("파이어베이스>>", "booklist 1명:" + bookListVO);
                }

                //i를 현재 파이어베이스가 가지고 있는 데이터 총 사이즈로 초기화
                listSize = arrayList.size(); // getChildrenCount() 와 같다.
            }

            @Override // 실패했을때 실행하는 메서드
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "데이터 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        });//database
    }//dataRead

}

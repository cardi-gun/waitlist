//package com.example.waitlist.customer;
//
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.example.waitlist.business.BookListVO;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.ValueEventListener;
//
//
//public class WaitThread extends Thread {
//    DatabaseReference database;
//    boolean flag;
//    private Handler handler;
//    private int position;
//
//    public WaitThread(Handler handler, int position) {
//        this.handler = handler;
//        this.position = position;
//    }
//
//    @Override
//    public void run() {
//        //판단할 객체
//        flag = false;
//        //전달할 메세지 객체
//        Message msg = new Message();
//        try {//3초뒤에 화면 전환
//            Thread.sleep(3000);
//            Log.d("파이어베이스>>>", "sleep 실행");
//            //여기서 판단 할것
//            Log.d("파이어베이스>>>", "포지션 값은 " + position);
//            //String positionStr = Integer.toString(position);
//
//            database.child("booklist").orderByChild("bookNum").equalTo(String.valueOf(position)).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    flag = true;
//                    Log.d("파이어베이스>>>", "검색 성공");
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Log.w("파이어베이스>>", "loadPost:onCancelled");
//                    flag = false;
//                    Log.d("파이어베이스>>>", "검색 실패");
//                }
//            });
//            Log.d("파이어베이스>>>", "스래드 반복문 판단");
//
//
//            Log.d("파이어베이스>>>", "스래드 성공");
//            msg.what = 1;//메세지 1을 전달
//            handler.sendEmptyMessage(msg.what);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}





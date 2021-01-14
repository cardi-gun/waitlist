package com.example.waitlist.business;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.waitlist.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class BusinessBookList extends TabActivity {

    DatabaseReference database;
    ArrayList<BookListVO> bookArrayList, cancelArrayList;
    BookListAdapter bookListAdapter;
    CancelListAdapter cancelListAdapter;

    Button btnBook, btnEnter, btnCancel, startDB, stopDB;
    ListView booklist, cancelList;
    TextView bclCancelState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessmain);
        TabHost tabHost = getTabHost();

        //각각의 탭을 정의
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("wait").setIndicator("대기목록");
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("cancel").setIndicator("취소목록");
        //tab1이 들어갈곳 정의
        tabSpec1.setContent(R.id.tab1);
        tabSpec2.setContent(R.id.tab2);
        //tabhost에 삽입
        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);

        tabHost.setCurrentTab(0);

        //1. 데이터를 지정
        //2. view객체 지정
        booklist = findViewById(R.id.bookList);
        bookArrayList = new ArrayList<>();

        cancelList = findViewById(R.id.cancelList);
        cancelArrayList =  new ArrayList<>();
        bclCancelState = findViewById(R.id.bclCancelState);

        Log.d("파이어베이스>>", "실행번호 " + 1);
        database = FirebaseDatabase.getInstance().getReference("");
        startDB = findViewById(R.id.startDB);
        stopDB = findViewById(R.id.stopDB);

        startDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookArrayList.clear();
                readWait();
                readPermission();

                final AlertDialog.Builder aBuilder = new AlertDialog.Builder(BusinessBookList.this);
                aBuilder.setTitle("영업시작").setMessage("시작하시겠습니까.");
                aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        readBookAdapter();
                    }
                });
                AlertDialog alertDialog = aBuilder.create();
                alertDialog.show();
            }
        });

        stopDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //리스트를 없애고,
                bookArrayList.clear();
                //어댑터를 refresh!
                bookListAdapter.notifyDataSetChanged();
            }
        });


        readCancel();
        readCancelAdapter();


    }//onCreate

    private void readBookAdapter() {
        Log.d("파이어베이스>>", "실행번호 " + 4);
        Log.d("파이어베이스>>", "booklist 최종 길이 " + bookArrayList);

        //3. adapter를 생성
        //final BookListAdapter
        bookListAdapter = new BookListAdapter(getApplicationContext());
        //4. view객체에 adapter지정
        booklist.setAdapter(bookListAdapter);
    }

    private void readCancelAdapter() {
        Log.d("파이어베이스>>", "실행번호 " + 4);
        Log.d("파이어베이스>>", "canclelist 최종 길이 " + cancelList);

        //3. adapter를 생성
        //final BookListAdapter
        cancelListAdapter = new CancelListAdapter(getApplicationContext());
        //4. view객체에 adapter지정
        cancelList.setAdapter(cancelListAdapter);

    }

    private void readWait() {
        //bookArrayList = new ArrayList<>();
        database.child("booklist").orderByChild("enterState").equalTo(0).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("파이어베이스>>", "실행번호 " + 2);
                Log.d("파이어베이스>>", "0 갯수 : " + snapshot.getChildrenCount());
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    BookListVO vo = snapshot1.getValue(BookListVO.class);
                    bookArrayList.add(vo);
                    Log.d("파이어베이스>>", "booklist : " + vo);
                }
                Log.d("파이어베이스>>", "booklist 길이 " + bookArrayList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("파이어베이스>>", "loadPost:onCancelled");
            }
        });
    }

    private void readPermission() {
        database.child("booklist").orderByChild("enterState").equalTo(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("파이어베이스>>", "실행번호 " + 3);
                Log.d("파이어베이스>>", "1 갯수 : " + snapshot.getChildrenCount());
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    BookListVO vo = snapshot1.getValue(BookListVO.class);
                    bookArrayList.add(vo);
                    Log.d("파이어베이스>>", "booklist : " + vo);
                }
                Log.d("파이어베이스>>", "booklist 길이 " + bookArrayList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("파이어베이스>>", "loadPost:onCancelled");
            }
        });
    }

    private void readCancel() {
        database.child("booklist").orderByChild("enterState").equalTo(3).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("파이어베이스>>", "실행번호 " + 3);
                Log.d("파이어베이스>>", "1 갯수 : " + snapshot.getChildrenCount());
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    BookListVO vo = snapshot1.getValue(BookListVO.class);
                    cancelArrayList.add(vo);
                    Log.d("파이어베이스>>", "booklist : " + vo);
                }
                Log.d("파이어베이스>>", "booklist 길이 " + cancelArrayList.size());
                bclCancelState.setText("오늘 예약취소 건 : "+cancelArrayList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("파이어베이스>>", "loadPost:onCancelled");
            }
        });
    }


    public class BookListAdapter extends BaseAdapter {
        Context context;

        public BookListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return bookArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View booklistView = View.inflate(getApplicationContext(), R.layout.booklistlayout, null);
            //각 요소 구현
            TextView bookNumText = booklistView.findViewById(R.id.TvBookNumText);
            TextView bookTimeText = booklistView.findViewById(R.id.TvBookTimeText);
            TextView enterStateText = booklistView.findViewById(R.id.TvEnterstatusText);

            TextView bookNum = booklistView.findViewById(R.id.TvBookNum);
            TextView bookTime = booklistView.findViewById(R.id.TvBookTime);
            TextView enterState = booklistView.findViewById(R.id.TvEnterstatus);

            btnBook = booklistView.findViewById(R.id.BtnBook);
            btnEnter = booklistView.findViewById(R.id.BtnEnter);
            btnCancel = booklistView.findViewById(R.id.BtnCancel);

            //tv 안에 내용물 어떻게 넣을지, 어디서 받아서 어떻게 입력할지 구현하기
            bookNumText.setText("예약번호 : ");
            bookTimeText.setText("예약시간 : ");
            enterStateText.setText("입장시간 : ");

            bookNum.setText(bookArrayList.get(position).getBookNum());
            bookTime.setText(bookArrayList.get(position).getBookTime());
            //int형 변환
            if(bookArrayList.get(position).getEnterState()==0){
                enterState.setText("예약 요청 중");
            }else{
                enterState.setText("예약 확인");
            }


            btnBook.setText("확인");
            btnEnter.setText("입장");
            btnCancel.setText("취소");

            //확인 눌렀을 때
            btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "확인눌림", Toast.LENGTH_SHORT).show();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("enterState",1);
                    Log.d("파이어베이스>>>", "포지션값"+position);
                    Log.d("파이어베이스>>>", "글자 포지션값"+bookArrayList.get(position).getBookNum());
                    int positionInt = Integer.parseInt(bookArrayList.get(position).getBookNum());
                    String positionStr = Integer.toString(positionInt);
                    Log.d("파이어베이스>>>", "바뀐 포지션값"+positionStr);

                    database.child("booklist").child(positionStr).updateChildren(map);
                    Log.d("파이어베이스>>>", "map값"+map);
                    bookListAdapter.notifyDataSetChanged();
                }
            });
            //입장 눌렀을 때
            btnEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "입장눌림", Toast.LENGTH_SHORT).show();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("enterState",2);
                    Log.d("파이어베이스>>>", "포지션값"+position);
                    Log.d("파이어베이스>>>", "글자 포지션값"+bookArrayList.get(position).getBookNum());
                    int positionInt = Integer.parseInt(bookArrayList.get(position).getBookNum());
                    String positionStr = Integer.toString(positionInt);
                    Log.d("파이어베이스>>>", "바뀐 포지션값"+positionStr);

                    database.child("booklist").child(positionStr).updateChildren(map);
                    Log.d("파이어베이스>>>", "map값"+map);
                    bookListAdapter.notifyDataSetChanged();
                }
            });
            //취소 눌렀을 때
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "취소눌림", Toast.LENGTH_SHORT).show();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("enterState",3);
                    Log.d("파이어베이스>>>", "포지션값"+position);
                    Log.d("파이어베이스>>>", "글자 포지션값"+bookArrayList.get(position).getBookNum());
                    int positionInt = Integer.parseInt(bookArrayList.get(position).getBookNum());
                    String positionStr = Integer.toString(positionInt);
                    Log.d("파이어베이스>>>", "바뀐 포지션값"+positionStr);

                    database.child("booklist").child(positionStr).updateChildren(map);
                    Log.d("파이어베이스>>>", "map값"+map);
                    bookListAdapter.notifyDataSetChanged();
                }
            });

            return booklistView;
        }
    }

    public class CancelListAdapter extends BaseAdapter {
        Context context;

        public CancelListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return cancelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View cancellistView = View.inflate(getApplicationContext(), R.layout.cancellistlayout, null);
            //각 요소 구현
            TextView bookNumText2 = cancellistView.findViewById(R.id.TvBookNumText2);
            TextView cancelTimeText = cancellistView.findViewById(R.id.TvCancelTimeText);

            TextView bookNum2 = cancellistView.findViewById(R.id.TvBookNum2);
            TextView cancelTime = cancellistView.findViewById(R.id.TvCancelTime);

            //tv 안에 내용물 어떻게 넣을지, 어디서 받아서 어떻게 입력할지 구현하기
            bookNumText2.setText("예약번호 : ");
            cancelTimeText.setText("취소시간 : ");

            bookNum2.setText(cancelArrayList.get(position).getBookNum());
            cancelTime.setText(cancelArrayList.get(position).getBookTime());

            return cancellistView;
        }
    }


}

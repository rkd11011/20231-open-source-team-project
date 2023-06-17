/* **********************************************
 * 프로젝트명 :  SunganSungan
 * 작성자 : 2017015041 조정동
 * 최종작성일 : 2023.06. 11.
 * 함수설명 :  게시물 목록에서 클릭 시 게시물 출력/삭제 기능
 ************************************************/

package com.example.sungansungan12;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostShowActivity extends AppCompatActivity {
    private TextView titleTextView;
    private ImageView photoImageView;
    private TextView contentTextView;
    private TextView availabilityTextView;
    private TextView priceTextView;
    private ImageView homeButton;
    private Button deleteButton; // 삭제 버튼 추가
    private TextView userIdTextView;
    //강수 추가 수정 체팅기능----------------------------------------------------------------------------------
    private Button customButton;
    //강수 추가 수정 체팅기능----------------------------------------------------------------------------------

    // 삭제 버튼 클릭 시 게시물 삭제하는 메서드
    private void deletePost(String postName) {
        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("posts");
        postRef.orderByChild("name").equalTo(postName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    postSnapshot.getRef().removeValue();
                }
                // 게시물 삭제 후 홈 화면으로 이동
                Intent intent = new Intent(PostShowActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 삭제 실패 시 처리할 내용 작성
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_show);

        titleTextView = findViewById(R.id.titleTextView);
        photoImageView = findViewById(R.id.photoImageView);
        contentTextView = findViewById(R.id.contentTextView);
        availabilityTextView = findViewById(R.id.availabilityTextView);
        priceTextView = findViewById(R.id.priceTextView);
        homeButton = findViewById(R.id.homeButton);
        userIdTextView = findViewById(R.id.userIdTextView);
        //강수 추가 수정 체팅기능----------------------------------------------------------------------------------
        customButton = findViewById(R.id.customButton);
        //강수 추가 수정 체팅기능----------------------------------------------------------------------------------
        deleteButton = findViewById(R.id.deleteButton); // 삭제 버튼 초기화

        Intent intent = getIntent();
        String selectedPost = intent.getStringExtra("selectedPost");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.orderByChild("name").equalTo(selectedPost).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String postTitle = postSnapshot.child("name").getValue(String.class);
                    String postContent = postSnapshot.child("description").getValue(String.class);
                    String postImageUrl = postSnapshot.child("imageUrl").getValue(String.class);
                    String available = postSnapshot.child("available").getValue(String.class);
                    String price = postSnapshot.child("price").getValue(String.class);

                    titleTextView.setText(postTitle);
                    contentTextView.setText(postContent);
                    availabilityTextView.setText("대여기간:   "+available+"일");
                    priceTextView.setText("가격:   "+ price+"원");
                    Glide.with(PostShowActivity.this).load(postImageUrl).into(photoImageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 로드 실패 시 처리할 내용 작성
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostShowActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // 올린 사람 ID 가져오기
        DatabaseReference uploaderRef = FirebaseDatabase.getInstance().getReference("posts");
        uploaderRef.orderByChild("name").equalTo(selectedPost).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String uploaderId = postSnapshot.child("userId").getValue(String.class);

                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uploaderId);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String userName = dataSnapshot.child("name").getValue(String.class);
                                userIdTextView.setText(userName);

                                // 올린 사람과 로그인한 사람이 일치하는 경우에만 삭제 버튼을 활성화
                                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                if (currentUserId.equals(uploaderId)) {
                                    deleteButton.setVisibility(View.VISIBLE);
                                    deleteButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 게시물 삭제 로직 추가
                                            deletePost(selectedPost);
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // 데이터 로드 실패 시 처리할 내용 작성
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 로드 실패 시 처리할 내용 작성
            }
        });

        //강수 추가 수정 체팅기능----------------------------------------------------------------------------------
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 로그인한 사용자 ID 가져오기
                final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // 해당 게시물의 주인 ID와 제목 가져오기
                DatabaseReference uploaderRef = FirebaseDatabase.getInstance().getReference("posts");
                uploaderRef.orderByChild("name").equalTo(selectedPost).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String uploaderId = postSnapshot.child("userId").getValue(String.class);
                            String postTitle = postSnapshot.child("name").getValue(String.class);

                            // 채팅룸 생성
                            DatabaseReference chatRoomRef = FirebaseDatabase.getInstance().getReference("ChatRooms");
                            String chatRoomId = chatRoomRef.push().getKey(); // 채팅룸의 고유 ID 생성

                            // 채팅룸에 사용자 추가
                            chatRoomRef.child(chatRoomId).child("users").child(currentUserId).setValue(true);
                            chatRoomRef.child(chatRoomId).child("users").child(uploaderId).setValue(true);

                            // 채팅룸 이름 설정 (게시물 제목)
                            chatRoomRef.child(chatRoomId).child("name").setValue(postTitle);

                            // ChatRoomActivity로 이동
                            Intent intent = new Intent(PostShowActivity.this, ChatRoomActivity.class);
                            intent.putExtra("chatRoomId", chatRoomId); // 생성된 채팅룸의 ID를 전달
                            intent.putExtra("chatRoomName", postTitle);//이름전달
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 데이터 로드 실패 시 처리할 내용 작성
                    }
                });
            }
        });
    }
}
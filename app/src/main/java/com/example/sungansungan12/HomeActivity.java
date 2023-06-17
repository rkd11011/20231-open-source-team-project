/* **********************************************
 * 프로젝트명 :  SunganSungan
 * 작성자 : 2017015040 강수
 * 최종작성일 : 2023.05. 28.
 * 함수설명 :  메인 홈 화면 출력
 ************************************************/

package com.example.sungansungan12;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    AppCompatButton navi;
    AppCompatButton upload;
    AppCompatButton profile;
    AppCompatButton homeChatlistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Firebase 인증 정보 가져오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // 사용자 로그인 정보 출력
            Log.i("SunganLog", "HomeActivity 실행");
            Log.i("SunganLog", "로그인 중");
            String email = user.getEmail();
            String uid = user.getUid();
            Log.i("SunganLog", "로그인 된 사용자: " + email + " 고유식별자: " + uid);
            boolean emailVerified = user.isEmailVerified();
            Log.i("SunganLog", "로그인정보 호출 성공");
        } else {
            Log.e("SunganLog", "로그인정보 호출 실패");
        }

        // 물품 탐색 버튼
        navi = findViewById(R.id.homeNaviButton);
        // 물품 등록 버튼
        upload = findViewById(R.id.homeUploadButton);
        // 개인 정보 수정 버튼
        profile = findViewById(R.id.homeProfileButton);

        navi.setOnClickListener(v -> {
            Intent intent = new Intent(this, NaviActivity.class);
            startActivity(intent);
            Log.i("SunganLog", "HomeActivity 백그라운드");
        });

        upload.setOnClickListener(v -> {
            Intent intent = new Intent(this, UploadActivity.class);
            startActivity(intent);
            Log.i("SunganLog", "HomeActivity 백그라운드");
        });

        profile.setOnClickListener(v -> {
            Intent intent = new Intent(this, profileActivity.class);
            startActivity(intent);
            Log.i("SunganLog", "HomeActivity 백그라운드");
        });

        // Chat Room List 버튼
        homeChatlistButton = findViewById(R.id.homeChatlistButton);
        homeChatlistButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ChatRoomListActivity.class);
            startActivity(intent);
            Log.i("SunganLog", "Chat Room List 버튼 클릭");
        });

        // 로그아웃 버튼
        AppCompatButton logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            // Firebase에서 로그아웃 처리
            FirebaseAuth.getInstance().signOut();

            // 로그인 화면으로 이동
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // 현재 화면 종료하여 뒤로 가기로 돌아오면 로그인 화면으로 돌아가도록 함
        });

    }
}
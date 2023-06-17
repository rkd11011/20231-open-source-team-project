/* **********************************************
 * 프로젝트명 :  SunganSungan
 * 작성자 : 2017015041 조정동
 * 최종작성일 : 2023.06. 10.
 * 함수설명 :  게시물 업로드
 ************************************************/

package com.example.sungansungan12;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.sungansungan12.UploadActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.sungansungan12.R;

import java.util.UUID;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UploadActivity extends AppCompatActivity {
    ImageButton home;
    AppCompatButton uploadPOST;
    EditText productName, productDescription, productPrice, productAvailable;
    ImageView productImage;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST_CODE = 1;
    String imageUrl;
    Uri imageUri; // imageUri 변수 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        home = findViewById(R.id.homeButton);
        uploadPOST = findViewById(R.id.uplodePOSTBt);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        productAvailable = findViewById(R.id.productAvailable);
        productImage = findViewById(R.id.productImageView);

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        storageReference = FirebaseStorage.getInstance().getReference("product_images");

        home.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

        productImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "이미지 선택"), IMAGE_REQUEST_CODE);
        });

        uploadPOST.setOnClickListener(v -> {
            String name = productName.getText().toString();
            String description = productDescription.getText().toString();
            String price = productPrice.getText().toString();
            String available = productAvailable.getText().toString();

            if (imageUri != null) { // 이미지가 선택된 경우에만 업로드 및 저장 작업 수행
                StorageReference fileReference = storageReference.child(UUID.randomUUID().toString());
                UploadTask uploadTask = fileReference.putFile(imageUri);

                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        imageUrl = uri.toString();

                        // 사용자 UID 가져오기
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            String userId = currentUser.getUid();

                            // 게시글 정보와 사용자 UID를 함께 저장
                            Post post = new Post(name, description, price, available, imageUrl, userId);

                            String postId = databaseReference.push().getKey();
                            databaseReference.child(postId).setValue(post);

                            Intent intent = new Intent(this, PostDetailActivity.class);
                            intent.putExtra("postId", postId);
                            startActivity(intent);
                        }
                    });
                }).addOnFailureListener(e -> {
                    // 업로드 실패 시 에러 처리
                    e.printStackTrace();
                });
            }
            else {
                imageUrl = ""; // 이미지 URL을 빈 문자열로 설정
                productImage.setImageResource(R.drawable.no_image_placeholder);
            }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // imageUri 변수에 이미지 URI 저장

            Glide.with(this).load(imageUri).into(productImage);
        }
    }
}

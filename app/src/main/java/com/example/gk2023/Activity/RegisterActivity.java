package com.example.gk2023.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gk2023.Entity.User;
import com.example.gk2023.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    User user;
    EditText username, password, staffname;
    Button button_login, button_register;
    FirebaseAuth auth = FirebaseAuth.getInstance();
//    DatabaseReference databaseReference = firebaseDatabase.getReference("Accounts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.editText_Username);
        password = findViewById(R.id.editText_Password);
        staffname = findViewById(R.id.editText_Staff);
        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DatabaseReference newaccount = databaseReference.push();
                String email, pass;
                LocalDate localDate = LocalDate.now();

                email = username.getText().toString();
                pass = password.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this,"Vui lòng nhập email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            uploadData();
                            Toast.makeText(getApplicationContext(),"Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(it);
                        }else{
                            Toast.makeText(getApplicationContext(),"Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                newaccount.setValue(user)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(RegisterActivity.this, "Register Successfully!", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                startActivity(intent);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(RegisterActivity.this, "Register Unsuccessfully!", Toast.LENGTH_SHORT).show();
//                            }
//                        });


            }

            private void uploadData() {

                String email, pass, staff;
                LocalDate localDate = LocalDate.now();
                int day, month, year;

                email = username.getText().toString();
                pass = password.getText().toString();
                staff = staffname.getText().toString();
                day = localDate.getDayOfMonth();
                month = localDate.getMonthValue();
                year = localDate.getYear();
                String date = String.valueOf(day + "/" + month + "/" + year);

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser userFirebase = firebaseAuth.getCurrentUser();
                String id = userFirebase.getUid();

                user = new User(email, pass, staff, date);

                FirebaseDatabase.getInstance().getReference("Accounts").child(id)
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Saved",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button button_login, button_register;
//    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
//    DatabaseReference databaseReference = firebaseDatabase.getReference("Accounts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.editText_Username);
        password = findViewById(R.id.editText_Password);
        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;

                email = username.getText().toString();
                pass = password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập password.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        boolean isloggedin = false;
//
//                        for (DataSnapshot accountsnapshot : snapshot.getChildren()){
//                            String accountname, accountpassword;
//                            accountname = accountsnapshot.child("username").getValue(String.class);
//                            accountpassword = accountsnapshot.child("password").getValue(String.class);
//
//                            if (accountname.equals(name) && accountpassword.equals(pass)){
//                                isloggedin = true;
//                                break;
//                            }
//                        }
//
//                        if (isloggedin){
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                        else {
//                            Toast.makeText(LoginActivity.this, "Login Unsucessfully!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(LoginActivity.this, "Can't connect to database!", Toast.LENGTH_SHORT).show();
//                    }
//                });

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
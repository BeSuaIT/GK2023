package com.example.gk2023.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gk2023.Adapter.ProductAdapter;
import com.example.gk2023.Entity.Product;
import com.example.gk2023.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvproduct;
    private TextView staffname, createdate;
    private Button btn_checkout, btn_logout;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_logout = findViewById(R.id.button_logout);
        btn_checkout = findViewById(R.id.button_order);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        UIproccess();
        fetchproductfromDB();
    }

    private void UIproccess() {
        rcvproduct = findViewById(R.id.rcvproduct);
        staffname = findViewById(R.id.text_staff);
        createdate = findViewById(R.id.text_createdate);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvproduct.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), productList);
        rcvproduct.setAdapter(productAdapter);
    }
    private void fetchproductfromDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("VongTay");
        DatabaseReference userRef = database.getReference("Accounts");
        String userID = user.getUid();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getKey();
                staffname.setText(snapshot.child(userID).child("staffname").getValue(String.class));
                createdate.setText(snapshot.child(userID).child("createdate").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
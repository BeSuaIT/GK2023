package com.example.gk2023.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productList, cartItemList;
    private Context context;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.cartItemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_view_holder, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.description.setText(product.getDescription());
        Glide.with(context).load(product.getImg()).centerCrop().into(holder.image);

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItemList.add(product);
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String userID = user.getUid();
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Accounts").child(userID).child("cart");

                cartRef.orderByChild("name").equalTo(product.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean productExists = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getKey();
                            int currentAmount = snapshot.child("amount").getValue(Integer.class);
                            int updatedAmount = currentAmount + 1;
                            snapshot.getRef().child("amount").setValue(updatedAmount);
                            productExists = true;
                            break;
                        }

                        if (!productExists) {
                            DatabaseReference newCartItemRef = cartRef.push();
                            newCartItemRef.child("name").setValue(product.getName());
                            newCartItemRef.child("description").setValue(product.getDescription());
                            newCartItemRef.child("img").setValue(product.getImg());
                            newCartItemRef.child("price").setValue(product.getPrice());
                            newCartItemRef.child("amount").setValue(1);
                        }

                        Toast.makeText(context, product.getName() + " added!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, description;
        ImageView image;
        Button btn_add;

        public ProductViewHolder(View itemview) {
            super(itemview);
            name = itemview.findViewById(R.id.Flowername);
            price = itemview.findViewById(R.id.Flowerprice);
            description = itemview.findViewById(R.id.Flowerdescription);
            image = itemview.findViewById(R.id.Flowerimage);
            btn_add = itemview.findViewById(R.id.button_add);
        }
    }
}

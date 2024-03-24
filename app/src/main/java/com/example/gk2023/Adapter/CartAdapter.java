package com.example.gk2023.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gk2023.Entity.Cart;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<Cart> cartList;
    private Context context;

    public CartAdapter(Context context, ArrayList<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_view_holder, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.name.setText(cart.getName());
        holder.price.setText(String.valueOf(cart.getPrice()));
        holder.amount.setText(String.valueOf(cart.getAmount()));
        Glide.with(context).load(cart.getImg()).centerCrop().into(holder.image);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Accounts").child(userID).child("cart");

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCartItem(cart);
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentAmount = cart.getAmount();
                if (currentAmount > 1) {
                    int updatedAmount = currentAmount - 1;
                    int updatedPrice = cart.getPrice() / currentAmount * updatedAmount;
                    cart.setAmount(updatedAmount);
                    cart.setPrice(updatedPrice);
                    notifyDataSetChanged();
                    updateCartItem(cart);
                } else {
                    removeCartItem(cart);
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentAmount = cart.getAmount();
                int updatedAmount = currentAmount + 1;
                int updatedPrice = cart.getPrice() * updatedAmount;
                cart.setAmount(updatedAmount);
                cart.setPrice(updatedPrice);
                notifyDataSetChanged();
                updateCartItem(cart);
            }
        });
    }

    private void removeCartItem(Cart cart) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Accounts").child(userID).child("cart");

        cartRef.orderByChild("name").equalTo(cart.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateCartItem(Cart cart) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Accounts").child(userID).child("cart");

        cartRef.orderByChild("name").equalTo(cart.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().child("amount").setValue(cart.getAmount());
                    snapshot.getRef().child("price").setValue(cart.getPrice());
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, plus, minus;
        ImageView image;
        Button btn_remove;
        EditText amount;

        public CartViewHolder(View itemview) {
            super(itemview);
            plus = itemview.findViewById(R.id.textView_plus);
            minus = itemview.findViewById(R.id.textView_minus);
            name = itemview.findViewById(R.id.Flowername);
            price = itemview.findViewById(R.id.Price);
            image = itemview.findViewById(R.id.Flowerimage);
            btn_remove = itemview.findViewById(R.id.button_remove);
            amount = itemview.findViewById(R.id.editText_amount);
        }
    }
}

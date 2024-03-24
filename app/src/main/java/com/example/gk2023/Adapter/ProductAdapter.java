package com.example.gk2023.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gk2023.Entity.Product;
import com.example.gk2023.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productList;
    private Context context;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_holder, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.description.setText(product.getDescription());
        Glide.with(context).load(product.getImg()).centerCrop().into(holder.image);
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

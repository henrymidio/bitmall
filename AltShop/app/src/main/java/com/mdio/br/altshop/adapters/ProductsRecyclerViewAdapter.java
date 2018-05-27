package com.mdio.br.altshop.adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdio.br.altshop.R;
import com.mdio.br.altshop.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {

    private List<Product> itemList;
    private OnItemClickListener listener;

    public ProductsRecyclerViewAdapter(List<Product> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @Override
    public ProductsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_list, parent, false);
        ViewHolder vh = new ViewHolder(layoutView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(itemList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Product item);
    }

    public void filter(List<Product> newItemList){
        itemList = newItemList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productPrice;
        private TextView productName;
        private ImageView productPhoto;

        public ViewHolder(View v) {
            super(v);
            productPrice = (TextView) v.findViewById(R.id.productPrice);
            productName = (TextView) v.findViewById(R.id.productName);
            productPhoto = (ImageView) v.findViewById(R.id.product_photo);
        }

        public void bind(final Product item, final OnItemClickListener listener) {
            productPrice.setText("R$ " + Double.toString(item.getPreco()));
            productName.setText(item.getNome());
            Picasso.get().load(item.getImagem()).into(productPhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }


}

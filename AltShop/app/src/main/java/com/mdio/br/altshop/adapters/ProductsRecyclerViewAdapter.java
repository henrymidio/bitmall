package com.mdio.br.altshop.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdio.br.altshop.R;
import com.mdio.br.altshop.models.Product;

import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {

    private List<Product> itemList;

    public ProductsRecyclerViewAdapter(List<Product> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_lis, parent, false);
        ViewHolder vh = new ViewHolder(layoutView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.productName.setText(itemList.get(position).getName());
        holder.productDescription.setText(itemList.get(position).getDescription());
        holder.productPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productDescription;
        private ImageView productPhoto;

        public ViewHolder(View v) {
            super(v);
            productName = (TextView) v.findViewById(R.id.product_name);
            productDescription = (TextView) v.findViewById(R.id.product_description);
            productPhoto = (ImageView) v.findViewById(R.id.product_photo);
        }

    }


}

package com.mdio.br.altshop.activities.Main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mdio.br.altshop.R;
import com.mdio.br.altshop.adapters.ProductsRecyclerViewAdapter;
import com.mdio.br.altshop.interfaces.OnFragmentCallListener;
import com.mdio.br.altshop.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsFragment extends Fragment {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private View view;
    OnFragmentCallListener mCallback;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_products, container, false);
            database = FirebaseDatabase.getInstance();

            setRecyclerView();
            getProducts();
        }

        // Inflate the layout for this fragment
        return view;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnFragmentCallListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnFragmentCallListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private void setRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
    }

    private void getProducts() {

        DatabaseReference produtos = database.getReference("produtos");
        produtos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> productsList = new ArrayList<Product>();
                for (DataSnapshot produtosSnapshot: dataSnapshot.getChildren()) {
                    Product produto = produtosSnapshot.getValue(Product.class);
                    productsList.add(produto);
                }

                Collections.shuffle(productsList);
                renderProducts(productsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void renderProducts(List<Product> productsList) {
        ProductsRecyclerViewAdapter rcAdapter = new ProductsRecyclerViewAdapter(productsList, new ProductsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product item) {
                mCallback.onCall(item);
            }
        });
        recyclerView.setAdapter(rcAdapter);
    }

}

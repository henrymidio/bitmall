package com.mdio.br.altshop.activities.Main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private ProductsRecyclerViewAdapter rcAdapter;
    private ProgressBar mProgressBar;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null) {
            view = inflater.inflate(R.layout.fragment_products, container, false);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            database = FirebaseDatabase.getInstance();

            setRecyclerView();
            loadProducts(null);
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

    public void loadProducts(final String categoryFilter) {
        mProgressBar.setVisibility(View.VISIBLE);

        DatabaseReference produtos = database.getReference("produtos");
        produtos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> productsList = new ArrayList<Product>();
                for (DataSnapshot produtosSnapshot: dataSnapshot.getChildren()) {
                    Product produto = produtosSnapshot.getValue(Product.class);
                    if(categoryFilter != null) {
                        if(produto.getCategoria().equals(categoryFilter)) {
                            productsList.add(produto);
                        }
                    } else {
                        productsList.add(produto);
                    }
                }
                //Collections.shuffle(productsList);
                renderProducts(productsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void renderProducts(List<Product> productsList) {
        if(rcAdapter != null) {
            rcAdapter.filter(productsList);
            mProgressBar.setVisibility(View.GONE);
            return;
        }
        rcAdapter = new ProductsRecyclerViewAdapter(productsList, new ProductsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product item) {
                mCallback.onCall(item);
            }
        });
        int spanCount = 2; // 3 columns
        int spacing = 12; // 50px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        recyclerView.setAdapter(rcAdapter);

        mProgressBar.setVisibility(View.GONE);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = 0; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}

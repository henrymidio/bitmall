package com.mdio.br.altshop.activities.Main;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdio.br.altshop.R;
import com.mdio.br.altshop.adapters.ProductsRecyclerViewAdapter;
import com.mdio.br.altshop.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products, container, false);

        loadProducts(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void loadProducts(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<Product> gaggeredList = getProducts();

        ProductsRecyclerViewAdapter rcAdapter = new ProductsRecyclerViewAdapter(gaggeredList);
        recyclerView.setAdapter(rcAdapter);
    }

    private List<Product> getProducts() {

        List<Product> listViewItems = new ArrayList<Product>();

        listViewItems.add(new Product("R$ 445", "Smartphone Alcatel Pixi4 5 8gb Tela 5' 8mp Com Tv Digital+nf", R.drawable.relogio));
        listViewItems.add(new Product("12x R$ 445", "Celular Alcatel Pop3 ", R.drawable.relogio));
        listViewItems.add(new Product("R$ 445","Lorem ipsum lorem ipsum", R.drawable.relogio));
        listViewItems.add(new Product("10x R$ 445", "Celular Alcatel Pop3 ", R.drawable.relogio));
        listViewItems.add(new Product("R$ 445", "", R.drawable.one));
        listViewItems.add(new Product("Alkane", "", R.drawable.one));
        listViewItems.add(new Product("Alkane", "", R.drawable.one));

        return listViewItems;

    }

}

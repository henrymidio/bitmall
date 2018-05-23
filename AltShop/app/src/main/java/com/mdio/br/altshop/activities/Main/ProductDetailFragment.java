package com.mdio.br.altshop.activities.Main;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdio.br.altshop.R;
import com.mdio.br.altshop.models.Product;

import java.io.Serializable;

public class ProductDetailFragment extends Fragment {

    private static final String DESCRIBABLE_KEY = "product";
    private Product product;
    TextView productName;
    ImageView productImage;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(Product item) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, (Serializable) item);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        product = (Product) getArguments().getSerializable(DESCRIBABLE_KEY);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        productName = (TextView) view.findViewById(R.id.productName);
        productName.setText(product.getName());

        productImage = view.findViewById(R.id.productImage);
        productImage.setImageResource(product.getPhoto());
    }


}

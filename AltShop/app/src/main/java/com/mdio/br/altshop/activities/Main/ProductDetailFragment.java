package com.mdio.br.altshop.activities.Main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdio.br.altshop.R;
import com.mdio.br.altshop.models.Product;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ProductDetailFragment extends Fragment {

    private static final String DESCRIBABLE_KEY = "product";
    private Product product;
    TextView productName;
    TextView productDescricao;
    TextView productloja;
    TextView produtoPreco;
    ImageView productImage;
    Button openStoreLink;

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

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            product = (Product) bundle.getSerializable(DESCRIBABLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        readBundle(getArguments());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        productName = (TextView) view.findViewById(R.id.textViewNome);
        productName.setText(product.getNome());

        produtoPreco = view.findViewById(R.id.textViewPreco);
        produtoPreco.setText("R$ " + product.getPreco());

        productDescricao = view.findViewById(R.id.textViewDescricao);
        productDescricao.setText(product.getDescricao());

        productloja = view.findViewById(R.id.textViewLoja);
        //productloja.setText(product.);

        productImage = view.findViewById(R.id.productImage);
        Picasso.get().load(product.getImagem()).into(productImage);

        openStoreLink = view.findViewById(R.id.btnLink);
        openStoreLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getLoja().get("link").toString()));
                startActivity(browserIntent);
            }
        });

    }


}

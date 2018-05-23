package com.mdio.br.altshop.activities.Main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.mdio.br.altshop.R;
import com.mdio.br.altshop.interfaces.OnFragmentCallListener;
import com.mdio.br.altshop.models.Product;
import com.mdio.br.altshop.providers.SuggestionProvider;


public class MainActivity extends AppCompatActivity implements OnFragmentCallListener {

    private Toolbar myToolbar;
    private FragmentManager fragmentManager = getFragmentManager();
    private boolean isTyping = false; //
    private String[] suggestions = {"Celular", "PC", "Máquina de Lavar", "Shampoo"};
    private ProductsFragment pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        pf = new ProductsFragment();

        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_fragment, pf);
            fragmentTransaction.commit();
        }

        /*
            Limpa o histórico de sugestões do searchview
         */
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(MainActivity.this,
                SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
        suggestions.clearHistory();

        setSearchView();

    }

    private void setFragmentTransitions(Product product) {
        // Find the shared element (in Fragment A)
        ImageView ivProfile = (ImageView) findViewById(R.id.product_photo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Inflate transitions to apply
            Transition changeTransform = TransitionInflater.from(this).
                    inflateTransition(R.transition.change_image_transform);
            Transition explodeTransform = TransitionInflater.from(this).
                    inflateTransition(android.R.transition.explode);

            // Setup exit transition on first fragment
            pf.setSharedElementReturnTransition(changeTransform);
            pf.setExitTransition(explodeTransform);

            // Setup enter transition on second fragment
            ProductDetailFragment df = ProductDetailFragment.newInstance(product);
            df.setSharedElementEnterTransition(changeTransform);
            df.setEnterTransition(explodeTransform);

            // Add second fragment by replacing first
            FragmentTransaction ft = getFragmentManager().beginTransaction()
                    .replace(R.id.container_fragment, df)
                    .addToBackStack("transaction")
                    .addSharedElement(ivProfile, "profile");
            // Apply the transaction
            ft.commit();
        } else {
            FragmentTransaction ft = getFragmentManager().beginTransaction()
                    .replace(R.id.container_fragment, new ProductDetailFragment())
                    .addToBackStack("transaction");
            // Apply the transaction
            ft.commit();
        }
    }

    private void setSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.search_product);
        ComponentName cn = new ComponentName(this, Main2Activity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchRecentSuggestions suggestionsManager = new SearchRecentSuggestions(MainActivity.this,
                        SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
                if(s.length() > 0 && isTyping == false) {
                    isTyping = true;
                    addSuggestions(suggestions, suggestionsManager);
                } else if(s.length() == 0) {
                    isTyping = false;
                    suggestionsManager.clearHistory();
                }
                return true;
            }
        });
    }

    private void addSuggestions(String[] suggestions, SearchRecentSuggestions suggestionsManager) {
        for (int i = 0; i < suggestions.length; i++) {
            suggestionsManager.saveRecentQuery(suggestions[i], null);
        }
    }

    @Override
    public void onCall(Product product) {
        setFragmentTransitions(product);
    }

}

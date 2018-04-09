package com.mdio.br.altshop.activities.Main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.mdio.br.altshop.R;
import com.mdio.br.altshop.providers.SuggestionProvider;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    FragmentManager fragmentManager = getFragmentManager();
    private boolean isTyping = false; //
    private String[] suggestions = {"Celular", "PC", "Máquina de Lavar", "Shampoo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        if(savedInstanceState == null) {
            ProductsFragment pf = new ProductsFragment();
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

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    */
}

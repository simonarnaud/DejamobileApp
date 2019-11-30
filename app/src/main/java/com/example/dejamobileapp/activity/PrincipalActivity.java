package com.example.dejamobileapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.fragment.CardListFragment;
import com.example.dejamobileapp.fragment.ProfileFragment;
import com.example.dejamobileapp.fragment.PurchaseListFragment;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.Codes;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Class in charge of presenting the heart of the application
 */
public class PrincipalActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        user = (User)getIntent().getSerializableExtra(Codes.USER_SEND_CODE);

        toolbar = getSupportActionBar();
        BottomNavigationView bottomNavigation = findViewById(R.id.navigation_bar);
        bottomNavigation.setOnNavigationItemSelectedListener(listener);

        launchCardsFragment();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = menuItem -> {
        switch(menuItem.getItemId()) {
            case R.id.navigation_cards:
                launchCardsFragment();
                return true;
            case R.id.navigation_profil:
                launchProfileFragment();
                return true;
            case R.id.navigation_purchases:
                launchPurchasesFragment();
                return true;
        }
        return false;
    };

    /**
     * Method which launch the cards fragment
     */
    private void launchCardsFragment() {
        toolbar.setTitle(getResources().getString(R.string.menu_cards));
        CardListFragment fragment = new CardListFragment();
        openFragment(fragment, Codes.FRAGMENT_CARDS_TAG);
    }

    /**
     * Method wich launch the purchases fragment
     */
    private void launchPurchasesFragment() {
        toolbar.setTitle(getResources().getString(R.string.menu_purchases));
        PurchaseListFragment fragment = new PurchaseListFragment();
        openFragment(fragment, Codes.FRAGMENT_PURCHASE_TAG);
    }

    /**
     * Method which launch the profile fragment
     */
    private void launchProfileFragment() {
        toolbar.setTitle(getResources().getString(R.string.menu_profile));
        ProfileFragment fragment = new ProfileFragment();
        openFragment(fragment, Codes.FRAGMENT_PROFILE_TAG);
    }

    /**
     * Method which open the fragment passed in parameters
     * @param fragment the fragment to display
     * @param tag the tag name of the fragment
     */
    private void openFragment(Fragment fragment, String tag) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Codes.USER_CODE, user);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }
}

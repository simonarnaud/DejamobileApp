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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PrincipalActivity extends AppCompatActivity {

    public static final String USER_CODE = "USER_SEND";
    public static final String CARD_SEND_CODE = "CARD_SEND";
    public static final String USER_ID_CODE = "USER_ID_SEND";

    public static final String EXTRA_CARD_REPLY = "REPLY_NEW_CARD";

    public static final int NEW_CARD_REQUEST_CODE = 1;
    public static final int NEW_PURCHASE_REQUEST_CODE = 2;

    public static final String FRAGMENT_PROFILE_TAG = "FRAGMENT_PROFILE";
    public static final String FRAGMENT_CARDS_TAG = "FRAGMENT_CARDS";
    public static final String FRAGMENT_PURCHASE_TAG = "FRAGMENT_PURCHASE";

    private ActionBar toolbar;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        user = (User)getIntent().getSerializableExtra(LoginActivity.USER_SEND_CODE);

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

    private void launchCardsFragment() {
        toolbar.setTitle(getResources().getString(R.string.menu_cards));
        CardListFragment fragment = new CardListFragment();
        openFragment(fragment, FRAGMENT_CARDS_TAG);
    }

    private void launchPurchasesFragment() {
        toolbar.setTitle(getResources().getString(R.string.menu_purchases));
        PurchaseListFragment fragment = new PurchaseListFragment();
        openFragment(fragment, FRAGMENT_PURCHASE_TAG);
    }

    private void launchProfileFragment() {
        toolbar.setTitle(getResources().getString(R.string.menu_profile));
        ProfileFragment fragment = new ProfileFragment();
        openFragment(fragment, FRAGMENT_PROFILE_TAG);
    }

    private void openFragment(Fragment fragment, String tag) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_CODE, user);
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

package com.example.dejamobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dejamobileapp.fragment.CardListFragment;
import com.example.dejamobileapp.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PrincipalActivity extends AppCompatActivity {

    public static final String USER_CODE = "USER_SEND";
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
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.navigation_cards:
                    toolbar.setTitle("Cards");
                    CardListFragment fragment = new CardListFragment();
                    openFragment(fragment);
                    return true;
                case R.id.navigation_profil:
                    return true;
                case R.id.navigation_purchases:
                    return true;
            }
            return false;
        }
    };

    private void openFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_CODE, user);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
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
        switch (item.getItemId()) {
            case R.id.menu_logout:
                finish();
                return true;
            case R.id.menu_profil:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }


}

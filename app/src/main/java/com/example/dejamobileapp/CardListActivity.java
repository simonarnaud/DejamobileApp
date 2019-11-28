package com.example.dejamobileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dejamobileapp.adapter.CardListAdapter;
import com.example.dejamobileapp.factory.CardViewModelFactory;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.RemoveCardListener;
import com.example.dejamobileapp.viewmodel.CardViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CardListActivity extends AppCompatActivity implements RemoveCardListener {

    public static final int NEW_CARD_REQUEST_CODE = 1;
    public static final String USER_ID_CODE = "USER_ID_SEND";

    private ActionBar toolbar;

    private CardViewModel cardViewModel;
    private User user;
    private ImageView emptyCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        user = (User)getIntent().getSerializableExtra(LoginActivity.USER_SEND_CODE);

        //
        toolbar = getSupportActionBar();
        BottomNavigationView bottomNavigation = findViewById(R.id.navigation_bar);
        //

        emptyCards = findViewById(R.id.empty_cards);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CardListAdapter adapter = new CardListAdapter(this, user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CardViewModelFactory cardFactory = new CardViewModelFactory(this.getApplication(), user.getUserId());
        cardViewModel = ViewModelProviders.of(this, cardFactory).get(CardViewModel.class);

        cardViewModel.getCardsByUserId(user.getUserId()).observe(this, cards -> {
            adapter.setCards(cards);
            setEmptyCardImage(cards);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CardListActivity.this, NewCardActivity.class);
            intent.putExtra(USER_ID_CODE ,user.getUserId());
            startActivityForResult(intent, NEW_CARD_REQUEST_CODE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card_list, menu);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            Card card = (Card)data.getSerializableExtra(NewCardActivity.EXTRA_REPLY);
            cardViewModel.insert(card);
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
        }
    }

    private void setEmptyCardImage(List<Card> cards) {
        if(cards.size() > 0) {
            emptyCards.setVisibility(View.GONE);
        } else {
            emptyCards.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void removeCard(Card card) {
       cardViewModel.delete(card);
    }

    @Override
    public void removeAllCards() {
        cardViewModel.deleteAllUserCards(user.getUserId());
    }
}

package com.example.dejamobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dejamobileapp.adapter.CardListAdapter;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.RemoveCardListener;
import com.example.dejamobileapp.viewmodel.CardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CardListActivity extends AppCompatActivity implements RemoveCardListener {

    public static final int NEW_CARD_REQUEST_CODE = 1;
    public static final String USER_ID_CODE = "USER_ID_SEND";

    private CardViewModel cardViewModel;
    private User user;
    private ImageView emptyCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        user = (User)getIntent().getSerializableExtra(LoginActivity.USER_SEND_CODE);

        emptyCards = findViewById(R.id.empty_cards);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CardListAdapter adapter = new CardListAdapter(this, user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);

        cardViewModel.getCards().observe(this, cards -> {
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
        cardViewModel.deleteAll();
    }
}

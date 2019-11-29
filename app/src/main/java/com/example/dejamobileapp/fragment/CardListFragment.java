package com.example.dejamobileapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dejamobileapp.PrincipalActivity;
import com.example.dejamobileapp.NewCardActivity;
import com.example.dejamobileapp.R;
import com.example.dejamobileapp.adapter.CardListAdapter;
import com.example.dejamobileapp.factory.CardViewModelFactory;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.RemoveCardListener;
import com.example.dejamobileapp.viewmodel.CardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CardListFragment extends Fragment  implements RemoveCardListener {

    public static final int NEW_CARD_REQUEST_CODE = 1;
    public static final String USER_ID_CODE = "USER_ID_SEND";

    private ImageView emptyCards;
    private CardViewModel cardViewModel;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        user = (User) getArguments().getSerializable(PrincipalActivity.USER_CODE);
        return inflater.inflate(R.layout.fragment_card_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyCards = view.findViewById(R.id.empty_cards);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final CardListAdapter adapter = new CardListAdapter(getActivity(), user, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        CardViewModelFactory cardFactory = new CardViewModelFactory(getActivity().getApplication(), user.getUserId());
        cardViewModel = ViewModelProviders.of(this, cardFactory).get(CardViewModel.class);

        cardViewModel.getCardsByUserId(user.getUserId()).observe(this, cards -> {
            adapter.setCards(cards);
            setEmptyCardImage(cards);
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(viewButton -> {
            Intent intent = new Intent(getActivity(), NewCardActivity.class);
            intent.putExtra(USER_ID_CODE ,user.getUserId());
            startActivityForResult(intent, NEW_CARD_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CARD_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            Card card = (Card)data.getSerializableExtra(NewCardActivity.EXTRA_REPLY);
            cardViewModel.insert(card);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
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

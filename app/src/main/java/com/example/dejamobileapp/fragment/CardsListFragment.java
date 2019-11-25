package com.example.dejamobileapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.viewmodel.CardViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class CardsListFragment extends Fragment {

   /* private CardsListAdapter cardsListAdapter;
    private CardViewModel cardViewModel;
    private Context context;

    public static CardsListFragment newInstance() {
        return new CardsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        cardsListAdapter = new CardsListAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_cards);
        recyclerView.setAdapter(cardsListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    private void initData() {
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        cardViewModel.getCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                cardsListAdapter.setCardList(cards);
            }
        });
    }

    public void removeData() {
        if(cardViewModel != null){
            cardViewModel.deleteAll();
        }
    }*/
}

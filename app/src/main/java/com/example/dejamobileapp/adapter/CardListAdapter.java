package com.example.dejamobileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.model.Card;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    class CardViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardItemView;

        private CardViewHolder(View itemView) {
            super(itemView);
            cardItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater inflater;
    private List<Card> cards;

    public CardListAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @NotNull
    @Override
    public CardViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_card_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull CardViewHolder holder, int position) {
        if (cards != null) {
            Card currentCard = cards.get(position);
            holder.cardItemView.setText(currentCard.getScheme().toString());
        } else {
            holder.cardItemView.setText(R.string.empty_numbers);
        }
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (cards != null) {
            return cards.size();
        }
        return 0;
    }
}

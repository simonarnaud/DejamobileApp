package com.example.dejamobileapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.dejamobileapp.CardListActivity;
import com.example.dejamobileapp.R;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.CardFormatter;

import org.jetbrains.annotations.NotNull;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    class CardViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardNumbers, cardDate, cardName, cardCrypto;
        private final ImageView cardScheme;
        private final LinearLayout cardLinear, cardBackLinear;

        private CardViewHolder(View itemView) {
            super(itemView);
            cardNumbers = itemView.findViewById(R.id.card_numbers);
            cardDate = itemView.findViewById(R.id.card_date);
            cardName = itemView.findViewById(R.id.card_name);
            cardCrypto = itemView.findViewById(R.id.card_crypto);
            cardScheme = itemView.findViewById(R.id.card_scheme);
            cardLinear = itemView.findViewById(R.id.card_linear);
            cardBackLinear = itemView.findViewById(R.id.card_back_linear);
        }
    }

    private final LayoutInflater inflater;
    private List<Card> cards;
    private User user;

    public CardListAdapter(Context context, User user) {
        inflater = LayoutInflater.from(context);
        this.user = user;
    }

    @NotNull
    @Override
    public CardViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_card_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull CardViewHolder holder, int position) {
        if (cards != null && user != null) {
            Card currentCard = cards.get(position);

            holder.cardNumbers.setText(CardFormatter.cardNumberFormatter(currentCard.getNumbers()));
            holder.cardDate.setText(formatExpirationDate(currentCard));
            holder.cardName.setText(user.getCardIdentity());
            holder.cardCrypto.setText(String.valueOf(currentCard.getCrypto()));
            holder.cardScheme.setImageDrawable(setSchemeImage(currentCard));


            holder.cardLinear.setOnClickListener(view -> switchView(holder));
            holder.cardBackLinear.setOnClickListener(view -> switchView(holder));

            holder.cardLinear.setOnLongClickListener(view -> {
                popupMenuGenerate(holder.cardLinear, currentCard);
                return true;
            });

            holder.cardBackLinear.setOnLongClickListener(view -> {
                popupMenuGenerate(holder.cardBackLinear, currentCard);
                return true;
            });

            holder.cardLinear.setVisibility(View.VISIBLE);
            holder.cardBackLinear.setVisibility(View.GONE);
        }
    }

    private void popupMenuGenerate(@NotNull LinearLayout linear, Card currentCard) {
        PopupMenu menu = new PopupMenu(inflater.getContext(), linear);
        menu.inflate(R.menu.menu_remove_card);
        menu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.remove_card_item_menu:
                    if(inflater.getContext() instanceof CardListActivity) {
                        ((CardListActivity) inflater.getContext()).removeCard(currentCard);
                    }
                    return true;
                case R.id.remove_all_card_item_menu:
                    if(inflater.getContext() instanceof CardListActivity) {
                        ((CardListActivity) inflater.getContext()).removeAllCards();
                    }
                default:
                    return false;
            }
        });
        menu.setGravity(Gravity.END);
        menu.show();
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

    private Drawable setSchemeImage(Card card) {
        switch (card.getScheme()) {
            case VISA:
                return inflater.getContext().getDrawable(R.drawable.bt_ic_visa);
            case JCB:
                return inflater.getContext().getDrawable(R.drawable.bt_ic_jcb);
            case MASTERCARD:
                return inflater.getContext().getDrawable(R.drawable.bt_ic_mastercard);
            case MAESTRO:
                return inflater.getContext().getDrawable(R.drawable.bt_ic_maestro);
            case DCI:
                return inflater.getContext().getDrawable(R.drawable.bt_ic_dci);
            default:
                return inflater.getContext().getDrawable(R.drawable.bt_ic_cmi);
        }
    }

    private void switchView(CardViewHolder holder) {
        if (holder.cardLinear.getVisibility() == View.VISIBLE) {
            holder.cardLinear.setVisibility(View.GONE);
            holder.cardBackLinear.setVisibility(View.VISIBLE);
        } else {
            holder.cardLinear.setVisibility(View.VISIBLE);
            holder.cardBackLinear.setVisibility(View.GONE);
        }
    }

    private String formatExpirationDate(Card card) {
        String chain = (String) DateFormat.format("MM", card.getExpiration());
        chain += "/";
        chain += (String) DateFormat.format("yy", card.getExpiration());
        return chain;
    }
}

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

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.converter.GenderConverter;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.CardFormatter;
import com.example.dejamobileapp.utils.Gender;
import com.example.dejamobileapp.utils.RemoveCardListener;

import org.jetbrains.annotations.NotNull;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Class in charge of adapt card items
 */
public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    /**
     * Class in charge of bin card items
     */
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
    private RemoveCardListener listener;

    public CardListAdapter(Context context, User user, RemoveCardListener listener) {
        inflater = LayoutInflater.from(context);
        this.user = user;
        this.listener = listener;
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
            holder.cardName.setText(GenderConverter.getGenderTitle(user.getGender(), inflater.getContext()).concat(user.getCardIdentity()));
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

    /**
     * Method which display the popup menu for interact with a card
     * @param linear the linearLayout corresponding to the card item
     * @param currentCard the current card into the item
     */
    private void popupMenuGenerate(@NotNull LinearLayout linear, Card currentCard) {
        PopupMenu menu = new PopupMenu(inflater.getContext(), linear);
        menu.inflate(R.menu.menu_card);
        menu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.make_purchase:
                    listener.makePurchase(currentCard);
                    return true;
                case R.id.remove_card_item_menu:
                    listener.removeCard(currentCard);
                    return true;
                case R.id.remove_all_card_item_menu:
                    listener.removeAllCards();
                default:
                    return false;
            }
        });
        menu.setGravity(Gravity.END);
        menu.show();
    }

    /**
     * Method which set the card list
     * @param cards the list of cards to set
     */
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

    /**
     * Method which set imageview with the right card scheme
     * @param card the card to display
     * @return the drawable corresponding to the card scheme
     */
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

    /**
     * Method which is in charge of switch with the card face
     * @param holder the holder of the view
     */
    private void switchView(CardViewHolder holder) {
        if (holder.cardLinear.getVisibility() == View.VISIBLE) {
            holder.cardLinear.setVisibility(View.GONE);
            holder.cardBackLinear.setVisibility(View.VISIBLE);
        } else {
            holder.cardLinear.setVisibility(View.VISIBLE);
            holder.cardBackLinear.setVisibility(View.GONE);
        }
    }

    /**
     * Method which format the expiration date
     * @param card the actual card
     * @return the expiration date
     */
    private String formatExpirationDate(Card card) {
        String chain = (String) DateFormat.format("MM", card.getExpiration());
        chain += "/";
        chain += (String) DateFormat.format("yy", card.getExpiration());
        return chain;
    }
}

package com.example.dejamobileapp.utils;

import com.example.dejamobileapp.model.Card;

/**
 * Interface used for triggered some methods
 */
public interface RemoveCardListener {
    void makePurchase(Card card);
    void removeCard(Card card);
    void removeAllCards();
}

package com.example.dejamobileapp.viewmodel;

import android.app.Application;

import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.repository.CardRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Class in charge of the link between the card view and the card model
 */
public class CardViewModel extends AndroidViewModel {

   private CardRepository cardRepository;
   private LiveData<List<Card>> cards;

   public CardViewModel(@NonNull Application application, int userId) {
       super(application);
       cardRepository = new CardRepository(application, userId);
       cards = cardRepository.getCardsByUserId();
   }

    /**
     * Method which get back all the cards in real time
     * @return all cards from the cardRepository
     */
   public LiveData<List<Card>> getCards() {return cards;}

    /**
     * Method which insert a list of cards
     * @param cards list of cards to insert with the cardRepository help
     */
   public void insert(Card... cards) {cardRepository.insertAll(cards);}

    /**
     * Method which insert a card
     * @param card the card to insert with the cardRepository help
     */
   public void insert(Card card) {cardRepository.insert(card);}

    /**
     * Method which delete a card
     * @param card the card to delete with the cardRepository help
     */
   public void delete(Card card) {cardRepository.delete(card);}

    /**
     * Method which delete all the cards with cardRepository help
     */
   public void deleteAll() {cardRepository.deleteAll();}

    /**
     * Method which delete all cards from with the cardRepository help attaching to a user
     * @param userId the user id
     */
   public void deleteAllUserCards(int userId) {cardRepository.deleteAllUserCards(userId);}

    /**
     * Method which update a card with cardRepository help
     * @param card the card to update
     */
   public void update(Card card) {cardRepository.update(card);}

    /**
     * Method which get a card by an identifier
     * @param id the card identifier
     * @return the card corresponding to the id with cardRepository help
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
   public Card getCardById(int id) throws ExecutionException, InterruptedException {return cardRepository.getCardById(id);}

    /**
     * Method which get back user cards in real time
     * @return user cards from the cardRepository
     */
   public LiveData<List<Card>> getCardsByUserId()  {return cards;}
 }

package com.example.dejamobileapp.viewmodel;

import android.app.Application;

import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.repository.CardRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CardViewModel extends AndroidViewModel {

   private CardRepository cardRepository;
   private LiveData<List<Card>> cards;

   public CardViewModel(@NonNull Application application, int userId) {
       super(application);
       cardRepository = new CardRepository(application, userId);
       cards = cardRepository.getCardsByUserId();
   }

   public LiveData<List<Card>> getCards() {return cards;}
   public void insert(Card... cards) {cardRepository.insertAll(cards);}
   public void insert(Card card) {cardRepository.insert(card);}
   public void delete(Card card) {cardRepository.delete(card);}
   public void deleteAll() {cardRepository.deleteAll();}
   public void deleteAllUserCards(int userId) {cardRepository.deleteAllUserCards(userId);}
   public void update(Card card) {cardRepository.update(card);}
   public Card getCardById(int id) throws ExecutionException, InterruptedException {return cardRepository.getCardById(id);}
   public LiveData<List<Card>> getCardsByUserId(int id)  {return cards/*cardRepository.getCardsByUserId()*/;}
 }

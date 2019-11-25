package com.example.dejamobileapp.viewmodel;

import android.app.Application;

import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.repository.CardRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CardViewModel extends AndroidViewModel {

   private CardRepository cardRepository;
   private LiveData<List<Card>> cards;

   public CardViewModel(@NonNull Application application) {
       super(application);
       cardRepository = new CardRepository(application);
       cards = cardRepository.getCards();
   }

   public LiveData<List<Card>> getCards() {return cards;}
   public void insert(Card... cards) {cardRepository.insertAll(cards);}
   public void insert(Card card) {cardRepository.insert(card);}
   public void delete(Card card) {cardRepository.delete(card);}
   public void deleteAll() {cardRepository.deleteAll();}
   public void update(Card card) {cardRepository.update(card);}
   public Card getCardById(int id) {return cardRepository.getCardById(id);}
   public LiveData<List<Card>> getCardsByUserId(int id) {return cardRepository.getCardsByUserId(id);}
 }

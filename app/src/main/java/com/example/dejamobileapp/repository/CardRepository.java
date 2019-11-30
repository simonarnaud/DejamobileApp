package com.example.dejamobileapp.repository;

import android.app.Application;

import com.example.dejamobileapp.dao.CardDao;
import com.example.dejamobileapp.database.AppDatabase;
import com.example.dejamobileapp.model.Card;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Class in charge of the link between the dao card methods and the cardviewmodel
 */
public class CardRepository {

    private CardDao cardDao;
    private LiveData<List<Card>> cards;

    public CardRepository(@NonNull Application application, int userId) {
        cardDao = AppDatabase.getAppDatabaseInstance(application).cardDao();
        cards = cardDao.loadAllCardsByUserId(userId);
    }

    /**
     * Method which get back all the cards in real time
     * @return all cards from the room database
     */
    public LiveData<List<Card>> getCards() {
        return cards;
    }

    /**
     * Method which insert a list of cards
     * @param cards list of cards to insert into the room database
     */
    public void insertAll(Card... cards) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.insertAllCards(cards));
    }

    /**
     * Method which insert a card
     * @param card the card to insert into the room database
     */
    public void insert(Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.insert(card));
    }

    /**
     * Method which delete a card
     * @param card the card to delete from the room database
     */
    public void delete(Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.delete(card));
    }

    /**
     * Method which delete all the cards from the room database
     */
    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.deleteAllCards());
    }

    /**
     * Method which delete all cards from the room database attaching to a user
     * @param userId the user id
     */
    public void deleteAllUserCards(int userId) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.deleteAllUserCards(userId));
    }

    /**
     * Method which update a card into the room database
     * @param card the card to update
     */
    public void update(Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.updateCard(card));
    }

    /**
     * Method which get a card by an identifier
     * @param id the card identifier
     * @return the card corresponding to the id from the room database
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public Card getCardById(int id) throws ExecutionException, InterruptedException {
        Callable<Card> callable = () -> cardDao.loadCardById(id);
        Future<Card> future = AppDatabase.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    /**
     * Method which get back user cards in real time
     * @return user cards from the room database
     */
    public LiveData<List<Card>> getCardsByUserId() {
        return cards;
    }
}

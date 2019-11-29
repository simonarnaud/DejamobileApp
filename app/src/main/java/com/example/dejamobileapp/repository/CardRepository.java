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

public class CardRepository {

    private CardDao cardDao;
    private LiveData<List<Card>> cards;

    public CardRepository(@NonNull Application application, int userId) {
        cardDao = AppDatabase.getAppDatabaseInstance(application).cardDao();
        cards = cardDao.loadAllCardsByUserId(userId);
    }

    public LiveData<List<Card>> getCards() {
        return cards;
    }

    public void insertAll(Card... cards) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.insertAllCards(cards));
    }

    public void insert(Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.insert(card));
    }

    public void delete(Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.delete(card));
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.deleteAllCards());
    }

    public void deleteAllUserCards(int userId) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.deleteAllUserCards(userId));
    }

    public void update(Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.updateCard(card));
    }

    public Card getCardById(int id) throws ExecutionException, InterruptedException {
        Callable<Card> callable = () -> cardDao.loadCardById(id);
        Future<Card> future = AppDatabase.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    public LiveData<List<Card>> getCardsByUserId() {
        return cards;
    }
}

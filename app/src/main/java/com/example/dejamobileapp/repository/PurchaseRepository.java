package com.example.dejamobileapp.repository;

import android.app.Application;

import com.example.dejamobileapp.dao.PurchaseDao;
import com.example.dejamobileapp.database.AppDatabase;
import com.example.dejamobileapp.model.Purchase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Class in charge of the link between the dao purchase methods and the purchaseviewmodel
 */
public class PurchaseRepository {

    private PurchaseDao purchaseDao;
    private LiveData<List<Purchase>> purchases;

    public PurchaseRepository(@NonNull Application application, int userId) {
        purchaseDao = AppDatabase.getAppDatabaseInstance(application).purchaseDao();
        purchases = purchaseDao.getPurchasesByUserId(userId);
    }

    /**
     * Method which get back all the purchases in real time
     * @return all purchases from the room database
     */
    public LiveData<List<Purchase>> getPurchases() {
        return this.purchases;
    }

    /**
     * Method which insert a list of purchases
     * @param purchases list of purchases to insert into the room database
     */
    public void insertAll(Purchase... purchases) {
        AppDatabase.databaseWriteExecutor.execute(() -> purchaseDao.insertAllPurchases(purchases));
    }

    public void update(Purchase purchase) {
        AppDatabase.databaseWriteExecutor.execute(() -> purchaseDao.updatePurchase(purchase));
    }

    /**
     * Method which delete a purchase
     * @param purchase the purchase to delete from the room database
     */
    public void delete(Purchase purchase) {
        AppDatabase.databaseWriteExecutor.execute(() -> purchaseDao.delete(purchase));
    }

    /**
     * Method which delete all the purchases from the room database
     */
    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> purchaseDao.deleteAllPurchases());
    }

    /**
     * Method which get a purchase by an identifier
     * @param id the purchase identifier
     * @return the purchase corresponding to the id from the room database
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public Purchase getPurchaseById(int id) throws ExecutionException, InterruptedException {
        Callable<Purchase> callable = () -> purchaseDao.loadPurchaseById(id);
        Future<Purchase> future = AppDatabase.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    /**
     * Method which get back a list of purchases in function of card identifier
     * @param id the card identifier
     * @return the list of purchases corresponding to the id from the room database
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public List<Purchase> getPurchasesByCardId(int id) throws ExecutionException, InterruptedException {
        Callable<List<Purchase>> callable = () -> purchaseDao.loadAllPurchasesByCardId(id);
        Future<List<Purchase>> future = AppDatabase.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    /**
     * Method which get back user purchases in real time
     * @return user purchases from the room database
     */
    public LiveData<List<Purchase>> getPurchasesByUserId() {
        return purchases;
    }
}

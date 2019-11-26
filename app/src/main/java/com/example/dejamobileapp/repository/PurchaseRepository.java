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

public class PurchaseRepository {

    private PurchaseDao purchaseDao;
    private LiveData<List<Purchase>> purchases;

    public PurchaseRepository(@NonNull Application application) {
        purchaseDao = AppDatabase.getAppDatabaseInstance(application).purchaseDao();
        purchases = purchaseDao.getAllPurchases();
    }

    public LiveData<List<Purchase>> getPurchases() {
        return this.purchases;
    }

    public void insertAll(Purchase... purchases) {
        AppDatabase.databaseWriteExecutor.execute(() -> purchaseDao.insertAllPurchases(purchases));
    }

    public void update(Purchase purchase) {
        AppDatabase.databaseWriteExecutor.execute(() -> purchaseDao.updatePurchase(purchase));
    }

    public void delete(Purchase purchase) {
        AppDatabase.databaseWriteExecutor.execute(() -> purchaseDao.delete(purchase));
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> purchaseDao.deleteAllPurchases());
    }

    public Purchase getPurchaseById(int id) throws ExecutionException, InterruptedException {
        Callable<Purchase> callable = () -> purchaseDao.loadPurchaseById(id);
        Future<Purchase> future = AppDatabase.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    public List<Purchase> getPurchasesByCardId(int id) throws ExecutionException, InterruptedException {
        Callable<List<Purchase>> callable = () -> purchaseDao.loadAllPurchasesByCardId(id);
        Future<List<Purchase>> future = AppDatabase.databaseWriteExecutor.submit(callable);
        return future.get();
    }
}

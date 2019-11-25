package com.example.dejamobileapp.repository;

import android.app.Application;

import com.example.dejamobileapp.dao.PurchaseDao;
import com.example.dejamobileapp.database.AppDatabase;
import com.example.dejamobileapp.model.Purchase;

import java.util.List;

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

    public Purchase getPurchaseById(int id) {
        return purchaseDao.loadPurchaseById(id);
    }

    public LiveData<List<Purchase>> getPurchasesByCardId(int id) {
        return purchaseDao.loadAllPurchasesByCardId(id);
    }
}

package com.example.dejamobileapp.viewmodel;

import android.app.Application;
import com.example.dejamobileapp.model.Purchase;
import com.example.dejamobileapp.repository.PurchaseRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PurchaseViewModel extends AndroidViewModel {

    private PurchaseRepository purchaseRepository;
    private LiveData<List<Purchase>> purchases;

    public PurchaseViewModel(@NonNull Application application) {
        super(application);
        purchaseRepository = new PurchaseRepository(application);
        purchases = purchaseRepository.getPurchases();
    }

    public LiveData<List<Purchase>> getPurchases() { return purchases; }
    public void insert(Purchase... purchases) {purchaseRepository.insertAll(purchases);}
    public void update(Purchase purchase) {purchaseRepository.update(purchase);}
    public void delete(Purchase purchase) {purchaseRepository.delete(purchase);}
    public void deleteAll() {purchaseRepository.deleteAll();}
    public Purchase getPurchaseById(int id) throws ExecutionException, InterruptedException {return purchaseRepository.getPurchaseById(id);}
    public LiveData<List<Purchase>> getPurchasesByCardId(int id) throws ExecutionException, InterruptedException {return purchaseRepository.getPurchasesByCardId(id);}
}

package com.example.dejamobileapp.viewmodel;

import android.app.Application;
import com.example.dejamobileapp.model.Purchase;
import com.example.dejamobileapp.repository.PurchaseRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Class in charge of the link between the purchase view and the purchase model
 */
public class PurchaseViewModel extends AndroidViewModel {

    private PurchaseRepository purchaseRepository;
    private LiveData<List<Purchase>> purchases;

    public PurchaseViewModel(@NonNull Application application, int userId) {
        super(application);
        purchaseRepository = new PurchaseRepository(application, userId);
        purchases = purchaseRepository.getPurchasesByUserId();
    }

    /**
     * Method which get back all the purchases in real time
     * @return all purchases from the purchaseRepository
     */
    public LiveData<List<Purchase>> getPurchases() { return purchases; }

    /**
     * Method which insert a list of purchases
     * @param purchases list of purchases to insert with the purchaseRepository help
     */
    public void insert(Purchase... purchases) {purchaseRepository.insertAll(purchases);}

    /**
     * Method which update a purchase with purchaseRepository help
     * @param purchase the purchase to update
     */
    public void update(Purchase purchase) {purchaseRepository.update(purchase);}

    /**
     * Method which delete a purchase
     * @param purchase the purchase to delete with the purchaseRepository help
     */
    public void delete(Purchase purchase) {purchaseRepository.delete(purchase);}

    /**
     * Method which delete all the purchases with purchaseRepository help
     */
    public void deleteAll() {purchaseRepository.deleteAll();}

    /**
     * Method which get a purchase by an identifier
     * @param id the purchase identifier
     * @return the purchase corresponding to the id with purchaseRepository help
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public Purchase getPurchaseById(int id) throws ExecutionException, InterruptedException {return purchaseRepository.getPurchaseById(id);}

    /**
     * Method which get back a list of purchase by a card identifier
     * @param id the card identifier
     * @return a list of purchases corresponding to the card identifier
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public List<Purchase> getPurchasesByCardId(int id) throws ExecutionException, InterruptedException {return purchaseRepository.getPurchasesByCardId(id);}

    /**
     * Method which get back user purchases in real time
     * @return user purchases from the purchaseRepository
     */
    public LiveData<List<Purchase>> getPurchasesByUserId() { return purchases; }
}

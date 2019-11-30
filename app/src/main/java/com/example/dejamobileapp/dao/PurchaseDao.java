package com.example.dejamobileapp.dao;

import com.example.dejamobileapp.model.Purchase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Class in charge of regroup purchase methods to interact with room database
 */
@Dao
public interface PurchaseDao {

    @Query("SELECT * FROM purchase")
    LiveData<List<Purchase>> getAllPurchases();

    @Query("SELECT * FROM purchase WHERE id = :id LIMIT 1")
    Purchase loadPurchaseById(int id);

    @Insert(onConflict = REPLACE)
    void insertAllPurchases(Purchase... purchases);

    @Delete
    void delete(Purchase purchase);

    @Update(onConflict = REPLACE)
    void updatePurchase(Purchase purchase);

    @Query("DELETE FROM purchase")
    void deleteAllPurchases();

    @Query("SELECT * FROM purchase WHERE card_id = :cardId")
    List<Purchase> loadAllPurchasesByCardId(int cardId);

    @Query("SELECT * FROM purchase WHERE card_id IN (SELECT id FROM card WHERE user_id = :userId)")
    LiveData<List<Purchase>> getPurchasesByUserId(int userId);
}

package com.example.dejamobileapp.dao;

import com.example.dejamobileapp.model.Card;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CardDao {
    @Query("SELECT * FROM card")
    LiveData<List<Card>> getAllCards();

    @Query("SELECT * FROM card WHERE id = :id LIMIT 1")
    Card loadCardById(int id);

    @Query("SELECT * FROM card WHERE user_id = :userId")
    LiveData<List<Card>> loadAllCardsByUserId(int userId);

    @Insert(onConflict = REPLACE)
    void insertAllCards(Card... cards);

    @Delete
    void delete(Card card);

    @Query("DELETE FROM card")
    void deleteAllCards();

    @Query("DELETE FROM card where user_id = :userId")
    void deleteAllUserCards(int userId);

    @Update(onConflict = REPLACE)
    void updateCard(Card card);

    @Insert(onConflict = REPLACE)
    long insert(Card card);
}

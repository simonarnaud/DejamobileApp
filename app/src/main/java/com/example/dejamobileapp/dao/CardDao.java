package com.example.dejamobileapp.dao;

import com.example.dejamobileapp.model.Card;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CardDao {
    @Query("SELECT * FROM card")
    List<Card> getAllCards();

    @Query("SELECT * FROM card WHERE id IN (:cardIds)")
    List<Card> loadAllCardsByIds(int[] cardIds);

    @Query("SELECT * FROM card WHERE user_id = (:userId)")
    List<Card> loadAllCardsByUserId(int userId);

    @Insert(onConflict = REPLACE)
    void insertAllCards(Card... cards);

    @Delete
    void delete(Card card);

    @Query("DELETE FROM card")
    void deleteAllCards();

    @Update(onConflict = REPLACE)
    void updateCard(Card card);
}

package com.example.dejamobileapp.dao;

import com.example.dejamobileapp.model.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllUsersByIds(int[] userIds);

    @Insert(onConflict = REPLACE)
    void insertAllUsers(User... users);

    @Insert(onConflict = REPLACE)
    long insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAllUsers();

    @Update(onConflict = REPLACE)
    void updateUser(User user);
}

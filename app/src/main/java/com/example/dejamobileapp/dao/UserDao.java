package com.example.dejamobileapp.dao;

import com.example.dejamobileapp.model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    User loadUserById(int id);

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

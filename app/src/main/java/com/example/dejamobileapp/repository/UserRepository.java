package com.example.dejamobileapp.repository;

import android.app.Application;

import com.example.dejamobileapp.dao.UserDao;
import com.example.dejamobileapp.database.AppDatabase;
import com.example.dejamobileapp.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> users;

    public UserRepository(@NonNull Application application) {
        userDao = AppDatabase.getAppDatabaseInstance(application).userDao();
        users = userDao.getAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void insertAll(User... users) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insertAllUsers(users));
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.updateUser(user));
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.deleteAllUsers());
    }

    public void delete(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.delete(user));
    }

    public User getUserById(int id) {
        return userDao.loadUserById(id);
    }
}

package com.example.dejamobileapp.repository;

import android.app.Application;

import com.example.dejamobileapp.dao.UserDao;
import com.example.dejamobileapp.database.AppDatabase;
import com.example.dejamobileapp.model.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Class in charge of the link between the dao user methods and the userviewmodel
 */
public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> users;

    public UserRepository(@NonNull Application application) {
        userDao = AppDatabase.getAppDatabaseInstance(application).userDao();
        users = userDao.getAllUsers();
    }

    /**
     * Method which get back all the users in real time
     * @return all users from the room database
     */
    public LiveData<List<User>> getUsers() {
        return users;
    }

    /**
     * Method which insert a list of users
     * @param users list of users to insert into the room database
     */
    public void insertAll(User... users) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insertAllUsers(users));
    }

    /**
     * Method which insert a user
     * @param user the user to insert into the room database
     */
    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    /**
     * Method which update a user into the room database
     * @param user the user to update
     */
    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.updateUser(user));
    }

    /**
     * Method which delete all the users from the room database
     */
    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.deleteAllUsers());
    }

    /**
     * Method which delete a user
     * @param user the user to delete from the room database
     */
    public void delete(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.delete(user));
    }

    /**
     * Method which get a user by an identifier
     * @param id the user identifier
     * @return the user corresponding to the id from the room database
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public User getUserById(int id) throws ExecutionException, InterruptedException {
        Callable<User> callable = () -> userDao.loadUserById(id);
        Future<User> future = AppDatabase.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    /**
     * Method which get a user in function of an email and a password from the room database
     * @param email the email
     * @param password the password
     * @return a user if he exist
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public User tryToLogOn(String email, String password) throws ExecutionException, InterruptedException {
        Callable<User> callable = () -> userDao.tryToLogOn(email, password);
        Future<User> future = AppDatabase.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    /**
     * Method which get back the list of all emails into the room database
     * @return the list of emails
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public List<String> getEmails() throws ExecutionException, InterruptedException {
        Callable<List<String>> callable = () -> userDao.getEmails();
        Future<List<String>> future = AppDatabase.databaseWriteExecutor.submit(callable);
        return future.get();
    }
}

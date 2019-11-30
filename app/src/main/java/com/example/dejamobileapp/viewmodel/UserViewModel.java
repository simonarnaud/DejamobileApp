package com.example.dejamobileapp.viewmodel;

import android.app.Application;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.repository.UserRepository;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Class in charge of the link between the user view and the user model
 */
public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getUsers();
    }

    /**
     * Method which get back all the cards in real time
     * @return all cards from the cardRepository
     */
    public LiveData<List<User>> getUsers() { return users; }

    /**
     * Method which insert a list of users
     * @param users list of users to insert with the userRepository help
     */
    public void insert(User... users) {userRepository.insertAll(users);}

    /**
     * Method which insert a user
     * @param user the user to insert with the userRepository help
     */
    public void insert(User user) {userRepository.insert(user);}

    /**
     * Method which delete a user
     * @param user the user to delete with the userRepository help
     */
    public void delete(User user) {userRepository.delete(user);}

    /**
     * Method which delete all the users with userRepository help
     */
    public void deleteAll() {userRepository.deleteAll();}

    /**
     * Method which update a user with userRepository help
     * @param user the user to update
     */
    public void update(User user) {userRepository.update(user);}

    /**
     * Method which get a user by an identifier
     * @param id the user identifier
     * @return the user corresponding to the id with userRepository help
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public User getUserById(int id) throws ExecutionException, InterruptedException {return userRepository.getUserById(id);}

    /**
     * Method which get a user in function of an email and a password with the userRepository help
     * @param email the email
     * @param password the password
     * @return a user if he exist
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public User tryToLogOn(String email, String password) throws ExecutionException, InterruptedException {return userRepository.tryToLogOn(email, password);}

    /**
     * Method which get all emails with the userRepository help
     * @return the list of emails
     * @throws ExecutionException execution exception
     * @throws InterruptedException interrupted exception
     */
    public List<String> getEmails() throws ExecutionException, InterruptedException {return userRepository.getEmails();}
}

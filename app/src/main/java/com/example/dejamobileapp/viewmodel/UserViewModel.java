package com.example.dejamobileapp.viewmodel;

import android.app.Application;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.repository.UserRepository;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getUsers();
    }

    public LiveData<List<User>> getUsers() { return users; }
    public void insert(User... users) {userRepository.insertAll(users);}
    public void insert(User user) {userRepository.insert(user);}
    public void delete(User user) {userRepository.delete(user);}
    public void deleteAll() {userRepository.deleteAll();}
    public void update(User user) {userRepository.update(user);}
    public User getUserById(int id) {return userRepository.getUserById(id);}
}

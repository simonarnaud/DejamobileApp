package com.example.dejamobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.os.Bundle;
import android.widget.Toast;

import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.Gender;
import com.example.dejamobileapp.viewmodel.CardViewModel;
import com.example.dejamobileapp.viewmodel.UserViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserViewModel userViewModel = new UserViewModel(getApplication());

    }
}

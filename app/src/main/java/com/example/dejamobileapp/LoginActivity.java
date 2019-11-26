package com.example.dejamobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.viewmodel.UserViewModel;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private TextView textSignup;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        Button loginButton = findViewById(R.id.login_button);
        textSignup = findViewById(R.id.text_signup);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        loginButton.setOnClickListener(view -> {
            User user = null;
            try {
                user = userViewModel.tryToLogOn(inputEmail.getText().toString(), inputPassword.getText().toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (user != null) {
               Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
           } else {
               Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
           }
        });

    }
}

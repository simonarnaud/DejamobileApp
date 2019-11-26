package com.example.dejamobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.viewmodel.UserViewModel;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        Button loginButton = findViewById(R.id.login_button);
        TextView textSignup = findViewById(R.id.text_signup);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        loginButton.setOnClickListener(view -> {
            User user = null;
            try {
                user = userViewModel.tryToLogOn(inputEmail.getText().toString(), inputPassword.getText().toString());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            if (user != null) {
               Intent intent = new Intent(this, CardListActivity.class);
               intent.putExtra("user", user);
               startActivity(intent);
           } else {
               Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show();
           }
        });

        textSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}

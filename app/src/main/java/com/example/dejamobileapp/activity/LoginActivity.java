package com.example.dejamobileapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.Codes;
import com.example.dejamobileapp.viewmodel.UserViewModel;

import java.util.concurrent.ExecutionException;


/**
 * Class in charge of Login presentation
 */
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
            User user;
            try {
                user = userViewModel.tryToLogOn(inputEmail.getText().toString(), inputPassword.getText().toString());
                if (user != null) {
                    inputEmail.getText().clear();
                    inputEmail.requestFocus();
                    inputPassword.getText().clear();
                    Intent intent = new Intent(this, PrincipalActivity.class);
                    intent.putExtra(Codes.USER_SEND_CODE, user);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                }

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        textSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}

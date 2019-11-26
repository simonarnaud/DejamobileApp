package com.example.dejamobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dejamobileapp.converter.GenderConverter;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.Gender;
import com.example.dejamobileapp.viewmodel.UserViewModel;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, password, confirmPassword;
    private Spinner spinnerGender;
    private List<String> emails;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bindViewItems();
        Button signup = findViewById(R.id.button_signup);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        getEmails();

        spinnerGender.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Gender.values()));

        signup.setOnClickListener(view -> {
            if(allFieldAreCorrect()) {
                User user = new User(0,
                        firstName.getText().toString().toLowerCase(),
                        lastName.getText().toString().toLowerCase(),
                        email.getText().toString(),
                        GenderConverter.toGender(spinnerGender.getSelectedItem().toString()),
                        password.getText().toString(),
                        false);

                if(emails != null && !emails.contains(email.getText().toString())) {
                    userViewModel.insert(user);
                    finish();
                } else {
                    Toast.makeText(this, "Failed to create new account, this mail address might be already used.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "A field is not fill correctly", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindViewItems() {
        firstName = findViewById(R.id.edit_firstname);
        lastName = findViewById(R.id.edit_lastname);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        confirmPassword = findViewById(R.id.edit_confirm_password);
        spinnerGender = findViewById(R.id.spinner_gender);
    }

    private void getEmails() {
        try {
            emails = userViewModel.getEmails();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean allFieldAreCorrect() {
        return !firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && checkPassword();
    }

    private boolean checkPassword() {
        return !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty() && confirmPassword.getText().toString().equals(password.getText().toString());
    }
}

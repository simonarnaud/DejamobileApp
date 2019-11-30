package com.example.dejamobileapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.converter.GenderConverter;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.Gender;
import com.example.dejamobileapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class in charge of sign up a user
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, password, confirmPassword;
    private Spinner spinnerGender;
    private List<String> emails, genders;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bindViewItems();
        Button signup = findViewById(R.id.button_signup);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        getEmails();

        setGenders();
        spinnerGender.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, genders));

        signup.setOnClickListener(view -> {
            if(allFieldAreCorrect()) {
                User user = new User(0,
                        firstName.getText().toString().toLowerCase(),
                        lastName.getText().toString().toLowerCase(),
                        email.getText().toString(),
                        getGender(spinnerGender.getSelectedItem().toString()),
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

    /**
     * Method which bind items view to the variables
     */
    private void bindViewItems() {
        firstName = findViewById(R.id.edit_firstname);
        lastName = findViewById(R.id.edit_lastname);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        confirmPassword = findViewById(R.id.edit_confirm_password);
        spinnerGender = findViewById(R.id.spinner_gender);
    }

    /**
     * Method which affect to emails variable all emails get from room database
     */
    private void getEmails() {
        try {
            emails = userViewModel.getEmails();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method which test if textfield are in correct form
     * @return if all fields are in correct form
     */
    private boolean allFieldAreCorrect() {
        return !firstName.getText().toString().isEmpty()
                && !lastName.getText().toString().isEmpty()
                && checkPassword()
                && isEmailValid(email.getText().toString());
    }

    /**
     * Method which check if the password and the confirmPassword field are fill correctly
     * @return if the password is in correct form
     */
    private boolean checkPassword() {
        return !password.getText().toString().isEmpty()
                && !confirmPassword.getText().toString().isEmpty()
                && confirmPassword.getText().toString().equals(password.getText().toString());
    }

    /**
     * Method which check if the email is in correct form
     * @param email the email enter by the user
     * @return if the email is in correct form
     */
    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Method which set list of genders with right translations
     */
    private void setGenders() {
        genders = new ArrayList<>();
        genders.add(getResources().getString(R.string.male));
        genders.add(getResources().getString(R.string.female));
        genders.add(getResources().getString(R.string.other));
    }

    /**
     * Method which return the right gender comparing string with a translate
     * @param gender the string containing the gender
     * @return the right gender form
     */
    private Gender getGender(String gender) {
        if(gender.equals(getResources().getString(R.string.male))) {
            return Gender.MALE;
        } else if(gender.equals(getResources().getString(R.string.female))) {
            return Gender.FEMALE;
        }
        return Gender.OTHER;
    }
}

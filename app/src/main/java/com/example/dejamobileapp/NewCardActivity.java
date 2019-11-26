package com.example.dejamobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewCardActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.cardlistsql.REPLY";

    private EditText editCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        editCardView = findViewById(R.id.edit_card);
        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(editCardView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String card = editCardView.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, card);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}

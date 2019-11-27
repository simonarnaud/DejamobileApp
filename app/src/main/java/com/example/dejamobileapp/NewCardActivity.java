package com.example.dejamobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NewCardActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.cardlistsql.REPLY";

    private EditText editCardNumber, editCardExpiration, editCardCvv;
    private ImageView visa, mastercard, dci, jcb, maestro, cmi, cardScheme;
    private List<ImageView> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        bindViewItems();
        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(editCardNumber.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String card = editCardNumber.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, card);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        visa.setOnClickListener(view -> alphaCardGesture(visa));
        mastercard.setOnClickListener(view -> alphaCardGesture(mastercard));
        dci.setOnClickListener(view -> alphaCardGesture(dci));
        jcb.setOnClickListener(view -> alphaCardGesture(jcb));
        maestro.setOnClickListener(view -> alphaCardGesture(maestro));
        cmi.setOnClickListener(view -> alphaCardGesture(cmi));
    }

    private void bindViewItems() {
        visa = findViewById(R.id.bt_visa);
        mastercard = findViewById(R.id.bt_mastercard);
        dci = findViewById(R.id.bt_dci);
        jcb = findViewById(R.id.bt_jcb);
        maestro = findViewById(R.id.bt_maestro);
        cmi = findViewById(R.id.bt_cmi);
        editCardNumber = findViewById(R.id.edit_card);
        cardScheme = findViewById(R.id.bt_card_scheme);
        editCardExpiration = findViewById(R.id.edit_expiration);
        editCardCvv = findViewById(R.id.edit_cvv);

        cards = new ArrayList<>();
        cards.add(visa);
        cards.add(mastercard);
        cards.add(dci);
        cards.add(jcb);
        cards.add(maestro);
        cards.add(cmi);
    }

    private void alphaCardGesture(ImageView imageClicked) {
        for(ImageView image : cards) {
            if(image.equals(imageClicked)) {
                image.setAlpha(1.0f);
                cardScheme.setImageDrawable(image.getDrawable());
            } else {
                image.setAlpha(0.5f);
            }
        }
    }
}

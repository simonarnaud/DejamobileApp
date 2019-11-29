package com.example.dejamobileapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.fragment.CardListFragment;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.utils.CardFormatter;
import com.example.dejamobileapp.utils.CardScheme;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NewCardActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "REPLY_NEW_CARD";

    private EditText editCardNumber, editCardExpiration, editCardCvv;
    private ImageView visa, mastercard, dci, jcb, maestro, cmi, cardScheme;
    private List<ImageView> cards;
    private Button button;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        userId = (int)getIntent().getSerializableExtra(CardListFragment.USER_ID_CODE);

        bindViewItems();
        cardClickGesture();
        cardNumberFilters();
        expirationDateFilters();

        cardListener(editCardExpiration, '/', 3, 3);
        cardListener(editCardNumber, ' ', 5, 4);

        saveCard();
    }

    private void saveCard() {
        button.setOnClickListener(view -> {
            Intent reply = new Intent();
            Date expiration = expirationDateValidation();
            String numbers = numberCardValidation();
            String cvv = cvvCardValidation();
            CardScheme scheme = cardSchemeChecker();

            if(expiration != null && numbers != null && cvv != null) {
                Card card = new Card(0, numbers, cvv, expiration, scheme, userId, false);
                reply.putExtra(EXTRA_REPLY, card);
                setResult(RESULT_OK, reply);
                finish();
            }
        });
    }

    private void cardNumberFilters() {
        InputFilter filter = (charSequence, start, end, spanned, i2, i3) -> {
            for (int i = start; i < end; i++) {
                if (!Character.isDigit(charSequence.charAt(i)) && charSequence.charAt(i) != ' ') {
                    return "";
                }
            }
            return null;
        };
        editCardNumber.setFilters(new InputFilter[] {filter, new InputFilter.LengthFilter(19)});
    }

    private void expirationDateFilters() {
        InputFilter filter = (charSequence, start, end, spanned, i2, i3) -> {
            for (int i = start; i < end; i++) {
                if(!Character.isDigit(charSequence.charAt(i)) && charSequence.charAt(i) != '/') {
                    return "";
                }
                if(charSequence.charAt(i) == '/' && editCardExpiration.getText().toString().length() < 2) { //test if edit contain the two first digits before "/"
                    return "";
                }
                if(charSequence.charAt(i) == '/' && editCardExpiration.getText().toString().length() > 3) { //test if edit already contain "/" before the two last digits
                    return "";
                }
            }
            return null;
        };
        editCardExpiration.setFilters(new InputFilter[] {filter, new InputFilter.LengthFilter(5)});
    }

    private void cardListener(EditText editCardExpiration, char character, int divider, int blocks) {
        editCardExpiration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {CardFormatter.cardFormatter(editable, character, divider, blocks);}
        });
    }

    private void cardClickGesture() {
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
        button = findViewById(R.id.button_save);

        editCardExpiration.setOnClickListener(view -> editCardExpiration.getText().clear());
        editCardExpiration.setOnKeyListener((view, i, keyEvent) -> {
            System.out.println(i);
            if(i == KeyEvent.KEYCODE_DEL) {
                editCardExpiration.getText().clear();
            }
            return false;
        });

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

    private Date expirationDateValidation() {
        if(editCardExpiration.getText().toString().length() == 5) {

            String month = editCardExpiration.getText().toString().substring(0, 2);
            String year = editCardExpiration.getText().toString().substring(3);

            if(Integer.parseInt(month) <= 12) {
                Calendar cal = Calendar.getInstance();
                long actual = cal.getTimeInMillis();

                cal.set(Integer.parseInt("20" + year), Integer.parseInt(month) - 1, 1);        //find better solution for "20"
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                long cardExpiration = cal.getTimeInMillis();

                if (actual <= cardExpiration) {
                    editCardExpiration.setTextColor(getColor(R.color.blackTextColor));
                    return cal.getTime();
                }
            }
        }
        editCardExpiration.setTextColor(getColor(R.color.error));
        return null;
    }

    private String numberCardValidation() {
        if(editCardNumber.getText().toString().length() == 19) {
            editCardNumber.setTextColor(getColor(R.color.blackTextColor));
            return editCardNumber.getText().toString().replace(" ", "");
        }
        editCardNumber.setTextColor(getColor(R.color.error));
        return null;
    }

    private String cvvCardValidation() {
        if(editCardCvv.getText().toString().length() == 3) {
            editCardCvv.setTextColor(getColor(R.color.blackTextColor));
            return editCardCvv.getText().toString();
        }
        editCardCvv.setTextColor(getColor(R.color.error));
        return null;
    }

    private CardScheme cardSchemeChecker() {
        final Bitmap actualCardImage = ((BitmapDrawable)cardScheme.getDrawable()).getBitmap();

        if(actualCardImage == ((BitmapDrawable) Objects.requireNonNull(getDrawable(R.drawable.bt_ic_visa))).getBitmap()) {
            return CardScheme.VISA;
        } else if (actualCardImage == ((BitmapDrawable) Objects.requireNonNull(getDrawable(R.drawable.bt_ic_mastercard))).getBitmap()) {
            return CardScheme.MASTERCARD;
        } else if (actualCardImage == ((BitmapDrawable) Objects.requireNonNull(getDrawable(R.drawable.bt_ic_maestro))).getBitmap()) {
            return CardScheme.MAESTRO;
        } else if (actualCardImage == ((BitmapDrawable) Objects.requireNonNull(getDrawable(R.drawable.bt_ic_jcb))).getBitmap()) {
            return CardScheme.JCB;
        } else if (actualCardImage == ((BitmapDrawable) Objects.requireNonNull(getDrawable(R.drawable.bt_ic_dci))).getBitmap()) {
            return CardScheme.DCI;
        }
        return CardScheme.CMI;
    }
}

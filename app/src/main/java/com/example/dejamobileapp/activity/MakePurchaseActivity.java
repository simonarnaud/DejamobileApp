package com.example.dejamobileapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.factory.PurchaseViewModelFactory;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.Purchase;
import com.example.dejamobileapp.viewmodel.PurchaseViewModel;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MakePurchaseActivity extends AppCompatActivity {

    private Card card;
    private int userId;
    private EditText purchaseDescription;
    private ImageView nfcPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_purchase);

        card = (Card)getIntent().getSerializableExtra(PrincipalActivity.CARD_SEND_CODE);
        userId = getIntent().getIntExtra(PrincipalActivity.USER_ID_CODE,-1);

        purchaseDescription = findViewById(R.id.purchase_description);
        nfcPurchase = findViewById(R.id.nfc_button);

        realysePurchase();
    }

    private void realysePurchase() {

        //request the plateform with nfc for make purchase
        //then ask to the bank if the payment can be valdiate
        //get back the amount and the destination of the payment (i suppose date is the date at the moment)

        nfcPurchase.setOnClickListener(view -> {
            Purchase purchase = generatePurchase();
            PurchaseViewModelFactory purchaseFactory = new PurchaseViewModelFactory(getApplication(), userId);
            PurchaseViewModel purchaseViewModel = ViewModelProviders.of(this, purchaseFactory).get(PurchaseViewModel.class);
            purchaseViewModel.insert(purchase);

            simulatePaymentValidation();
        });
    }

    private Purchase generatePurchase() {
        return new Purchase(0, card.getCardId(), 30, purchaseDescription.getText().toString(), "Caen baker", new Date(), false);
    }

    private void simulatePaymentValidation() {
        AlertDialog dialog = new AlertDialog.Builder(this).setCancelable(false).setView(R.layout.layout_loading_dialog).create();
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            dialog.dismiss();
            setResult(RESULT_OK, new Intent());
            finish();
        }, 2000);
    }
}

package com.example.dejamobileapp.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.converter.GenderConverter;
import com.example.dejamobileapp.factory.CardViewModelFactory;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.Codes;
import com.example.dejamobileapp.viewmodel.CardViewModel;
import com.example.dejamobileapp.viewmodel.UserViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * Class in charge of control the profile list fragment
 */
public class ProfileFragment extends Fragment {

    private User user;

    private TextView userCardsOwned, userEmail, userName;
    private Button buttonRemoveAccount;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        assert getArguments() != null;
        user = (User) getArguments().getSerializable(Codes.USER_CODE);
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        bindViewItems(view);
        setItemsText();

        buttonRemoveAccount.setOnClickListener(view1 -> confirmRemoveAccount());
    }

    /**
     * Method which display an alert dialog to know if the user really want to delete his account
     */
    private void confirmRemoveAccount() {
        DialogInterface.OnClickListener dialogClickListener = (dialogInterface, i) -> {
            if (i == DialogInterface.BUTTON_POSITIVE) {
                UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
                userViewModel.delete(user);
                activity.finish();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(getResources().getString(R.string.delete_account_confirmation))
                .setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
    }

    /**
     * Method which bind the list of cards to the room card database
     */
    private void setUserCardsOwnedObserver() {
        CardViewModelFactory factory = new CardViewModelFactory(activity.getApplication(), user.getUserId());
        CardViewModel cardViewModel = ViewModelProviders.of(this, factory).get(CardViewModel.class);

        cardViewModel.getCardsByUserId().observe(this, cards -> userCardsOwned.setText(getResources().getString(R.string.cards_owned_end, cards.size())));
    }

    /**
     * Method which bind items view to the variables
     * @param view the actual view
     */
    private void bindViewItems(@NonNull View view) {
        userCardsOwned = view.findViewById(R.id.user_cards_owned);
        userEmail = view.findViewById(R.id.user_email);
        userName = view.findViewById(R.id.user_name);
        buttonRemoveAccount = view.findViewById(R.id.button_remove_account);
    }

    /**
     * Method which set text into textview
     */
    private void setItemsText() {
        userName.setText(GenderConverter.getGenderTitle(user.getGender(), getContext()).concat(user.getCardIdentity()));
        userEmail.setText(user.getEmail());
        setUserCardsOwnedObserver();
    }
}

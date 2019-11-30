package com.example.dejamobileapp.factory;

import android.app.Application;

import com.example.dejamobileapp.viewmodel.CardViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Class in charge of create a ViewModelProvider for card
 */
public class CardViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int userId;

    public CardViewModelFactory(Application application, int userId) {
        this.application = application;
        this.userId = userId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CardViewModel(application, userId);
    }
}

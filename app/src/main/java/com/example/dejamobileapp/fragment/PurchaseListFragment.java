package com.example.dejamobileapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.activity.PrincipalActivity;
import com.example.dejamobileapp.adapter.PurchaseListAdapter;
import com.example.dejamobileapp.factory.PurchaseViewModelFactory;
import com.example.dejamobileapp.model.Purchase;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.viewmodel.PurchaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PurchaseListFragment extends Fragment {

    private ImageView emptyPurchases;
    private User user;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        user = (User) getArguments().getSerializable(PrincipalActivity.USER_CODE);
        return inflater.inflate(R.layout.fragment_purchase_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        emptyPurchases = view.findViewById(R.id.empty_purchases);

        setPurchaseList(view);
    }

    private void setPurchaseList(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_purchase);
        final PurchaseListAdapter adapter = new PurchaseListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        PurchaseViewModelFactory purchaseFactory = new PurchaseViewModelFactory(activity.getApplication(), user.getUserId());
        PurchaseViewModel purchaseViewModel = ViewModelProviders.of(this, purchaseFactory).get(PurchaseViewModel.class);

        purchaseViewModel.getPurchasesByUserId().observe(this, purchases -> {
            adapter.setPurchases(purchases);
            setEmptyPurchasesImage(purchases);
        });
    }

    private void setEmptyPurchasesImage(List<Purchase> purchases) {
        if(purchases.size() > 0) {
            emptyPurchases.setVisibility(View.GONE);
        } else {
            emptyPurchases.setVisibility(View.VISIBLE);
        }
    }
}

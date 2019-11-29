package com.example.dejamobileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.model.Purchase;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.PurchaseViewHolder> {

    class PurchaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView purchaseDate, purchaseDestination, purchaseAmount, purchaseDescription;

        private PurchaseViewHolder(View itemView) {
            super(itemView);
            purchaseDate = itemView.findViewById(R.id.purchase_item_date);
            purchaseAmount = itemView.findViewById(R.id.purchase_item_amount);
            purchaseDestination = itemView.findViewById(R.id.purchase_item_destinary);
            purchaseDescription = itemView.findViewById(R.id.purchase_item_description);
        }
    }

    private final LayoutInflater inflater;
    private List<Purchase> purchases;

    public PurchaseListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NotNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_purchase_item, parent, false);
        return new PurchaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull PurchaseViewHolder holder, int position) {
        if (purchases != null) {
            Purchase currentPurchase = purchases.get(position);

            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(inflater.getContext());
            holder.purchaseDate.setText(dateFormat.format(currentPurchase.getDate()));
            holder.purchaseAmount.setText(inflater.getContext().getResources().getString(R.string.purchase_amount_euros, currentPurchase.getAmount()));
            holder.purchaseDestination.setText(currentPurchase.getDestination());
            holder.purchaseDescription.setText(currentPurchase.getDescription());

            if(holder.purchaseDescription.getText().toString().length() < 1) {
                holder.purchaseDescription.setVisibility(View.GONE);
            } else {
                holder.purchaseDescription.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (purchases != null) {
            return purchases.size();
        }
        return 0;
    }
}

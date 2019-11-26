package com.example.dejamobileapp.model;

import com.example.dejamobileapp.converter.DateConverter;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "card_id",
        onDelete = ForeignKey.CASCADE
),
        tableName = "purchase")

public class Purchase {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int purchaseId;

    @ColumnInfo(name = "card_id")
    private int card;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "destination")
    private String destination;

    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter.class)
    private Date date;

    @ColumnInfo(name = "sync")
    private boolean sync;

    public Purchase(int purchaseId, @NonNull int card, @NonNull int amount, String description, @NonNull String destination, Date date, boolean sync) {
        this.purchaseId = purchaseId;
        this.card = card;
        this.amount = amount;
        this.description = (description != null && description.length() > 0) ? destination : "no description";
        this.destination = destination;
        this.date = new Date();
        this.sync = false;
    }

    public int getPurchaseId() {
        return this.purchaseId;
    }

    public int getCard() {
        return this.card;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDestination() {
        return this.destination;
    }

    public Date getDate() {
        return this.date;
    }

    public boolean getSync() {
        return this.sync;
    }
}

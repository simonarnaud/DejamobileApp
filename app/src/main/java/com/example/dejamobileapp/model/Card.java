package com.example.dejamobileapp.model;

import com.example.dejamobileapp.converter.CardSchemeConverter;
import com.example.dejamobileapp.utils.CardScheme;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
            parentColumns = "id",
            childColumns = "user_id",
            onDelete = ForeignKey.CASCADE
            ),
        tableName = "card")
public class Card {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    private int cardId;

    @ColumnInfo(name = "numbers")
    private long numbers;

    @ColumnInfo(name = "crypto")
    private int crypto;

    @ColumnInfo(name = "scheme")
    @TypeConverters(CardSchemeConverter.class)
    private CardScheme scheme;

    @ColumnInfo(name = "user_id", index = true)
    private int userId;

    @ColumnInfo(name = "sync")
    private boolean sync;

    public Card(int cardId, @NonNull long numbers, @NonNull int crypto, @NonNull CardScheme scheme, @NonNull int userId, boolean sync) {
        this.cardId = cardId;
        this.numbers = numbers;
        this.crypto = crypto;
        this.scheme = scheme;
        this.userId = userId;
        this.sync = false;
    }

    public int getCardId() {
        return this.cardId;
    }

    public long getNumbers() {
        return this.numbers;
    }

    public int getCrypto() {
        return this.crypto;
    }

    public CardScheme getScheme() {
        return this.scheme;
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean getSync() {
        return this.sync;
    }
}

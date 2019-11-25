package com.example.dejamobileapp.model;

import com.example.dejamobileapp.utils.CardScheme;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
    private CardScheme scheme;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "sync")
    private boolean sync;

    public Card(@NonNull long numbers, @NonNull int crypto, @NonNull CardScheme scheme, @NonNull int userId) {
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

package com.example.dejamobileapp.model;

import com.example.dejamobileapp.converter.CardSchemeConverter;
import com.example.dejamobileapp.converter.DateConverter;
import com.example.dejamobileapp.utils.CardScheme;

import java.io.Serializable;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * Class representing the card model
 */
@Entity(foreignKeys = @ForeignKey(entity = User.class,
            parentColumns = "id",
            childColumns = "user_id",
            onDelete = ForeignKey.CASCADE
            ),
        tableName = "card")
public class Card implements Serializable {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    private int cardId;

    @ColumnInfo(name = "numbers")
    private String numbers;

    @ColumnInfo(name = "crypto")
    private String crypto;

    @ColumnInfo(name = "expiration")
    @TypeConverters(DateConverter.class)
    private Date expiration;

    @ColumnInfo(name = "scheme")
    @TypeConverters(CardSchemeConverter.class)
    private CardScheme scheme;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "sync")
    private boolean sync;

    public Card(int cardId, String numbers, String crypto, @NonNull Date expiration, @NonNull CardScheme scheme, int userId, boolean sync) {
        this.cardId = cardId;
        this.numbers = numbers;
        this.crypto = crypto;
        this.expiration = expiration;
        this.scheme = scheme;
        this.userId = userId;
        this.sync = sync;
    }

    public int getCardId() {
        return this.cardId;
    }

    public String getNumbers() {
        return this.numbers;
    }

    public String getCrypto() {
        return this.crypto;
    }

    public Date getExpiration() { return this.expiration; }

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

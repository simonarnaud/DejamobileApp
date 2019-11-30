package com.example.dejamobileapp.model;

import com.example.dejamobileapp.converter.GenderConverter;
import com.example.dejamobileapp.utils.Gender;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * Class representing the user model
 */
@Entity(tableName = "user")
public class User implements Serializable {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    private int userId;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "gender")
    @TypeConverters(GenderConverter.class)
    private Gender gender;

    @ColumnInfo(name = "sync")
    private boolean sync;

    @ColumnInfo(name = "password")
    private String password;

    public User(int userId, @NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull Gender gender, @NonNull String password, boolean sync) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.sync = sync;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Gender getGender() {
        return this.gender;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getSync() {
        return this.sync;
    }

    public String getCardIdentity() {
        String str = (this.firstName.substring(0,1).toUpperCase() + this.firstName.substring(1));
        str += " ";
        str += this.lastName.toUpperCase();

        return str;
    }
}

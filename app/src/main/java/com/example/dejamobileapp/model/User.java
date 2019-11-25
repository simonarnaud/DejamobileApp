package com.example.dejamobileapp.model;

import com.example.dejamobileapp.utils.Gender;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
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
    private Gender gender;

    @ColumnInfo(name = "sync")
    private boolean sync;

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.sync = false;
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

    public boolean getSync() {
        return this.sync;
    }
}

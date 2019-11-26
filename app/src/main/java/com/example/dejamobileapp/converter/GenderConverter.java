package com.example.dejamobileapp.converter;

import com.example.dejamobileapp.utils.Gender;

import androidx.room.TypeConverter;

public class GenderConverter {

    @TypeConverter
    public static Gender toGender(int id) {
        switch (id) {
            case 0:
                return Gender.MALE;
            case 1:
                return Gender.FEMALE;
            default:
                return Gender.OTHER;
        }
    }

    @TypeConverter
    public static int toId(Gender gender) {
        switch (gender) {
            case MALE:
                return 0;
            case FEMALE:
                return 1;
            default:
                return 2;
        }
    }

    public static Gender toGender(String gender) {
        if(gender.toLowerCase().equals("male")) {
            return Gender.MALE;
        } else if(gender.toLowerCase().equals("female")) {
            return Gender.FEMALE;
        }
        return Gender.OTHER;
    }
}

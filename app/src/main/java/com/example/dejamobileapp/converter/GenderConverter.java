package com.example.dejamobileapp.converter;

import android.content.Context;

import com.example.dejamobileapp.R;
import com.example.dejamobileapp.utils.Gender;

import androidx.room.TypeConverter;

/**
 * Class in charge of convert Gender
 */
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

    /**
     * Method which return the title in function of gender
     * @param gender the user gender
     * @return the title associated
     */
    public static String getGenderTitle(Gender gender, Context context) {
        if(gender == Gender.MALE) {
            return context.getResources().getString(R.string.male_title).concat(" ");
        } else if(gender == Gender.FEMALE) {
            return context.getResources().getString(R.string.female_title).concat(" ");
        }
        return "";
    }
}

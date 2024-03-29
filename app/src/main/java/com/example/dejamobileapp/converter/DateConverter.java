package com.example.dejamobileapp.converter;

import java.util.Date;

import androidx.room.TypeConverter;

/**
 * Class in charge of convert Date
 */
public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

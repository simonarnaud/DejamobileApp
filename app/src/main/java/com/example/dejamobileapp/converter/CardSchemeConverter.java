package com.example.dejamobileapp.converter;

import com.example.dejamobileapp.utils.CardScheme;

import androidx.room.TypeConverter;

/**
 * Class in charge of convert CardScheme
 */
public class CardSchemeConverter {

    @TypeConverter
    public static CardScheme toCardScheme(int id) {
        switch (id) {
            case 0:
                return CardScheme.VISA;
            case 1:
                return CardScheme.CMI;
            case 2:
                return CardScheme.MASTERCARD;
            case 3:
                return CardScheme.MAESTRO;
            case 4:
                return CardScheme.DCI;
            default:
                return CardScheme.JCB;
        }
    }

    @TypeConverter
    public static int toId(CardScheme cardScheme) {
        switch(cardScheme) {
            case VISA:
                return 0;
            case CMI:
                return 1;
            case MASTERCARD:
                return 2;
            case MAESTRO:
                return 3;
            case DCI:
                return 4;
            default:
                return 5;
        }
    }
}

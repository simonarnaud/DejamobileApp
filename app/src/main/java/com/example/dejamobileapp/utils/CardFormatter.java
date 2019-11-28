package com.example.dejamobileapp.utils;

import android.text.Editable;
import android.text.TextUtils;

public class CardFormatter {

    public static void cardFormatter(Editable editable, Character character, int divider, int blocks) {
        blocks--;

        if (editable.length() > 0 && (editable.length() % divider) == 0) {
            final char c = editable.charAt(editable.length() - 1);
            if(character == c) {
                editable.delete(editable.length() - 1, editable.length());
            }
        }
        if(editable.length() > 0 && (editable.length() % divider) == 0) {
            char c = editable.charAt(editable.length() - 1);
            if (Character.isDigit(c) && TextUtils.split(editable.toString(), character.toString()).length <= blocks) {
                editable.insert(editable.length() - 1, character.toString());
            }
        }
    }

    public static String cardNumberFormatter(long numbers) {
        String chain = String.valueOf(numbers);
        return chain.substring(0,4) + " " + chain.substring(4,8) + " " + chain.substring(8,12) + " " + chain.substring(12);
    }
}

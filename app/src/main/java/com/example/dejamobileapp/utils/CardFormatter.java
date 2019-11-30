package com.example.dejamobileapp.utils;

import android.text.Editable;
import android.text.TextUtils;

/**
 * Class in charge of regroup the method to format card informations
 */
public class CardFormatter {

    /**
     * Method which format an editable with a character insert at specific entry
     * @param editable the editable to format
     * @param character the character to add
     * @param divider the divider
     * @param blocks the blocks required
     */
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

    /**
     * Method which format the card numbers
     * @param numbers the numbers to format
     * @return the string numbers formatted
     */
    public static String cardNumberFormatter(String numbers) {
        return numbers.substring(0,4) + " " + numbers.substring(4,8) + " " + numbers.substring(8,12) + " " + numbers.substring(12);
    }
}

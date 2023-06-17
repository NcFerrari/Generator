package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextFormatTest {

    @Test
    void removeDiacritics() {
        String diacritic = "Nechť již hříšné saxofony ďáblů rozezvučí síň úděsnými tóny waltzu, tanga a quickstepu.";
        String nonDiacritic = "Necht jiz hrisne saxofony dablu rozezvuci sin udesnymi tony waltzu, tanga a quickstepu.";
        assertEquals(TextFormat.removeDiacritics(diacritic), nonDiacritic);
        diacritic = "Příliš žluťoučký kůň úpěl ďábelské ódy.";
        nonDiacritic = "Prilis zlutoucky kun upel dabelske ody.";
        assertEquals(TextFormat.removeDiacritics(diacritic), nonDiacritic);
        String diacriticAlphabet = "AÁBCČDĎEÉĚFGHChIÍJKLMNŇOÓPQRŘSŠTŤUÚŮVWXYÝZŽ";
        String nonDiacriticAlphabet = "AABCCDDEEEFGHChIIJKLMNNOOPQRRSSTTUUUVWXYYZZ";
        assertEquals(TextFormat.removeDiacritics(diacriticAlphabet), nonDiacriticAlphabet);
    }


    @Test
    void addDiacritic() {
        String withoutDiacritic = "auticko";
        String correctResult = "autíčko";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < withoutDiacritic.split("").length; i++) {
            if (i > 2 && i < 5) {
                result.append(TextFormat.addDiacriticToLetter(withoutDiacritic.charAt(i)));
            } else {
                result.append(withoutDiacritic.charAt(i));
            }
        }
        assertEquals(result.toString(), correctResult);

        assertEquals("Ř", TextFormat.addDiacriticToLetter('R'));
        assertNotEquals("í", TextFormat.addDiacriticToLetter('x'));
    }
}
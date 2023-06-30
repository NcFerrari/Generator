package utils;

import generator.utils.HumanAtr;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumanAtrTest {

    @Test
    void valueOf() {
        String[] array = new String[]{
                "ALL", "NAME", "SURNAME", "SEX", "EMAIL", "BIRTH_DAY", "AGE", "STREET", "HOUSE_NUMBER"};
        int success = 0;
        for (String s : array) {
            for (HumanAtr value : HumanAtr.values()) {
                if (value == HumanAtr.valueOf(s)) {
                    success++;
                    break;
                }
            }
        }
        assertEquals(success, HumanAtr.values().length);
    }
}
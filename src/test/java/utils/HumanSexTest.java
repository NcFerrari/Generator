package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumanSexTest {

    @Test
    void getSex() {
        String uniName = "Alexis";
        String manName = "Otta";
        String womanName = "Alena";
        String manName2 = "Pavel";
        assertTrue(((HumanSex.getSex(uniName) == HumanSex.WOMAN) || (HumanSex.getSex(uniName) == HumanSex.MAN)));
        assertEquals(HumanSex.MAN, HumanSex.getSex(manName));
        assertEquals(HumanSex.WOMAN, HumanSex.getSex(womanName));
        assertEquals(HumanSex.MAN, HumanSex.getSex(manName2));
    }
}
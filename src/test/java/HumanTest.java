import generator.Human;
import org.junit.jupiter.api.Test;
import generator.utils.HumanAtr;
import generator.utils.HumanSex;
import generator.utils.TextFormat;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest {

    @Test
    void generate() {
        String name = (String) Human.generate(HumanAtr.NAME)[0];
        String surname = (String) Human.generate(HumanAtr.SURNAME)[0];
        HumanSex sex = (HumanSex) Human.generate(HumanAtr.SEX)[0];
        String email = (String) Human.generate(HumanAtr.EMAIL)[0];
        LocalDate birthDate = (LocalDate) Human.generate(HumanAtr.BIRTH_DAY)[0];
        int age = (int) Human.generate(HumanAtr.AGE)[0];
        String street = (String) Human.generate(HumanAtr.STREET)[0];
        int houseNumber = (int) Human.generate(HumanAtr.HOUSE_NUMBER)[0];

        assertNotNull(name);
        assertNotNull(surname);
        assertNotNull(sex);
        assertNotNull(email);
        assertNotNull(birthDate);
        assertTrue(age > 0);
        assertNotNull(street);
        assertTrue(houseNumber > 0);
        assertEquals(0, Human.generate().length);
    }

    @Test
    void generateMan() {
        assertEquals(HumanSex.MAN, Human.generateMan(HumanAtr.SEX)[0]);
    }

    @Test
    void generateWoman() {
        assertEquals(HumanSex.WOMAN, Human.generateWoman(HumanAtr.SEX)[0]);
    }

    @Test
    void generateWomenSurnameFromMans() {
        String[] mans = {"Pešek", "Batěk", "Bílý", "Blekota", "Drnec", "Cumel", "Novák"};
        String[] woman = {"Pešková", "Baťková", "Bílá", "Blekotová", "Drncová", "Cumlová", "Nováková"};
        for (int i = 0; i < mans.length; i++) {
            assertEquals(woman[i], Human.generateWomenSurnameFromMans(mans[i]));
        }
    }

    @Test
    void allAttributesTest() {
        Object[] allAttributes = Human.generate(HumanAtr.ALL);
        String name = (String) allAttributes[0];
        String surname = (String) allAttributes[1];
        HumanSex sex = (HumanSex) allAttributes[2];
        String email = (String) allAttributes[3];
        LocalDate birthDate = (LocalDate) allAttributes[4];
        int age = (int) allAttributes[5];
        String street = (String) allAttributes[6];
        int houseNumber = (int) allAttributes[7];

        assertEquals(TextFormat.removeDiacritics(name.toLowerCase()), email.split("\\.")[0]);
        assertEquals(TextFormat.removeDiacritics(surname.toLowerCase()), email.split("\\.")[1].split("@")[0]);
        assertEquals(HumanSex.getSex(name), sex);
        assertEquals(Period.between(birthDate, LocalDate.now()).getYears(), age);
        assertNotNull(street);
        assertTrue(houseNumber > 0);
    }

    @Test
    void ageAndBirthdayTest() {
        Object[] birthDayAndAge = Human.generate(HumanAtr.BIRTH_DAY, HumanAtr.AGE);
        LocalDate birthDay = (LocalDate) Human.generate(HumanAtr.BIRTH_DAY, HumanAtr.AGE)[0];
        int age = (int) Human.generate(HumanAtr.BIRTH_DAY, HumanAtr.AGE)[1];
        assertEquals(Period.between((LocalDate) birthDayAndAge[0], LocalDate.now()).getYears(),
                (int) birthDayAndAge[1]);
        assertNotEquals(Period.between(LocalDate.now(), birthDay).getYears(), age);
    }
}
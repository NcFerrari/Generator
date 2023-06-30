package generator;

import generator.service.FileService;
import generator.serviceimpl.FileServiceImpl;
import generator.utils.HumanAtr;
import generator.utils.HumanSex;
import generator.utils.TextEnum;
import generator.utils.TextFormat;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

public class Human {

    private static final Random RND = new Random();
    private static final String[] NAMES;
    private static final String[] MAN_SURNAMES;
    private static final String[] WOMAN_SURNAMES;
    private static final String[] DOMAINS;
    private static final String[] STREETS;

    static {
        FileService fileService = FileServiceImpl.getInstance();
        NAMES = fileService.loadData(Human.class.getClassLoader().getResourceAsStream(TextEnum.NAMES_FILE.getText()));
        MAN_SURNAMES = fileService.loadData(
                Human.class.getClassLoader().getResourceAsStream(TextEnum.MAN_SURNAMES_FILE.getText()));
        WOMAN_SURNAMES = fileService.loadData(
                Human.class.getClassLoader().getResourceAsStream(TextEnum.WOMAN_SURNAMES_FILE.getText()));
        DOMAINS = fileService.loadData(
                Human.class.getClassLoader().getResourceAsStream(TextEnum.DOMAINS_FILE.getText()));
        STREETS = fileService.loadData(
                Human.class.getClassLoader().getResourceAsStream(TextEnum.STREETS_FILE.getText()));
    }

    private Human() {

    }

    public static Object[] generate(HumanAtr... attributes) {
        return RND.nextBoolean() ? generateMan(attributes) : generateWoman(attributes);
    }

    public static Object[] generateMan(HumanAtr... attributes) {
        return generateHuman(HumanSex.MAN, attributes);
    }

    public static Object[] generateWoman(HumanAtr... attributes) {
        return generateHuman(HumanSex.WOMAN, attributes);
    }

    /**
     * @param attributes {@link HumanAtr} enum values in optional order
     * @return Object[] of attributes in order: NAME, SURNAME,SEX, EMAIL, BIRTH_DAY, AGE, STREET, HOUSE_NUMBER
     */
    private static Object[] generateHuman(HumanSex sex, HumanAtr... attributes) {
        if (attributes == null || attributes.length == 0) {
            return new String[0];
        }
        Object[] finalResult = new Object[attributes.length];
        int index = 0;
        LocalDate birthDay;
        String name = null;
        if (attributesContain(HumanAtr.NAME, attributes)) {
            name = generateName(sex);
            finalResult[index++] = name;
        }
        if (attributesContain(HumanAtr.SURNAME, attributes)) {
            finalResult[index++] = generateSurname(name == null ? generateName(sex) : name);
        }
        if (attributesContain(HumanAtr.SEX, attributes)) {
            finalResult[index++] = sex;
        }
        if (attributesContain(HumanAtr.EMAIL, attributes)) {
            finalResult[index++] = generateEmail(generateName(sex), generateSurname(generateName(sex)));
        }
        if (attributesContain(HumanAtr.BIRTH_DAY, attributes) && attributesContain(HumanAtr.AGE, attributes)) {
            birthDay = generateBirthDay();
            finalResult[index++] = birthDay;
            finalResult[index++] = Period.between(birthDay, LocalDate.now()).getYears();
        } else if (attributesContain(HumanAtr.BIRTH_DAY, attributes)) {
            birthDay = generateBirthDay();
            finalResult[index++] = birthDay;
        } else if (attributesContain(HumanAtr.AGE, attributes)) {
            finalResult[index++] = generateAge(RND.nextInt(100));
        }
        if (attributesContain(HumanAtr.STREET, attributes)) {
            finalResult[index++] = generateStreet();
        }
        if (attributesContain(HumanAtr.HOUSE_NUMBER, attributes)) {
            finalResult[index] = generateHouseNumber();
        }
        if (attributesContain(HumanAtr.ALL, attributes)) {
            finalResult = new Object[HumanAtr.values().length - 1];
            index = 0;
            name = generateName(sex);
            String surname = generateSurname(name);
            finalResult[index++] = name;
            finalResult[index++] = surname;
            finalResult[index++] = sex;
            finalResult[index++] = generateEmail(name, surname);
            birthDay = generateBirthDay();
            finalResult[index++] = birthDay;
            finalResult[index++] = Period.between(birthDay, LocalDate.now()).getYears();
            finalResult[index++] = generateStreet();
            finalResult[index] = generateHouseNumber();
        }
        return finalResult;
    }

    /**
     * <li>1. pravidlo: ek -> (-2) ková</li>
     * <li>2. pravidlo: ěk -> (-3) [zscrdtn]->[žščřďťň]ková</li>
     * <li>3. pravidlo: ý -> (-1) á</li>
     * <li>4. pravidlo: a -> (-1) ová</li>
     * <li>5. pravidlo: ec -> (-2) cová</li>
     * <li>6. pravidlo: el -> (-2) lová</li>
     * <li>7. pravidlo: name + ová</li>
     *
     * @param manSurname Man surname to generate woman one
     * @return woman surname
     */
    public static String generateWomenSurnameFromMans(String manSurname) {
        String rootOfManSurname = manSurname.substring(0, manSurname.length() - 2);
        if (manSurname.endsWith(TextEnum.FORMAT_SUFFIX_EK.getText())) {
            return rootOfManSurname + TextEnum.FORMAT_SUFFIX_KOVA.getText();
        } else if (manSurname.endsWith(TextEnum.FORMAT_SUFFIX_EK2.getText())) {
            String letter = TextFormat.addDiacriticToLetter(manSurname.charAt(manSurname.length() - 3));
            return manSurname.substring(0, manSurname.length() - 3) + letter + TextEnum.FORMAT_SUFFIX_KOVA.getText();
        } else if (manSurname.endsWith(TextEnum.FORMAT_SUFFIX_A.getText())) {
            return manSurname.substring(0, manSurname.length() - 1) + TextEnum.FORMAT_SUFFIX_OVA.getText();
        } else if (manSurname.endsWith(TextEnum.FORMAT_SUFFIX_Y.getText())) {
            return manSurname.substring(0, manSurname.length() - 1) + TextEnum.FORMAT_SUFFIX_A2.getText();
        } else if (manSurname.endsWith(TextEnum.FORMAT_SUFFIX_EC.getText())) {
            return rootOfManSurname + TextEnum.FORMAT_SUFFIX_COVA.getText();
        } else if (manSurname.endsWith(TextEnum.FORMAT_SUFFIX_EL.getText())) {
            return rootOfManSurname + TextEnum.FORMAT_SUFFIX_LOVA.getText();
        } else {
            return manSurname + TextEnum.FORMAT_SUFFIX_OVA.getText();
        }
    }

    private static String generateName(HumanSex sex) {
        String resultName;
        do {
            resultName = NAMES[RND.nextInt(NAMES.length)];
        }
        while (HumanSex.getSex(resultName) != sex);
        return resultName;
    }

    private static String generateSurname(String czechFirstName) {
        if (HumanSex.getSex(czechFirstName) == HumanSex.MAN) {
            return MAN_SURNAMES[RND.nextInt(MAN_SURNAMES.length)];
        } else {
            return WOMAN_SURNAMES[RND.nextInt(WOMAN_SURNAMES.length)];
        }
    }

    private static String generateEmail(String name, String surname) {
        return String.format(TextEnum.EMAIL_PATTERN.getText(),
                TextFormat.removeDiacritics(name.toLowerCase()),
                TextFormat.removeDiacritics(surname.toLowerCase()),
                DOMAINS[RND.nextInt(DOMAINS.length)]);
    }

    private static LocalDate generateBirthDay() {
        return LocalDate.now().minusDays(RND.nextInt(36500));
    }

    private static int generateAge(int minAge) {
        return RND.nextInt(100 - minAge) + minAge;
    }

    private static String generateStreet() {
        return STREETS[RND.nextInt(STREETS.length)];
    }

    private static int generateHouseNumber() {
        return RND.nextInt(10000);
    }

    private static boolean attributesContain(HumanAtr attribute, HumanAtr[] attributes) {
        for (HumanAtr element : attributes) {
            if (attribute == element) {
                return true;
            }
        }
        return false;
    }
}
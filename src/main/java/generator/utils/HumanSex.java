package generator.utils;

import generator.service.FileService;
import generator.serviceimpl.FileServiceImpl;

import java.util.Random;

public enum HumanSex {
    MAN,
    WOMAN;

    private static final Random RND = new Random();
    /**
     * Pravidlo pro poznání, že se jedná o ženské jméno. Ženské jméno poznáme tak, že končí na: a, y, še, ce, ie, es.
     * Potom je 13 jmen, která jsou jedinečná, proto jsou tu vypsána.
     */
    private static final String[] WOMAN_SPECIFIC;
    /**
     * 3 mužská jména se vymykají pravidlu ženských jmen (končí na "a"). Proto je potřeba je specifikovat a říct,
     * že v těchto konkrétních případech půjde o muže.
     */
    private static final String[] MAN_SPECIFIC;

    static {
        FileService fileService = FileServiceImpl.getInstance();
        WOMAN_SPECIFIC = fileService.loadData(
                HumanSex.class.getClassLoader().getResourceAsStream(TextEnum.WOMAN_SPECIFIC_FILE.getText()));
        MAN_SPECIFIC = fileService.loadData(
                HumanSex.class.getClassLoader().getResourceAsStream(TextEnum.MAN_SPECIFIC_FILE.getText()));
    }

    public static HumanSex getSex(String name) {
        //Následující jméno může být jak pro muže, tak pro ženu
        String bothPossibleName = TextEnum.ALEXIS_NAME.getText();
        if (bothPossibleName.equals(name)) {
            return RND.nextBoolean() ? MAN : WOMAN;
        }
        for (String spec : MAN_SPECIFIC) {
            if (name.endsWith(spec)) {
                return MAN;
            }
        }
        for (String spec : WOMAN_SPECIFIC) {
            if (name.endsWith(spec)) {
                return WOMAN;
            }
        }
        return MAN;
    }
}

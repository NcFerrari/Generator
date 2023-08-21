import generator.utils.TextFormat;

public class Main {

    public static void main(String[] args) {
        String text = "X_MANAGER\n" +
                "RV\n" +
                "_PLNÁ_ROLE_VERZE_1.9\n" +
                "X_OBCHOD\n" +
                "X_KONTROLA\n" +
                "X_PRODUKCE\n" +
                "X_PROVOZ\n" +
                "X_REDITEL/KA\n" +
                "Y_EMPTY\n" +
                "GEOMARKETING_PROVOZ\n" +
                "SKLADY\n" +
                "LOGISTIKA_A\n" +
                "OBLASTNÍ_VEDOUCÍ\n" +
                "REDITEL_SPOLECNOSTI\n" +
                "MDS\n" +
                "ADRESNÁ\n" +
                "REDITELKA_KLIENT_SERVISU\n" +
                "ASISTENT_KLIENTSERVISU\n" +
                "REDITELKA_FINANCÍ\n" +
                "VEDOUCÍ_SPISOVNY\n" +
                "ÚCETNÍ_RV\n" +
                "ÚCETNÍ_FIRMY\n" +
                "SPRÁVCE_POHLEDÁVEK\n" +
                "PERSONALISTA\n" +
                "REDITEL_OBCHODU\n" +
                "OBCHOD\n" +
                "GEOMARKETING\n" +
                "MANAGER_LOKÁLNÍHO_OBCHODU\n" +
                "ASISTENT_GEOMARKETING\n" +
                "VEDOUCÍ_KLIENTSERVISU\n" +
                "TEST_OBCH\n" +
                "INSPEKTOR\n" +
                "VEDOUCÍ_KVALITY\n" +
                "REDITEL_PROVOZU\n" +
                "MARKETING\n" +
                "KONTROLNI_PANEL\n" +
                "TEST\n" +
                "ISO\n" +
                "INSPEKTOR_2\n" +
                "DISTRIBUTORI\n" +
                "PRODUKCE\n" +
                "KLIENTSERVIS\n" +
                "LOGISTIKA\n" +
                "AO\n" +
                "AUDIT\n" +
                "RIDIC_RV\n" +
                "BILLA\n" +
                "VLP-B\n" +
                "PROVOZ\n" +
                "MEDVED\n" +
                "EXTERNÍ_KONTROLOR\n" +
                "SESTAVA\n" +
                "PRODEJNY\n" +
                "ASISTENT_OZP\n" +
                "KOORDINÁTOR_OZP\n" +
                "IT\n" +
                "VOZÍKY\n" +
                "NÁHRADNÍ_PLNENÍ\n";
        System.out.println(TextFormat.removeDiacritics(text));
    }
}

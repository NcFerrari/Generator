package utils;

import service.FileService;
import serviceimpl.FileServiceImpl;

public class TextFormat {

    private static final String[] DIACRITICS;
    private static final String[] WITHOUT_DIACRITICS;

    static {
        FileService fileService = FileServiceImpl.getInstance();
        String[] dataFromFile = fileService.loadData(
                TextFormat.class.getClassLoader().getResourceAsStream(TextEnum.DIACRITIC_FILE.getText()));
        DIACRITICS = new String[dataFromFile.length];
        WITHOUT_DIACRITICS = new String[dataFromFile.length];
        for (int i = 0; i < dataFromFile.length; i++) {
            DIACRITICS[i] = dataFromFile[i].split(TextEnum.COLON_MARK.getText())[0];
            WITHOUT_DIACRITICS[i] = dataFromFile[i].split(TextEnum.COLON_MARK.getText())[1];
        }
    }

    private TextFormat() {
    }

    public static String removeDiacritics(String diacritic) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String letter : diacritic.split(TextEnum.EMPTY_MARK.getText())) {
            String currentLetter = letter;
            for (int i = 0; i < DIACRITICS.length; i++) {
                if (letter.equals(DIACRITICS[i])) {
                    currentLetter = WITHOUT_DIACRITICS[i];
                    break;
                }
            }
            stringBuilder.append(currentLetter);
        }
        return stringBuilder.toString();
    }

    public static String addDiacriticToLetter(char letter) {
        for (int i = 0; i < WITHOUT_DIACRITICS.length; i++) {
            if (WITHOUT_DIACRITICS[i].equals(String.valueOf(letter))) {
                return DIACRITICS[i];
            }
        }
        return null;
    }

    public static String capitalizeOrCamelCaseText(String text, boolean camelCase) {
        StringBuilder resultText = new StringBuilder();
        text = text.replace(TextEnum.DASH_MARK.getText(), TextEnum.UNDERSCORE_MARK.getText());
        String[] words = text.toLowerCase().split(TextEnum.UNDERSCORE_MARK.getText());
        for (String word : words) {
            if (word.length() > 0) {
                resultText.append(word.substring(0, 1).toUpperCase());
                resultText.append(word.substring(1));
            }
        }
        if (camelCase) {
            return resultText.substring(0, 1).toLowerCase() + resultText.substring(1);
        }
        return resultText.toString();
    }
}

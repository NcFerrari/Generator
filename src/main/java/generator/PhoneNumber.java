package generator;

import generator.service.LoggerService;
import generator.serviceimpl.LoggerServiceImpl;
import generator.utils.TextEnum;
import org.apache.log4j.Logger;

import java.util.Random;

public class PhoneNumber {

    private static final LoggerService LOGGER_SERVICE = LoggerServiceImpl.getInstance(PhoneNumber.class);
    private static final Logger LOG = LOGGER_SERVICE.getLog();
    private static final Random RANDOM = new Random();
    private static final int[] PREFIX_NUMBERS = {601, 602, 603, 604, 605, 606, 607, 608, 702, 703, 705, 720, 721, 722, 723, 724, 725, 726, 727, 728, 729, 730, 731, 732, 733, 734, 735, 736, 737, 738, 739, 770, 771, 772, 773, 774, 775, 776, 777, 778, 779, 790, 797, 799};

    public static String generateStandardPhoneNumber(boolean showGeneratedPhoneNumberInLog) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX_NUMBERS[RANDOM.nextInt(PREFIX_NUMBERS.length)]);
        for (int i = 0; i < 2; i++) {
            stringBuilder.append(TextEnum.SPACE_MARK.getText());
            for (int j = 0; j < 3; j++) {
                stringBuilder.append(RANDOM.nextInt(10));
            }
        }
        if (showGeneratedPhoneNumberInLog) {
            LOG.info(stringBuilder.toString());
        }
        return stringBuilder.toString();
    }

    public static String generateStandardPhoneNumber() {
        return generateStandardPhoneNumber(false);
    }

    private PhoneNumber() {
    }
}

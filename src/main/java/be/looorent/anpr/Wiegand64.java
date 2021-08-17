package be.looorent.anpr;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.toHexString;
import static java.lang.String.format;

/**
 * @author Lorent Lempereur
 */
public class Wiegand64 {

    private static final int UNKWOWN_CHARACTER = 0b111111;
    private static final int EMPTY = 0b000000;
    private static final int MAX_NUMBER_OF_CHARACTERS = 10;
    private static final long HEADER = ((long) 0b0110) << 60;
    private static final Map<Character, Integer> MAPPINGS = initializeMappingBetweenCharactersAndTheirBinaryRepresentation();
    private static final int NUMBER_OF_BITS_PER_CHARACTER = 6;

    /**
     * License plates can be converted in a hash in format Wiegand 64 bits where .
     * @param licensePlate must have length &le; 10 characters
     * @return a lowercase representation in the Wiegand 64 bits of the provided license plate or null (when the provided license plate is blank or null)
     */
    public static String transform(String licensePlate) {
        String sanitized = sanitize(licensePlate);
        if (sanitized == null || sanitized.isEmpty()) {
            return null;
        } else if (sanitized.length() > MAX_NUMBER_OF_CHARACTERS) {
            throw new IllegalArgumentException("Wiegand64 does not support license plate containing more than "+MAX_NUMBER_OF_CHARACTERS+" characters: "+sanitized);
        } else {
            long bits = on64Bits(sanitized);
            return toHexString(bits).toUpperCase();
        }
    }

    private static long on64Bits(String plate) {
        char[] characters = toArrayOfCharacters(plate);
        long bits = HEADER;
        for (int position = 0; position < MAX_NUMBER_OF_CHARACTERS; position++) {
            char character = characters[position];
            bits |= computeBinaryMask(position, character);
        }
        return bits;
    }

    private static char[] toArrayOfCharacters(String plate) {
        return format("%"+MAX_NUMBER_OF_CHARACTERS+"s", plate).toCharArray();
    }

    private static long computeBinaryMask(int position, char character) {
        Integer binary = findBinaryRepresentationOf(character);
        return computeBinaryMask(position, binary);
    }

    private static long computeBinaryMask(int position, long binaryRepresentation) {
        int shift = NUMBER_OF_BITS_PER_CHARACTER * (MAX_NUMBER_OF_CHARACTERS - position - 1);
        return binaryRepresentation << shift;
    }

    private static Integer findBinaryRepresentationOf(char character) {
        Integer binaryRepresentation = MAPPINGS.get(character);
        return binaryRepresentation == null ? UNKWOWN_CHARACTER : binaryRepresentation;
    }

    private static String sanitize(String licensePlate) {
        if (licensePlate != null) {
            return licensePlate
                    .trim()
                    .toUpperCase()
                    .replaceAll(" ","");
        } else {
            return null;
        }
    }

    private static Map<Character, Integer> initializeMappingBetweenCharactersAndTheirBinaryRepresentation() {
        char[] characters = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        final int zero = 0b010000;
        Map<Character, Integer> mapping = new HashMap<>(characters.length + 1);
        for (int index = 0; index < characters.length; index++) {
            mapping.put(characters[index], zero + index);
        }
        mapping.put(' ', EMPTY);
        return mapping;
    }
}

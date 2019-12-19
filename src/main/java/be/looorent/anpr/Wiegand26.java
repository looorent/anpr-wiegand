package be.looorent.anpr;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.Integer.bitCount;
import static java.lang.Integer.toHexString;
import static java.lang.String.format;

/**
 * @author Lorent Lempereur
 */
public class Wiegand26 {

    private static final int EVEN_PARITY_FIELD = 0b11111111111110000000000000;
    private static final int ODD_PARITY_FIELD  = 0b00000000000001111111111111;
    private static final int EVEN_PARITY_MASK  = 0b10000000000000000000000000;
    private static final int ODD_PARITY_MASK   = 0b00000000000000000000000001;
    private static final String SHA_ONE = "SHA1";
    private static final int MAX_NUMBER_OF_CHARACTERS = 10;

    /**
     * License plates can be converted in a hash in format Wiegand 26-bits (sha1-based).
     * @param licensePlate must have length &le; 10 characters
     * @return a lowercase representation in the Wiegand 26bits format (sha1-based) of the provided license plate or null (when the provided license plate is blank or null)
     * @throws WiegandException when there is no SHA1 algorithm available in <code>java.security.MessageDigest#getInstance(java.lang.String)</code>
     */
    public static String hash(String licensePlate) throws WiegandException {
        String sanitized = sanitize(licensePlate);
        if (sanitized == null || sanitized.isEmpty()) {
            return null;
        } else if (sanitized.length() > MAX_NUMBER_OF_CHARACTERS) {
            throw new IllegalArgumentException("Wiegang26 does not support license plate containing more than "+MAX_NUMBER_OF_CHARACTERS+" characters: "+sanitized);
        } else {
            byte[] shaOne = sha1(sanitized);
            byte[] lessSignificantBits = lessSignifiant24Bits(shaOne);
            int bits = moveTo26Bits(useNumericalRepresentation(lessSignificantBits));
            bits = addParityBits(bits);
            return toHexadecimal(bits);
        }
    }

    private static int addParityBits(int bits) {
        return setOddParityBit(setEvenParityBit(bits));
    }

    private static String sanitize(String licensePlate) {
        if (licensePlate != null) {
            return removeSpecialCharacters(licensePlate.trim());
        } else {
            return null;
        }
    }

    private static String removeSpecialCharacters(String licensePlate) {
        return licensePlate.toUpperCase().replaceAll("[^A-Z0-9]","");
    }

    private static byte[] lessSignifiant24Bits(byte[] binary) {
        byte[] lessSignificantBytes = new byte[4];
        lessSignificantBytes[0] = 0;
        lessSignificantBytes[1] = binary[binary.length-3];
        lessSignificantBytes[2] = binary[binary.length-2];
        lessSignificantBytes[3] = binary[binary.length-1];
        return lessSignificantBytes;
    }

    private static int useNumericalRepresentation(byte[] binary) {
        return ByteBuffer.wrap(binary).getInt();
    }

    private static int moveTo26Bits(int numericalRepresentation) {
        return numericalRepresentation << 1;
    }

    private static int setEvenParityBit(int numericalRepresentation) {
        int oneBits = bitCount(numericalRepresentation & EVEN_PARITY_FIELD);
        boolean mustAddParityBit = oneBits % 2 != 0;
        return mustAddParityBit ? numericalRepresentation | EVEN_PARITY_MASK : numericalRepresentation;
    }

    private static int setOddParityBit(int numericalRepresentation) {
        int oneBits = bitCount(numericalRepresentation & ODD_PARITY_FIELD);
        boolean mustAddParityBit = oneBits % 2 == 0;
        return mustAddParityBit ? numericalRepresentation | ODD_PARITY_MASK : numericalRepresentation;
    }

    private static byte[] sha1(String value) throws WiegandException {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_ONE);
            return digest.digest(value.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new WiegandException("An error occurred when finding SHA-1", e);
        }
    }

    private static String toHexadecimal(int value) {
        return format("%7s", toHexString(value).toUpperCase()).replace(" ", "0");
    }
}

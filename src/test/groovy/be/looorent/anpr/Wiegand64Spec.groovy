package be.looorent.anpr

import spock.lang.Specification

import static Wiegand64.transform
import static be.looorent.anpr.Wiegand26.hash

class Wiegand64Spec extends Specification {

    def "transform(null) returns null"() {
        when:
        def hash = transform(null)

        then:
        hash == null
    }

    def "transform(blank) returns null"() {
        when:
        def hash = transform(blank)

        then:
        hash == null

        where:
        blank | _
        ""    | _
        "   " | _
        " "   | _
    }

    def "transform(plate) returns the 64bits mapping of this plate"() {
        when:
        def hash = transform(plate)

        then:
        hash == expectedHash
        hash.length() == 16

        where:
        plate        | expectedHash
        "AZERTYUIOP" | "66B37ABB72BA2A29"
        "Z"          | "6000000000000033"
        "1WFV385"    | "6000011C1FBD3615"
        "1SDF534"    | "6000011B1D7D54D4"
        "ADF543"     | "600000069D7D5513"
        "DE56G"      | "600000001D7955A0"
        "FF23DDFDF5" | "67DF49375D7DD7D5"
        "AZERTYUIOI" | "66B37ABB72BA2A22"
        "1FRDEERE"   | "600045FADD79EADE"
        "2ZZD456"    | "6000012CF3754556"
        "1T1234"     | "600000046D4524D4"
    }


    def "transform(very long plate) throws an exception"() {
        when:
        hash(veryLongPlate)

        then:
        thrown(IllegalArgumentException)

        where:
        veryLongPlate       | _
        "azertyuiop0987"    | _
        "1234567899879825"  | _
        "nnnnnnnnnnnnnnnn"  | _
    }

    def "transform(short plate) returns the 64bits mapping of this plate"() {
        when:
        def hash = transform(plate)

        then:
        hash == expectedHash
        hash.length() == 16

        where:
        plate   | expectedHash
        "A"     | "600000000000001A"
        "EE"    | "600000000000079E"
        "012"   | "6000000000010452"
        "1T"    | "600000000000046D"
        "789"   | "6000000000017619"
        "42"    | "6000000000000512"
        "1337"  | "60000000004534D7"
        "CB"    | "600000000000071B"
        "ZIP"   | "60000000000338A9"
        "WAP"   | "60000000000306A9"
        "IU"    | "60000000000008AE"
        "PL"    | "6000000000000A65"

    }

    def "transform(plate with spaces and special characters) returns the 64 bits hash of this plate"() {
        when:
        def hash = transform(plate)

        then:
        hash == expectedHash
        hash.length() == 16

        where:
        plate         | expectedHash
        "HK 55 EVB"   | "600002191555EBDB"
        "VR46#T"      | "6000000BEB516FED"
        " VR46#T   "  | "6000000BEB516FED"
    }
}

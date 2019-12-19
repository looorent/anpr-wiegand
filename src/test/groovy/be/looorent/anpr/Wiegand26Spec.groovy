package be.looorent.anpr

import spock.lang.Specification

import static Wiegand26.hash

class Wiegand26Spec extends Specification {

    def "#hash(null) returns null"() {
        when:
        def hash = hash(null)

        then:
        hash == null
    }

    def "#hash(blank) returns null"() {
        when:
        def hash = hash(blank)

        then:
        hash == null

        where:
        blank | _
        ""    | _
        "   " | _
        " "   | _
    }

    def "#hash(very long plate) throws an exception"() {
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

    def "#hash(plate) returns the 26bit hash of this plate"() {
        when:
        def hash = hash(plate)

        then:
        hash == expectedHash
        hash.length() == 7

        where:
        plate        | expectedHash
        "1WFV385"    | "1A98B4B"
        "1SDF534"    | "2521053"
        "ADF543"     | "226C607"
        "DE56G"      | "31C234A"
        "FF23DDFDF5" | "090BF4D"
        "AZERTYUIOI" | "006D2EC"
        "1FRDEERE"   | "1A7622D"
        "2ZZD456"    | "2866DD8"
    }

    def "#hash(short plate) returns the 26bit hash of this plate"() {
        when:
        def hash = hash(plate)

        then:
        hash == expectedHash
        hash.length() == 7

        where:
        plate   | expectedHash
        "85"    | "0A6D5C5"
        "34"    | "3FE96B2"
        "3"     | "0DD0777"
        "DFDF5" | "1F628B4"
        "YUIOI" | "3A9BABB"
        "ERE"   | "08F4AEC"
        "56"    | "3EB746E"
    }

    def "#hash(plate with spaces and special characters) returns the 26bit hash of this plate"() {
        when:
        def hash = hash(plate)

        then:
        hash == expectedHash
        hash.length() == 7

        where:
        plate         | expectedHash
        "HK 55 EVB"   | "3019E2A"
        "HK-55-EVB"   | "3019E2A"
        "HK-55-evb"   | "3019E2A"
        " HK-55€evb " | "3019E2A"
        "1wfv385"     | "1A98B4B"
        "1S--DF534"   | "2521053"
        "A__DF543"    | "226C607"
        "de56g"       | "31C234A"
        "FF23DDFDF5"  | "090BF4D"
        "azer)TYUIOI" | "006D2EC"
        "1FR//DEERE"  | "1A7622D"
        "2ZZD4;;..56" | "2866DD8"
    }

}

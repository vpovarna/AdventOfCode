package io.aoc._2020.day02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RecordTest {

    @Test
    void validateValidPasswordPart1Test() {
        var passwordPolicy = new PasswordPolicy(1, 3, 'a', "abcde");
        Assertions.assertTrue(passwordPolicy.isPasswordValidPart1());
    }

    @Test
    void validateInvalidPasswordPart2Tet() {
        var passwordPolicy = new PasswordPolicy(1, 3, 'b', "cdefg");
        Assertions.assertFalse(passwordPolicy.isPasswordValidPart1());
    }

    @Test
    void validateValidPasswordPart2Test() {
        var passwordPolicy = new PasswordPolicy(1, 3, 'a', "abcde");
        Assertions.assertTrue(passwordPolicy.isPasswordValidPart2());
    }

    @Test
    void validateInvalidPasswordPart2Test() {
        var passwordPolicy = new PasswordPolicy(2, 9, 'c', "ccccccccc");
        Assertions.assertFalse(passwordPolicy.isPasswordValidPart2());
    }
}
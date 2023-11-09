package io.aoc._2020.day02;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProblemTest {

    private static String INPUT = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
            """;

    @Test
    void part1Test() {
        var problem = new Problem();
        var result = problem.part1(INPUT);
        Assertions.assertEquals(2, result);
    }

    @Test
    void part2Test() {
        var problem = new Problem();
        var result = problem.part2(INPUT);
        Assertions.assertEquals(1, result);
    }

    @Test
    void createPasswordPolicyTest() {
        var line = "1-3 a: abcde";
        var problem = new Problem();
        var expectedPasswordPolicy = new PasswordPolicy(1,3, 'a', "abcde");
        var passwordPolicy = problem.createPasswordPolicy(line);

        Assertions.assertEquals(passwordPolicy, expectedPasswordPolicy);
    }

    @Test
    void testInvalidInput() {
        var line = "test 1232131";
        var problem = new Problem();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> problem.createPasswordPolicy(line),
                "Invalid input line. Can't create password policy"
        );
    }
}
package hillel;

import org.testng.Assert;
import org.testng.annotations.Test;

import hillel.generators.FibGenerator;
import hillel.generators.Generator;
import hillel.generators.PrimeGenerator;

public class TestCases {

    @Test(description = "Check first 3 memebers produced by a prime generator")
    public static void test1() throws Exception {
        PrimeGenerator g = new PrimeGenerator();
        int[] primes = { 2, 3, 5 };

        Assert.assertTrue(firstNMembers(g, primes), "First three members produced by PrimeGenerator do not match the actual prime numbers");
    }

    @Test(description = "Check first 3 memebers produced by a fib generator")
    public static void test2() throws Exception {
        FibGenerator g = new FibGenerator();
        int[] fibs = { 0, 1, 1 };

        Assert.assertTrue(firstNMembers(g, fibs), "First three members produced by FibGenerator do not match the actual fib numbers");
    }

    public static boolean firstNMembers(Generator g, int[] expectedValues) {
        for (int v : expectedValues) {
            if (g.next() != v) {
                return false;
            }
        }

        return true;
    }

}
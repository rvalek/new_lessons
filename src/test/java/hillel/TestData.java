package hillel;

import hillel.generators.FibGenerator;
import hillel.generators.PrimeGenerator;

public interface TestData {
    public static Object[][] generators = new Object[][] {
        { new PrimeGenerator() },
        { new FibGenerator() },
    };

    static int[] primes = {2, 3, 5};
    static int[] fibs = {0, 1, 1};
    static int[] moreFibs = {0, 1, 1, 2, 3, 5, 8, 13, 21};

    public static Object[][] generatorsWithNumbers = new Object[][] {
        { new PrimeGenerator(),  primes },
        { new FibGenerator(), fibs },
        { new FibGenerator(), moreFibs },
    };

}
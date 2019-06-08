package generators;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCases {
    // static Generator g;

    // @BeforeMethod
    // public static void instantiateGenerator2() {
    //     g = new PrimeGenerator();
    // }

    @DataProvider
    public static Object[][] generatorsAndNumbers() {
        return TestData.generatorsWithNumbers;
    }

    @DataProvider
    public static Object[][] generators() {
        return TestData.generators;
    }

    @Test(dataProvider = "generatorsAndNumbers", description = "Check first 3 memebers produced by a prime generator")
    public static void test1(Generator gen, int[] expectedMembers) {
        System.out.println("t1");
        Assert.assertTrue(Utils.firstNMembers(gen, expectedMembers),
                "First three members produced by PrimeGenerator do not match the actual prime numbers");
    }

    @Test(dataProvider = "generators", description = "Test for the reset() method of a generator")
    public static void test3(Generator gen) {
        System.out.println("t2");
        int firstTime = gen.next();
        gen.reset();
        int secondTime = gen.next();

        Assert.assertEquals(firstTime, secondTime, "Values generated before and after reset do not match");
    }
}
package hillel;

import hillel.generators.Generator;

public class Utils {
    public static boolean firstNMembers(Generator g, int[] expectedValues) {
        for (int v : expectedValues) {
            if (g.next() != v) {
                return false;
            }
        }

        return true;
    }
}
// package generators;

// public class FibGenerator implements IGenerator {
//     public FibGenerator() { }

//     public int next() {
//         return 0;
//     }
// }

package generators;

public class FibGenerator implements IGenerator {
    private int first = 1;
    private int second = 1;
    private int current;

    public FibGenerator() {
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int next() {
        current = first + second;
        first = second;
        second = current;
        return current;
    }
}
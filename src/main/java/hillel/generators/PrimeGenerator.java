package generators;

public class PrimeGenerator implements IGenerator {
    private int baseValue = 2;
    private int nextStartingPoint = baseValue;

    public PrimeGenerator() {

    }

    public int getBaseValue() {
        return baseValue;
    }

    public int next() {
        for (int i = nextStartingPoint;; i += 1) {
            if (isPrime(i)) {
                nextStartingPoint = i + 1;
                return i;
            }
        }
    }

    private boolean isPrime(int n) {
        for (int i = 2; i < n / 2; i += 1) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

}

// class Client {
//     public static void main(String[] args) {
        // PrimeNumbers o = new PrimeNumbers();
//         o.nextPrime();
//         // System.out.println

//     }

//     public Number timesTwo(Number n) {

//     }
// }
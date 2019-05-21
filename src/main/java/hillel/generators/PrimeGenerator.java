package generators;

public class PrimeGenerator extends Generator {
    public PrimeGenerator() {
        init(2);
    }

    public int getBaseValue() {
        return baseValue;
    }

    public int next() {
        // if this member of our sequence was already generated
        // return it from array
        // else
        // generate next value from current one
        for (int i = currentValue + 1;; i += 1) {
            if (isPrime(i)) {
                currentValue = i;
                return i;
                // also add it to array
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
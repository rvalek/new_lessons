package generators;

public class PrimeGenerator extends Generator {
    public PrimeGenerator() {
        super(2);
    }

    protected int generateNextMember() {
        for (int i = getCurrentMember() + 1;; i += 1) {
            if (isPrime(i)) {
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
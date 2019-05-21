package generators;

import java.util.ArrayList;

public abstract class Generator {
    // protected int baseValue;
    // protected int currentValue;
    protected int counter;
    protected ArrayList<Integer> generatedValues = new ArrayList<>();

    protected void init(int _baseValue) {
        // baseValue = _baseValue;
        generatedValues.add(_baseValue);
        // currentValue = baseValue;
        counter = 0;
    }

    public abstract int next();

    public void reset() {
        // currentValue = baseValue;
        counter = 0;
    };
}
package hillel.generators;

import java.util.ArrayList;

public abstract class Generator {
    protected int currentMemberIndex = 0;
    protected ArrayList<Integer> generatedValues = new ArrayList<>();

    protected Generator(int _baseValue) {
        generatedValues.add(_baseValue);
    }

    public int getBaseMember() {
        return generatedValues.get(0);
    }

    public int getCurrentMember() {
        return generatedValues.get(currentMemberIndex - 1);
    }

    protected boolean generatedAlready() {
        return currentMemberIndex <= generatedValues.size() - 1;
    }

    public int next() {
        if (generatedAlready()) {
            currentMemberIndex += 1;
            return getCurrentMember();
        } else {
            int nextValue = generateNextMember();
            generatedValues.add(nextValue);
            currentMemberIndex += 1;
            return nextValue;
        }

    }

    public void reset() {
        currentMemberIndex = 0;
    }

    protected abstract int generateNextMember();
}
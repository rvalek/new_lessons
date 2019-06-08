package generators;

public class FibGenerator extends Generator {
    public FibGenerator() {
        super(0);
    }

    @Override
    protected int generateNextMember() {
        if (currentMemberIndex == 1) {
            return 1;
        } else {
            return generatedValues.get(currentMemberIndex - 1) + generatedValues.get(currentMemberIndex - 2);
        }
    }
}
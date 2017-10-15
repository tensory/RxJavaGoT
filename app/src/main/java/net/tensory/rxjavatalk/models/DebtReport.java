package net.tensory.rxjavatalk.models;

public class DebtReport {
    private Value<Double> v1;
    private Value<Double> v2;
    private Value<Double> v3;
    private Value<Double> v4;

    public DebtReport(Value<Double> lannisterDebt,
                      Value<Double> targaryenDebt,
                      Value<Double> starkDebt,
                      Value<Double> nightKingDebt) {
        v1 = lannisterDebt;
        v2 = targaryenDebt;
        v3 = starkDebt;
        v4 = nightKingDebt;
    }

    public Value<Double> getLannisterDebt() { return v1; }
    public Value<Double> getTargaryenDebt() { return v2; }
    public Value<Double> getStarkDebt() { return v3; }
    public Value<Double> getNightKingDebt() { return v4; }
}

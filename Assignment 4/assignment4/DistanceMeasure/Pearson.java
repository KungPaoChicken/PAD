package assignment4.DistanceMeasure;

import assignment4.Unit;

public class Pearson implements DistanceMeasure {
    public Pearson() {

    }

    @Override
    public double calculateDistance(Unit unit1, Unit unit2) {
        double pearsonCorrelation = 0;
        double sumUnit1 = 0, meanUnit1 = 0, sumUnit2 = 0, meanUnit2 = 0;
        double totalSqrDiffUnit1 = 0, stdDevUnit1 = 0, totalSqrDiffUnit2 = 0, stdDevUnit2 = 0;
        for (int i = 0; i < unit1.length(); i++) {
            sumUnit1 += unit1.elementAt(i);
            sumUnit2 += unit2.elementAt(i);
        }
        meanUnit1 = sumUnit1 / unit1.length();     //Questionable readability - let's see
        meanUnit2 = sumUnit2 / unit2.length();

        for (int i = 0; i < unit1.length(); i++) {
            totalSqrDiffUnit1 += Math.pow(unit1.elementAt(i) - meanUnit1, 2);
            totalSqrDiffUnit2 += Math.pow(unit2.elementAt(i) - meanUnit2, 2);
        }
        stdDevUnit1 = Math.sqrt(totalSqrDiffUnit1 / (unit1.length() - 1));
        stdDevUnit2 = Math.sqrt(totalSqrDiffUnit2 / (unit2.length() - 1));

        for (int i = 0; i < unit1.length(); i++) {
            pearsonCorrelation += (unit1.elementAt(i) - meanUnit1) / stdDevUnit1 * (unit2.elementAt(i) - meanUnit2) / stdDevUnit2;
        }
        pearsonCorrelation = pearsonCorrelation / (unit1.length() - 1);
        return 1 - pearsonCorrelation;
    }
}

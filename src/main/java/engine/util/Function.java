package engine.util;

public class Function {
    private double[] coefficients;

    public Function(double... coefficients) {
        this.coefficients = coefficients;
    }

    public double compute(int x) {
        double result = 0.0;
        int degree = coefficients.length - 1;

        for (int i = 0; i <= degree; i++) {
            result += coefficients[i] * Math.pow(x, degree - i);
        }

        return result;
    }
}

package org.itstep.Task3;

public class Fraction {
    private int numerator;
    private int denominator;

    public Fraction() {
    }

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public Fraction addition(Fraction fraction) {
        this.numerator = this.numerator + fraction.getNumerator();
        return reduce(this);
    }

    public Fraction subtraction(Fraction fraction) {
        this.numerator = this.numerator - fraction.getNumerator();
        return reduce(this);
    }

    public Fraction multiplication(Fraction fraction) {
        this.numerator = this.numerator * fraction.getNumerator();
        this.denominator = this.denominator * fraction.getDenominator();
        return reduce(this);
    }

    public Fraction division(Fraction fraction) {
        this.numerator = this.numerator * fraction.getDenominator();
        this.denominator = this.denominator * fraction.getNumerator();
        return reduce(this);
    }

    private Fraction reduce(Fraction fraction) {

        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        int limit = Math.min(numerator, denominator);

        for (int i = 2; i <= limit; i++) {
            if (numerator % i == 0 && denominator % i == 0) {
                while (denominator >= i){
                    numerator /= i;
                    denominator /= i;
                }
            }
        }

        fraction.setNumerator(numerator);
        fraction.setDenominator(denominator);
        return fraction;
    }
}

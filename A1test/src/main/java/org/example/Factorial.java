package org.example;

import java.math.BigInteger;

public class Factorial {
    public static double getFactorial(int f) {
            double u = 1.0;
            for (int i = 2; i <= f; i++) {
                u = 1.0 + u / i;
            }
            return u;
    }
}

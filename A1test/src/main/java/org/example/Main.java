package org.example;
import org.example.Factorial;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите число, которое нужно рассчитать по формуле: ");
        int n = 0;
        if(sc.hasNextInt()){
            n = sc.nextInt();
        }
        System.out.println("Результат вычисления: ");
        System.out.println(Factorial.getFactorial(n));
        System.out.println("Выражение стремится не к 0 или бесконечности, а к 1");
    }
}
import functions.*;
import functions.basic.*;
;

import java.io.*;


public class Main {
    public static void main(String[] args)
    {
        Function funSin = new Sin();
        Cos funCos = new Cos();

        for(double i = 0; i <= (2 * Math.PI); i += 0.1){
            System.out.print("sin: " + funSin.getFunctionValue(i) + " ");
            System.out.print("cos: " + funCos.getFunctionValue(i));
            System.out.println();
        }
        System.out.println();

        Function tabSin = TabulatedFunctions.tabulate(funSin, 0, 2 * Math.PI, 10);
        Function tabCos = TabulatedFunctions.tabulate(funCos, 0, 2 * Math.PI, 10);

        for(double i = 0; i <= (2 * Math.PI); i += 0.1){
            System.out.print("sin: " + tabSin.getFunctionValue(i) + " ");
            System.out.print("cos: " + tabCos.getFunctionValue(i));
            System.out.println();
        }
        System.out.println();


     //123

    }
}



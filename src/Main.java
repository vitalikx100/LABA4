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

        Function tabSin = null;
        try {
            tabSin = TabulatedFunctions.tabulate(funSin, 0, 2 * Math.PI, 10);
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }
        Function tabCos = null;
        try {
            tabCos = TabulatedFunctions.tabulate(funCos, 0, 2 * Math.PI, 10);
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }

        for(double i = 0; i <= (2 * Math.PI); i += 0.1){
            System.out.print("sin: " + tabSin.getFunctionValue(i) + " ");
            System.out.print("cos: " + tabCos.getFunctionValue(i));
            System.out.println();
        }
        System.out.println();

        Function sin2 = Functions.Power(tabSin, 2);
        Function cos2 = Functions.Power(tabCos, 2);

        Function sumFun = Functions.Sum(sin2, cos2);
        for(double i = 0; i <= (2 * Math.PI); i += 0.1){
            System.out.print("sin2 + cos2: " + sumFun.getFunctionValue(i));
            System.out.println();
        }



    }
}



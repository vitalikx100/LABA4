import functions.*;
import functions.basic.*;

import java.io.*;

public class Main{
    public static void main(String[] args) {
        Function sin = new Sin();
        Function cos = new Cos();

        for (double i = 0; i <= 2 * Math.PI; i += 0.1) {
            System.out.println("sin = " + sin.getFunctionValue(i) + " cos = " + cos.getFunctionValue(i));
        }
        System.out.println();

        TabulatedFunction tabSin = null;
        try {
            tabSin = TabulatedFunctions.tabulate(sin, 0, 2 * Math.PI, 10);
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }
        TabulatedFunction tabCos = null;
        try {
            tabCos = TabulatedFunctions.tabulate(cos, 0, 2 * Math.PI, 10);
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }

        for (double i = 0; i <= 2 * Math.PI; i += 0.1) {
            System.out.println("TabSin = " + tabSin.getFunctionValue(i) + " TabCos = " + tabCos.getFunctionValue(i));
        }
        System.out.println();

        Function sumSqrSinAndCos = Functions.Sum(Functions.Power(tabSin, 2), Functions.Power(tabCos, 2));
        for(double i = 0; i <= 2 * Math.PI; i += 0.1) {
            System.out.println(sumSqrSinAndCos.getFunctionValue(i));
        }
        System.out.println();

        Function exp = new Exp();
        TabulatedFunction tabExp = null;
        try {
            tabExp = TabulatedFunctions.tabulate(exp, 0, 10, 11);
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }
        try {
            FileWriter out = new FileWriter("output.txt");
            TabulatedFunctions.writeTabulatedFunction(tabExp, out);
            FileReader in = new FileReader("output.txt");
            TabulatedFunction expw = TabulatedFunctions.readTabulatedFunction(in);
            for(int i = 0; i <= 10; ++i) {
                System.out.println(tabExp.getFunctionValue(i) + " " + expw.getFunctionValue(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();

        Function log = new Log(2);
        TabulatedFunction tabLog = null;
        try {
            tabLog = TabulatedFunctions.tabulate(log, 0, 10 , 11);
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }
        try {
            FileOutputStream out2 = new FileOutputStream("output2.txt");
            TabulatedFunctions.outputTabulatedFunction(tabLog, out2);
            FileInputStream in2 = new FileInputStream("output2.txt");
            TabulatedFunction logw = TabulatedFunctions.inputTabulatedFunction(in2);
            for(int i = 0; i <= 10; ++i) {
                System.out.println(tabLog.getFunctionValue(i) + " " + logw.getFunctionValue(i));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println();

        try {
            FileOutputStream out3 = new FileOutputStream("output3.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out3);
            objectOutputStream.writeObject(tabLog);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TabulatedFunction logs = null;
        try {
            FileInputStream in3 = new FileInputStream("output3.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(in3);
            logs = (TabulatedFunction) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for(double i = 0; i <= 10; i++){
            System.out.println(tabLog.getFunctionValue(i) + " " + logs.getFunctionValue(i));
        }

    }
}
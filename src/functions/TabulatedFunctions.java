package functions;

import java.io.*;

public class TabulatedFunctions {

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException{
        if (pointsCount <2 || leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) throw new IllegalArgumentException();
        double size = (rightX-leftX)/(pointsCount-1);
        FunctionPoint[] MassOfValues = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; ++i) {
            double x = leftX + size * i;
            MassOfValues[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return new ArrayTabulatedFunction(MassOfValues, pointsCount);
    }


    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out){
        try (DataOutputStream stream = new DataOutputStream(out)) {
            int pointsCount = function.getNumberOfPoints();
            stream.writeInt(pointsCount);
            for (int i = 0; i < pointsCount; ++i) {
                stream.writeDouble(function.getPointX(i));
                stream.writeDouble(function.getPointY(i));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in){
        try (DataInputStream stream = new DataInputStream(in)) {
            int pointsCount = stream.readInt();
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; ++i) {
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }
            return new ArrayTabulatedFunction(points, pointsCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out){
        try (BufferedWriter stream = new BufferedWriter(out)) {
            int pointsCount = function.getNumberOfPoints();
            stream.write(String.valueOf(pointsCount) + " ");
            for (int i = 0; i < pointsCount; i++) {
                stream.write(function.getPointX(i) + " " + function.getPointY(i) + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in){
        try {
            StreamTokenizer stream = new StreamTokenizer(in);
            stream.nextToken();
            int pointsCount = (int) stream.nval;
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; stream.nextToken() != StreamTokenizer.TT_EOF; i++) {
                double x = stream.nval;
                stream.nextToken();
                double y = stream.nval;
                points[i] = new FunctionPoint(x, y);
            }
            return new ArrayTabulatedFunction(points, pointsCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }




}

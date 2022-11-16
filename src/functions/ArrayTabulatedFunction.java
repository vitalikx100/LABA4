package functions;

public class ArrayTabulatedFunction implements TabulatedFunction, Function{
    private FunctionPoint[] MassOfValues;
    private int NumberOfPoints;

    public ArrayTabulatedFunction(FunctionPoint[] points, int NumberOfPoints) {
        if (points.length < 2) throw new IllegalArgumentException();

        this.MassOfValues = new FunctionPoint[NumberOfPoints];
        for (int i = 0; i < NumberOfPoints; ++i) {
           MassOfValues[i]=points[i];
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount)  throws InappropriateFunctionPointException, IllegalArgumentException {
        if((leftX>=rightX) || (pointsCount<2)) throw new IllegalArgumentException();

        double size = ((rightX - leftX) / (pointsCount));
        this.MassOfValues = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            this.MassOfValues[i] = new FunctionPoint(leftX + i * size, 0);
        }
        NumberOfPoints = pointsCount;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException, IllegalArgumentException {
        if((leftX>=rightX) || (values.length<2)) throw new IllegalArgumentException();

        this.MassOfValues = new FunctionPoint[values.length];
        double size = ((rightX - leftX) / (values.length ));
        for (int i = 0; i < values.length; i++) {
            this.MassOfValues[i] = new FunctionPoint(leftX + i * size, values[i]);
        }
        NumberOfPoints = values.length;
    }

    public double getLeftDomainBorder() {
        return this.MassOfValues[0].getX();
    }

    public double getRightDomainBorder() {
        return this.MassOfValues[NumberOfPoints - 1].getX();
    }

    public double getFunctionValue(double x) {
        if ((this.MassOfValues[0].getX() > x) || (this.MassOfValues[MassOfValues.length - 1].getX() < x)) {
            return Double.NaN;
        }

        int i = 0;
        while (this.MassOfValues[i].getX() < x) i++;

        double x1, x2, y1, y2;
        x1 = this.MassOfValues[i - 1].getX();
        x2 = this.MassOfValues[i].getX();
        y1 = this.MassOfValues[i - 1].getY();
        y2 = this.MassOfValues[i].getY();
        double k = (y2 - y1) / (x2 - x1);
        double b = y1 - k * x1;
        return k * x + b;
    }

    public int getNumberOfPoints() {
        return NumberOfPoints;
    }
    public void setNumberOfPoints(int count){

    }

    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= NumberOfPoints) throw new FunctionPointIndexOutOfBoundsException();
        return MassOfValues[index];
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if ((index < 0) || (index >= NumberOfPoints) || (point.getX() > this.getRightDomainBorder() || point.getX() < this.getLeftDomainBorder()))
            throw new FunctionPointIndexOutOfBoundsException();

        if (((index == 0) ? false : (this.MassOfValues[index - 1].getX() >= point.getX())) || ((index == (NumberOfPoints - 1)) ? false : (this.MassOfValues[index + 1].getX() <= point.getX())))
            throw new InappropriateFunctionPointException();

        this.MassOfValues[index] = point;
    }

    public double getPointX(int index) {
        if (index < 0 || index >= NumberOfPoints) throw new FunctionPointIndexOutOfBoundsException();
        return MassOfValues[index].getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if ((index < 0) || (index >= NumberOfPoints) || (x > this.getRightDomainBorder() || x < this.getLeftDomainBorder()))
            throw new FunctionPointIndexOutOfBoundsException();

        if (((index == 0) ? false : (this.MassOfValues[index - 1].getX() >= x)) || ((index == (NumberOfPoints - 1)) ? false : (this.MassOfValues[index + 1].getX() <= x)))
            throw new InappropriateFunctionPointException();

        this.MassOfValues[index].setX(x);
    }


    public double getPointY(int index) {
        if (index < 0 || index >= NumberOfPoints) throw new FunctionPointIndexOutOfBoundsException();
        return MassOfValues[index].getY();
    }

    public void setPointY(int index, double y) {
        if (index < 0 || index >= NumberOfPoints) throw new FunctionPointIndexOutOfBoundsException();
        this.MassOfValues[index].setY(y);
    }

    public void deletePoint(int index) {
        if ((index < 0) || (index >= NumberOfPoints)) throw new FunctionPointIndexOutOfBoundsException();
        if (NumberOfPoints <= 3) throw new  IllegalStateException();
        System.arraycopy(this.MassOfValues, index + 1, this.MassOfValues, index, MassOfValues.length - index - 1);
        NumberOfPoints--;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (NumberOfPoints<3) throw new InappropriateFunctionPointException();
        else {
            int index = 0;
            if (point.getX() > this.MassOfValues[NumberOfPoints - 1].getX()) {
                index = NumberOfPoints;
            } else {
                while (MassOfValues[index].getX() < point.getX()) index++;
            }

            if (index < NumberOfPoints) {
                if (NumberOfPoints <= this.MassOfValues.length) {
                    FunctionPoint[] tmp = new FunctionPoint[getNumberOfPoints()];
                    System.arraycopy(MassOfValues, 0, tmp, 0, tmp.length);
                    this.MassOfValues = new FunctionPoint[getNumberOfPoints() + 1];
                    NumberOfPoints++;
                    System.arraycopy(tmp, 0, MassOfValues, 0, index);
                    this.MassOfValues[index] = point;
                    System.arraycopy(tmp, index, MassOfValues, index + 1, tmp.length - index);
                } else {
                    System.arraycopy(MassOfValues, index, MassOfValues, index + 1, getNumberOfPoints() - index);
                    this.MassOfValues[index] = point;
                }

            } else {
                FunctionPoint[] tmp = new FunctionPoint[getNumberOfPoints()];
                System.arraycopy(MassOfValues, 0, tmp, 0, tmp.length);
                this.MassOfValues = new FunctionPoint[getNumberOfPoints() + 1];
                NumberOfPoints++;
                System.arraycopy(tmp, 0, MassOfValues, 0, tmp.length);
                this.MassOfValues[NumberOfPoints - 1] = point;
            }
        }
    }

    public void print() {
        for (int i = 0; i < NumberOfPoints; i++) {
            System.out.println("(" + MassOfValues[i].getX() + ";" + MassOfValues[i].getY() + ")");
        }
    }

}





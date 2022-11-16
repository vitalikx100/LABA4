package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction, Function {

    private static class FunctionNode {
        private FunctionPoint point;
        private FunctionNode prev,next;
    }
    private FunctionNode head;
    private int NumberOfPoints = 0;

    public LinkedListTabulatedFunction(FunctionPoint[] points, int NumberOfPoints) throws InappropriateFunctionPointException{
        if (points.length < 2) throw new IllegalArgumentException();

        head = new FunctionNode();
        head.next=head;
        head.prev=head;
        this.NumberOfPoints=NumberOfPoints;
        for(int i=0; i<NumberOfPoints; i++){
            addPoint(new FunctionPoint(points[i].getX(), points[i].getY()));
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException, InappropriateFunctionPointException {
        if ((leftX >= rightX) || (pointsCount<2)) throw new IllegalArgumentException();

        head = new FunctionNode();
        head.prev = head;
        head.next = head;

        double size = (rightX - leftX) / (pointsCount - 1);
        for (int index = 0; index < pointsCount; ++index)
            addPoint(new FunctionPoint(leftX + size * index, 0));
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException, InappropriateFunctionPointException {

        if ((leftX >= rightX) || (values.length<2)) throw new IllegalArgumentException();


        head = new FunctionNode();
        head.prev = head;
        head.next = head;

        double size = (rightX - leftX) / (values.length );
        for (int index = 0; index < values.length; ++index)
            addPoint(new FunctionPoint(leftX + size * index, values[index]));

    }

    public FunctionNode getNodeByIndex(int index) {

        FunctionNode ActualElement = head;
        while (index-- >= 0) ActualElement = ActualElement.next;
        return ActualElement;
    }

    public FunctionNode addNodeToTail() {

        FunctionNode node = new FunctionNode();
        node.next = head;
        node.prev = head.prev;

        head.prev.next = node;
        head.prev = node;

        setNumberOfPoints(getNumberOfPoints() + 1);
        return node;

    }

    public FunctionNode addNodeByIndex(int index) {

        FunctionNode node = new FunctionNode();
        FunctionNode ActualElement = getNodeByIndex(index);

        node.next = ActualElement;
        node.prev = ActualElement.prev;

        ActualElement.prev.next = node;
        ActualElement.prev = node;

        setNumberOfPoints(getNumberOfPoints() + 1);
        return node;

    }

    public FunctionNode insertAfterNode(FunctionNode ActualElement) {

        FunctionNode node = new FunctionNode();

        node.prev = ActualElement;
        node.next = ActualElement.next;

        ActualElement.next.prev = node;
        ActualElement.next = node;

        setNumberOfPoints(getNumberOfPoints() + 1);
        return node;

    }

    public FunctionNode deleteNodeByIndex(int index) {

        FunctionNode node = getNodeByIndex(index);

        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.prev = null;
        node.next = null;

        setNumberOfPoints(getNumberOfPoints() - 1);
        return node;

    }

    public double getLeftDomainBorder() {
        return head.next.point.getX();
    }

    public double getRightDomainBorder() {
        return head.prev.point.getX();
    }

    public double getFunctionValue(double x) {

        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;

        FunctionNode node = head;
        while ((node = node.next) != head) {

            if (node.point.getX() == x)
                return node.point.getY();

            if (x >= node.point.getX() && x <= node.next.point.getX()) {

                FunctionPoint left = node.point;
                FunctionPoint right = node.next.point;

                double k = (right.getY() - left.getY()) / (right.getX() - left.getX());
                double b = right.getY() - k * right.getX();
                return k * x + b;

            }

        }
        return Double.NaN;
    }

    public int getNumberOfPoints() {
        return NumberOfPoints;
    }

    public void setNumberOfPoints(int count) {
        NumberOfPoints = count;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {

        if (index < 0 && index > getNumberOfPoints())  throw new FunctionPointIndexOutOfBoundsException();

        return getNodeByIndex(index).point;

    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {

        if (index < 0 && index > getNumberOfPoints())  throw new FunctionPointIndexOutOfBoundsException();

        FunctionNode node = getNodeByIndex(index);
        if (!(point.getX() >( node.prev == head ? getLeftDomainBorder() : node.prev.point.getX()) && point.getX() < (node.next == head ? getLeftDomainBorder() : node.next.point.getX())))
            throw new InappropriateFunctionPointException();

        node.point = point;

    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {

        if (index < 0 && index > getNumberOfPoints())  throw new FunctionPointIndexOutOfBoundsException();

        return getPoint(index).getX();

    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {

        if (index < 0 && index > getNumberOfPoints())  throw new FunctionPointIndexOutOfBoundsException();

        FunctionNode node = getNodeByIndex(index);
        if (!(x >( node.prev == head ? getLeftDomainBorder() : node.prev.point.getX()) && x < (node.next == head ? getLeftDomainBorder() : node.next.point.getX())))
            throw new InappropriateFunctionPointException();

        node.point.setX(x);
    }


    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {

        if (index < 0 && index > getNumberOfPoints())  throw new FunctionPointIndexOutOfBoundsException();

        return getPoint(index).getY();

    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {

        if (index < 0 && index > getNumberOfPoints())  throw new FunctionPointIndexOutOfBoundsException();

        getPoint(index).setY(y);

    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {

        FunctionNode position = head;
        FunctionNode current = head;

        while ((current = current.prev) != head) {

            if (point.getX() == current.point.getX())
                throw new InappropriateFunctionPointException();

            if (point.getX() > current.point.getX()) {

                position = current;
                break;

            }
        }

        insertAfterNode(position).point = point;
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException {

        if (index < 0 && index > getNumberOfPoints())  throw new FunctionPointIndexOutOfBoundsException();

        if (getNumberOfPoints() < 3)
            throw new IllegalStateException();

        setNumberOfPoints(getNumberOfPoints() - 1);
        deleteNodeByIndex(index);

    }

    public void print() {

        FunctionNode node = head;
        System.out.println();

        while ((node = node.next) != head)
            System.out.println("(" + node.point.getX() + " " + node.point.getY()+ ")");

    }

}



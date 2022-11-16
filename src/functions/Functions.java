package functions;

import functions.meta.*;

public class Functions {

    public static Function Sum(Function Func1, Function Func2) {
        return new Sum(Func1, Func2);
    }

    public static Function Mult(Function Func1, Function Func2) {
        return new Mult(Func1, Func2);
    }

    public static Function Power(Function Func, double power) {
        return new Power(Func, power);
    }

    public static Function Scale(Function Func, double ScaleX, double ScaleY) {
        return new Scale(Func, ScaleX, ScaleY);
    }

    public static Function Shift(Function Func, double ShiftX, double ShiftY) {
        return new Shift(Func, ShiftX, ShiftY);
    }

    public static Function Composition(Function Func1, Function Func2) {
        return new Composition(Func1, Func2);
    }
}

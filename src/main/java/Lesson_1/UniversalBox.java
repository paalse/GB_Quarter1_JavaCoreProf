package Lesson_1;

public class UniversalBox<T> {
    private T[] obj;

    public UniversalBox(T[] obj) {
        this.obj = obj;
    }

    public T[] getObj() {
        return obj;
    }

    public void setObj(T[] obj) {
        this.obj = obj;
    }

    public void SwapElementOfArray(int indexOfElement1, int indexOfElement2) {
        if ((indexOfElement1 < obj.length) && (indexOfElement2 < obj.length)) {
            T tmp = null;
            tmp = this.obj[indexOfElement1];
            this.obj[indexOfElement1] = this.obj[indexOfElement2];
            this.obj[indexOfElement2] = tmp;
        }
    }

    public void info() {
        for (T elem : obj) {
            System.out.print(elem + ", ");
        }
        System.out.println();
    }
}
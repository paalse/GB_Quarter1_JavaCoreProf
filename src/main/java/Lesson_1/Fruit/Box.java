package Lesson_1.Fruit;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    ArrayList<T> boxList;

    public Box() {
        this.boxList = new ArrayList<T>();
    }

    /**
     * Получение веса коробки
     * @return
     */
    public float getWeight() {
        if (boxList.size() == 0) return 0.0f;
        return boxList.get(0).getWeight() * boxList.size();
    }

    /**
     * Добавление фрукта в коробку
     * @param fruit
     */
    public void addFruit(T fruit) {
        boxList.add(fruit);
    }

    /**
     * Сравнение веса текущий и другой коробки с фруктами
     * @param boxFruit_1 - другая коробка с фруктами
     * @return
     */
    public boolean compare(Box<?> boxFruit_1) {
        if (this.getWeight() == boxFruit_1.getWeight()) return true;
        return false;
    }

    /**
     * Передача фруктов из текущей коробки в другую
     * @param boxFruit_1 - коробка в которую переложить фрукты
     */
    public void transmit(Box<T> boxFruit_1) {
        boxFruit_1.boxList.addAll(this.boxList);
        this.boxList.clear();
    }
}

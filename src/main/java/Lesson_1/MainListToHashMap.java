package Lesson_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 4 Необходимо из плоского списка (это List<SomeClass>)
 * SomeClass {
 * int id;
 * String name;
 * }
 * <p>
 * id name
 * 1 Test1
 * 2 Test1
 * 3 Test1
 * 4 Test2
 * 5 Test2
 * 6 Test3
 * 7 Test3
 * 8 Test4
 * <p>
 * переложить данные в HashMap
 * <p>
 * должно получиться
 * <p>
 * Test1 {1,2,3}
 * Test2 {4,5}
 * Test3 {6,7}
 * Test4 {8}
 */
public class MainListToHashMap {

    public static void main(String[] args) {
        List<SomeClass> origin = new ArrayList<SomeClass>();
        origin.add(new SomeClass(1, "Test1"));
        origin.add(new SomeClass(2, "Test1"));
        origin.add(new SomeClass(3, "Test1"));
        origin.add(new SomeClass(4, "Test2"));
        origin.add(new SomeClass(5, "Test2"));
        origin.add(new SomeClass(6, "Test3"));
        origin.add(new SomeClass(7, "Test3"));
        origin.add(new SomeClass(8, "Test4"));

        HashMap<String, ArrayList<Integer>> result = new HashMap<String, ArrayList<Integer>>();
        origin.forEach(i -> result.computeIfAbsent(i.getName(), k -> new ArrayList<Integer>()).add(i.getId()));


        /*
        for (SomeClass element : origin) {
            if (result.containsKey(element.getName())) {
                result.get(element.getName()).add(element.getId());
            } else {
                result.put(element.getName(), new ArrayList(Arrays.asList(element.getId())));
            }
        }
 */

    }
}
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        LoopList<Integer> test = new LoopList<>();

//        File file = new File(".", "pop");
//        try {
//            test.load(file);
//        }  catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Iterator rIterator = test.reverseIterator();
//
//        test.forEach(a -> {
//            if(a < 0)
//                System.out.println(-a);
//            else
//                System.out.println(a);
//        });
        //for(int data : test)
        //    System.out.println(data);
//        System.out.println();
//        while(rIterator.hasNext())
//            System.out.println(rIterator.next());

        test.pushBack(1100);
        test.pushFront(100);
        test.pushFront(300);
        test.pushBack(200);
        test.pushFront(300);
        test.pushBack(1100);
        test.pushFront(90);
        test.pushBack(5);
        test.insert(-74553, 6);
        test.insert(-7453213, 9);
        test.insert(74553, 6);
        test.insert(745532, 3);

        test.insert(999, 0);
        test.insert(32154, 3);
        test.insert(74554, 7);
        test.insert(11111, 9);

        //test.remove(3);
        //test.remove(7);
        //test.remove(0);

        test.set(-12313, 2);

        //System.out.println(test.get(2));

        System.out.println(test.isEmpty());

        System.out.println(test.getSize());

        System.out.println();
        for(int data: test)
            System.out.println(data);

        System.out.println();
        test.sort();

        for(int data: test)
            System.out.println(data);
//        Iterator<Integer> reverseIterator = test.reverseIterator();
//        while(reverseIterator.hasNext())
//            System.out.println(reverseIterator.next());

//        File file = new File(".", "pop");
//        try {
//            test.save(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

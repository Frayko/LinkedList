public class Main {
    public static void main(String[] args) {
        LoopList<Integer> test = new LoopList<>();

        test.pushFront(100);
        test.pushBack(200);
        test.pushFront(300);
        test.pushBack(1100);
        test.pushFront(90);
        test.pushBack(5);

        test.insert(999, 0);
        test.insert(32154, 3);
        test.insert(74554, 7);
        test.insert(11111, 9);

        test.remove(3);
        test.remove(7);
        test.remove(0);

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
    }
}

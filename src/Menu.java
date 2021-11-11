import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private LinkedList<Object> linkedList;
    private TypeBuilder typeBuilder;

    Menu() {
        linkedList = new LinkedList<>();
    }

    public void start() {
        boolean isExit = false;
        int input;

        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите тип данных");
            for (String type : TypeFactory.getTypeNameList())
                System.out.println(type);
            System.out.print(">> ");
            String str = in.nextLine();
            try {
                typeBuilder = TypeFactory.getBuilder(Types.valueOf(str));
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
            if (typeBuilder == null)
                System.out.println("Попробуйте снова");
            else
                break;
        }

        printMenu();

        while (!isExit) {
            System.out.print(">> ");

            try {

                input = in.nextInt();

                switch (input) {
                    case 1 -> {
                        linkedList.forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.print("Введите количество: ");
                        int count = in.nextInt();
                        for (int i = 0; i < count; i++)
                            linkedList.pushBack(typeBuilder.create());
                        System.out.println(count + " объектов были успешно добавлены");
                    }
                    case 3 -> {
                        System.out.print("Введите объект: ");
                        if (typeBuilder.getTypeName().equals("Integer")) {
                            int data = in.nextInt();
                            linkedList.pushFront(data);
                        } else if (typeBuilder.getTypeName().equals("String")) {
                            in.nextLine();
                            String data = in.nextLine();
                            linkedList.pushFront(data);
                        } else {
                            System.out.println("Ошибка в выбранном типе!");
                        }
                    }
                    case 4 -> {
                        System.out.print("Введите объект: ");
                        if (typeBuilder.getTypeName().equals("Integer")) {
                            int data = in.nextInt();
                            linkedList.pushBack(data);
                        } else if (typeBuilder.getTypeName().equals("String")) {
                            in.nextLine();
                            String data = in.nextLine();
                            linkedList.pushBack(data);
                        } else {
                            System.out.println("Ошибка в выбранном типе!");
                        }
                    }
                    case 5 -> {
                        System.out.print("Введите индекс: ");
                        int index = in.nextInt();
                        System.out.print("Введите объект: ");
                        if (typeBuilder.getTypeName().equals("Integer")) {
                            int data = in.nextInt();
                            linkedList.insert(data, index);
                        } else if (typeBuilder.getTypeName().equals("String")) {
                            in.nextLine();
                            String data = in.nextLine();
                            linkedList.insert(data, index);
                        } else {
                            System.out.println("Ошибка в выбранном типе!");
                        }
                    }
                    case 6 -> {
                        System.out.print("Введите индекс: ");
                        int index = in.nextInt();
                        linkedList.remove(index);
                    }
                    case 7 -> {
                        System.out.print("Введите индекс: ");
                        int index = in.nextInt();
                        System.out.println("Полученный объект: " + linkedList.get(index));
                    }
                    case 8 -> {
                        long start = System.nanoTime();
                        linkedList.sort(typeBuilder.getTypeComparator());
                        double time = (double)(System.nanoTime() - start) / 1_000_000_000.0;
                        System.out.printf("Список был успешно отсортирован за %.3f cек\n", time);
                    }
                    case 9 -> {
                       linkedList.save(new File(typeBuilder.getTypeName() + ".data"));
                        System.out.println("Данные были успешно сохранены в файл");
                    }
                    case 10 -> {
                        linkedList.load(new File(typeBuilder.getTypeName() + ".data"));
                        System.out.println("Данные были успешно загружены из файла");
                    }
                    case 0 -> isExit = true;
                    default -> System.out.println("Попробуйте снова");
                }
            }
            catch (NullPointerException | IOException | ClassNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
            catch (InputMismatchException exception) {
                System.out.println("Некорректный ввод");
                in.nextLine();
            }
        }
    }

    private void printMenu() {
        String str = """
                Меню
                [1]  Вывести все объекты
                [2]  Добавить n-ое количество объектов
                [3]  Добавить объект в начало
                [4]  Добавить объект в конец
                [5]  Добавить объект по индексу
                [6]  Удалить объект по индексу
                [7]  Получить объект по индексу
                [8]  Отсортировать список
                [9]  Сохранить объекты
                [10] Загрузить объекты
                [0]  Выход из программы
                """;

        System.out.println(str);
    }
}

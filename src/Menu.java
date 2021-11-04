import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    LoopList<Object> loopList;
    TypeBuilder typeBuilder;

    Menu() {
        loopList = new LoopList<>();
    }

    public void start() {
        boolean isExit = false;
        int input, k;

        Scanner in = new Scanner(System.in);

        while (true) {
            k = 1;
            System.out.println("Выберите тип данных");
            for (Types type : Types.values())
                System.out.println(k++ + " - " + type);
            System.out.print(">> ");
            input = in.nextInt();

            if (input == 1) {
                typeBuilder = new StringType();
                break;
            } else if (input == 2) {
                typeBuilder = new IntegerType();
                break;
            } else {
                System.out.println("Попробуйте снова");
            }
        }

        printMenu();

        while (!isExit) {
            System.out.print(">> ");

            input = in.nextInt();

            try {
                switch (input) {
                    case 1 -> {
                        loopList.forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.print("Введите количество: ");
                        int count = in.nextInt();
                        for (int i = 0; i < count; i++)
                            loopList.pushBack(typeBuilder.create());
                        System.out.println(count + " объектов были успешно добавлены");
                    }
                    case 3 -> {
                        System.out.print("Введите объект: ");
                        if (typeBuilder.getTypeName().equals("Integer")) {
                            int data = in.nextInt();
                            loopList.pushFront(data);
                        } else if (typeBuilder.getTypeName().equals("String")) {
                            in.nextLine();
                            String data = in.nextLine();
                            loopList.pushFront(data);
                        } else {
                            System.out.println("Ошибка в выбранном типе!");
                        }
                    }
                    case 4 -> {
                        System.out.print("Введите объект: ");
                        if (typeBuilder.getTypeName().equals("Integer")) {
                            int data = in.nextInt();
                            loopList.pushBack(data);
                        } else if (typeBuilder.getTypeName().equals("String")) {
                            in.nextLine();
                            String data = in.nextLine();
                            loopList.pushBack(data);
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
                            loopList.insert(data, index);
                        } else if (typeBuilder.getTypeName().equals("String")) {
                            in.nextLine();
                            String data = in.nextLine();
                            loopList.insert(data, index);
                        } else {
                            System.out.println("Ошибка в выбранном типе!");
                        }
                    }
                    case 6 -> {
                        System.out.print("Введите индекс: ");
                        int index = in.nextInt();
                        loopList.remove(index);
                    }
                    case 7 -> {
                        System.out.print("Введите индекс: ");
                        int index = in.nextInt();
                        System.out.println("Полученный объект: " + loopList.get(index));
                    }
                    case 8 -> {
                        loopList.sort(typeBuilder.getTypeComparator());
                        System.out.println("Список был успешно отсортирован");
                    }
                    case 9 -> {
                       loopList.save(new File(typeBuilder.getTypeName() + ".data"));
                        System.out.println("Данные были успешно сохранены в файл");
                    }
                    case 10 -> {
                        loopList.load(new File(typeBuilder.getTypeName() + ".data"));
                        System.out.println("Данные были успешно загружены из файла");
                    }
                    case 0 -> isExit = true;
                    default -> System.out.println("Попробуйте снова");
                }
            }
            catch (NullPointerException | IOException | ClassNotFoundException exception) {
                System.out.println(exception.getMessage());
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

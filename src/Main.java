import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {

        MarkArrayList<Student> students = new MarkArrayList<>();


        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        readStudentsFromFile("students.csv", students);

        while (running) {

            System.out.println("=======Меню=======");
            System.out.println("1. Добавить студента");
            System.out.println("2. Показать студентов");
            System.out.println("3. Имена лучших студентов по алфавиту (Stream API)");
            System.out.println("4. Удалить студента");
            System.out.println("5. Сортировать студентов");
            System.out.println("0. Выход");

            String userChoice = scanner.nextLine();

            switch (userChoice) {
                case "1" -> addStudent(scanner, students);

                case "2" -> showStudents(students);

                case "3" -> showBestStudents(students);

                case "4" -> removeStudent(scanner, students);

                case "5" -> sortStudents(scanner, students);

                case "0" -> running = false;

                default -> System.out.println("Ошибка: неизвестная команда!");
            }


        }
    }


    //валидация ввода имени
    public static String readNotEmptyString(Scanner scanner, String message) {
        String result = "";
        boolean valid = false;

        while (!valid) {
            System.out.println(message);
            result = scanner.nextLine().trim();
            if (result.isEmpty()) {
                System.out.println("Ошибка: поле не может быть пустым!");
            } else {
                valid = true;
            }
        }
        return result;
    }

    //валидация ввода среднего балла
    public static double readValidGrade(Scanner scanner, String message) {
        boolean valid = false;
        double result = 0;
        while (!valid) {
            try {
                System.out.println(message);
                result = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число в формате 4.5");
                continue;
            }
            if (!isValidGrade(result)) {
                System.out.println("Ошибка, неверное значение!");
            } else {
                valid = true;
            }
        }
        return result;
    }

    //проверка грейда
    public static boolean isValidGrade(double grade) {
        return Double.isFinite(grade) && grade >= 0 && grade <= 5;
    }

    //чтение студентов из csv
    public static void readStudentsFromFile(String fileName, MarkArrayList<Student> students) {
        Path path = Path.of(fileName);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(";", -1);
                if (parts.length != 3) {
                    System.out.println("Ошибка: запись должна содержать ровно 3 поля");
                    continue;
                }
                String name = parts[0].trim();

                double avgGrade;

                try {
                    avgGrade = Double.parseDouble(parts[1].trim());
                    if (!isValidGrade(avgGrade)) {
                        System.out.println("Неверный формат среднего балла в файле");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат числа");
                    continue;
                }
                String number = parts[2].trim();

                if (name.isEmpty() || number.isEmpty()) {
                    System.out.println("Ошибка: имя или номер зачётки пустые");
                    continue;
                }

                students.add(new Student(name, avgGrade, number));
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
    }

    //добавление студента в список
    public static void addStudent(Scanner scanner, MarkArrayList<Student> students) {
        String studentName = readNotEmptyString(scanner, "Введите имя");
        double avgGrade = readValidGrade(scanner, "Введите средний балл");
        String studentNumber = readNotEmptyString(scanner, "Введите номер зачетной книжки: ");
        students.add(new Student(studentName, avgGrade, studentNumber));
    }

    //вывод списка студентов
    public static void showStudents(MarkArrayList<Student> students) {
        if (!students.isEmpty()) {
            System.out.println("Студенты");
            System.out.println("Количество студентов: " + students.size());
            for (int i = 0; i < students.size(); i++) {
                System.out.println(i + 1 + ". ");
                System.out.println(students.get(i));
            }
        } else {
            System.out.println("Список студентов пуст");
        }
    }

    //вывод отсортированных лучших студентов
    public static void showBestStudents(MarkArrayList<Student> students) {
        boolean hasBestStudents = students.stream()
                .anyMatch(student -> student.getAverageGrade() >= 4.5);
        if (hasBestStudents) {
            students.stream()
                    .filter(student -> student.getAverageGrade() >= 4.5)
                    .sorted(Comparator.comparing(Student::getName))
                    .map(student -> student.getName())
                    .forEach(System.out::println);
        } else {
            System.out.println("Нет студентов с баллом от 4.5");
        }
    }

    //удаление студента из списка
    public static void removeStudent(Scanner scanner, MarkArrayList<Student> students) {

        if (students.isEmpty()) {
            System.out.println("Список студентов пуст");
            return;
        }

        System.out.println("Введите номер студента в списке для удаления");
        int result;
        try {
            result = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Введите целое число!");
            return;
        }
        if (result >= 1 && result <= students.size()) {
            students.remove(result - 1);
            System.out.println("Студент успешно удалён!");
        } else {
            System.out.println("Неверный номер");
        }
    }

    //сортировка
    public static void sortStudents(
            Scanner scanner,
            MarkArrayList<Student> students
    ) {
        Student[] studentsArray = new Student[students.size()];
        for (int i = 0; i < students.size(); i++) {
            studentsArray[i] = students.get(i);
        }
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст!");
            return;
        }

        System.out.println("Выберите алгоритм сортировки:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");

        String choice = scanner.nextLine();
        SortAlgorithm<Student> algorithm;

        switch (choice) {
            case "1" -> algorithm = new BubbleSort<>();
            case "2" -> algorithm = new SelectionSort<>();
            default -> {
                System.out.println("Неверный выбор алгоритма!");
                return;
            }
        }

        System.out.println("Выберите направление сортировки:");
        System.out.println("1. По возрастанию");
        System.out.println("2. По убыванию");

        String direction = scanner.nextLine();

        Comparator<Student> comparator =
                Comparator.comparingDouble(Student::getAverageGrade);

        switch (direction) {
            case "1" -> {
                //оставляем направление
            }
            case "2" -> {
                comparator = comparator.reversed();
            }
            default -> {
                System.out.println("Неверное направление сортировки!");
                return;
            }
        }
        algorithm.sort(studentsArray, comparator);


        System.out.println("Отсортированный список студентов");
        for (int i = 0; i < studentsArray.length; i++) {
            System.out.println(i + 1 + ". ");
            System.out.println(studentsArray[i]);
        }

    }
}


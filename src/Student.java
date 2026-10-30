public class Student {
    private String name;
    private double averageGrade;
    private String recordBookNumber;

    public Student(String name, double averageGrade, String recordBookNumber) {
        this.name = name;
        this.averageGrade = averageGrade;
        this.recordBookNumber = recordBookNumber;
    }

    public String getName() {
        return name;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public String toString() {
        return "Имя: " + name + "\nСредний балл: " + averageGrade + "\nНомер зачетки: " + recordBookNumber;
    }

}


package EduResults;

public class Student {
	String name;
    int score;
    String district;

    Student(String name, int score, String district) {
        this.name=name;
        this.score=score;
        this.district=district;
    }

    @Override
    public String toString() {
        return "Name of student- "+name+ "\n District is- "+district+ "\n Score is- "+score;
    }
}

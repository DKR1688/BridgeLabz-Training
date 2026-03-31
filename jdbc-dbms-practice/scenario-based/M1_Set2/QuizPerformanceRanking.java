package M1_Set2;

import java.util.*;

public class QuizPerformanceRanking {
	List<Student> records = new ArrayList<>();

	public void record(String name, String dept, int q1, int q2, int q3) {
		Student s = new Student(name, dept, q1, q2, q3);
		records.add(s);
		System.out.println("Record Added: " + name);
	}

	public void topDepartment(String dept) {
		List<Student> deptStudents = new ArrayList<>();
		for (Student s : records) {
			if (s.department.equals(dept)) {
				deptStudents.add(s);
			}
		}

		if (deptStudents.isEmpty()) {
			System.out.println("Department Not Found");
			return;
		}

		int max = deptStudents.stream().mapToInt(s -> s.total).max().orElse(-1);
		for (Student s : deptStudents) {
			if (s.total == max) {
				System.out.println(s.name + " " + s.total);
			}
		}
	}

	public void topQuiz(String quizName) {
		if (records.isEmpty()) {
			System.out.println("No Records Available");
			return;
		}

		int max = -1;
		switch (quizName) {
		case "Q1":
			max = records.stream().mapToInt(s -> s.q1).max().orElse(-1);
			for (Student s : records)
				if (s.q1 == max)
					System.out.println(s.name + " " + s.q1);
			break;
		case "Q2":
			max = records.stream().mapToInt(s -> s.q2).max().orElse(-1);
			for (Student s : records)
				if (s.q2 == max)
					System.out.println(s.name + " " + s.q2);
			break;
		case "Q3":
			max = records.stream().mapToInt(s -> s.q3).max().orElse(-1);
			for (Student s : records)
				if (s.q3 == max)
					System.out.println(s.name + " " + s.q3);
			break;
		default:
			System.out.println("Invalid Quiz Name");
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = Integer.parseInt(sc.nextLine());
		QuizPerformanceRanking qrs = new QuizPerformanceRanking();

		for (int i = 0; i < N; i++) {
			String[] parts = sc.nextLine().split(" ");
			if (parts[0].equals("Record")) {
				qrs.record(parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]),
						Integer.parseInt(parts[5]));
			} else if (parts[0].equals("Top")) {
				if (parts[1].startsWith("Q")) {
					qrs.topQuiz(parts[1]);
				} else {
					qrs.topDepartment(parts[1]);
				}
			}
		}
		sc.close();
	}
}

class Student {
	String name;
	String department;
	int q1, q2, q3;
	int total;

	Student(String name, String department, int q1, int q2, int q3) {
		this.name = name;
		this.department = department;
		this.q1 = q1;
		this.q2 = q2;
		this.q3 = q3;
		this.total = q1 + q2 + q3;
	}
}
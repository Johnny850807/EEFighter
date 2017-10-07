package StudentSystem;

import java.util.ArrayList;

import Exception.SemesterNumberNotFoundException;

public abstract class Student {

	public static int number = 0;
	protected String id;
	protected String name;
	protected char symbol;

	protected ArrayList<Semester> semesters;

	public Student(char symbol, Semester semester) {
		this.symbol = getIdSymbol();
		if (this.symbol == symbol) {
			setId(String.valueOf(symbol) + number++);
			addSemester(semester);
		}
	}

	public void addSemester(Semester semester) {
		semesters = new ArrayList<>();
		semesters.add(semester);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract char getIdSymbol();

	public int takeCredit(int semesterNumber) {
		for (Semester semester : semesters) {
			if (semesterNumber == semester.getId())
				return semester.getCredit();
		}
		throw new SemesterNumberNotFoundException();
	}

	public abstract int countExpense(int credit);

}

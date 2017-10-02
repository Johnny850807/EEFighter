package StudentSystem;

public class UniversityStudent extends Student {
	
	public UniversityStudent(char symbol, Semester semester) {
		super(symbol, semester);
	}

	@Override
	public int countExpense(int credit) {
		return credit * 100;
	}

	@Override
	public char getIdSymbol() {
		return 'S';
	}

}

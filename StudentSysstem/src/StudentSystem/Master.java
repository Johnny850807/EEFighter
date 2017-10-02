package StudentSystem;

public class Master extends Student{
	
	public Master(char symbol, Semester semester) {
		super(symbol, semester);
	}

	@Override
	public int countExpense(int credit) {
		return credit * 200;
	}

	@Override
	public char getIdSymbol() {
		return 'M';
	}
	
}

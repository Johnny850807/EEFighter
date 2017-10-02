package Test;

import Exception.IdNotFoundException;
import Exception.SemesterNumberNotFoundException;
import StudentSystem.Master;
import StudentSystem.Semester;
import StudentSystem.StudentSystem;
import StudentSystem.UniversityStudent;

public class Main {

	public static void main(String[] args) {
		String id = "M123";
		StudentSystem studentSystem = new StudentSystem();
		studentSystem.signUp(new UniversityStudent('S', new Semester(1, 20)));
		studentSystem.signUp(new Master('M', new Semester(1, 15)));
		try {
			System.out.println(studentSystem.searchCreditExpense(id, 1));
		} catch (IdNotFoundException err) {
			System.out.println(err.getMessage());
		} catch (SemesterNumberNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}

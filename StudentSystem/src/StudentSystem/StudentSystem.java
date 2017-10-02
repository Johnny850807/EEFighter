package StudentSystem;

import java.util.ArrayList;
import java.util.List;

import Exception.IdNotFoundException;

public class StudentSystem {

	private List<Student> students = new ArrayList<>();
	
	public void signUp(Student student) {
		if (student.id != null) 
			students.add(student);
	}
	
	public List<Student> getStudents() {
		return students;
	}
	
	public int searchCreditExpense(String id, int semester) {
		for(Student student : students)
			if (student.getId().equals(id))
				return student.countExpense(student.takeCredit(semester)); 
		throw new IdNotFoundException();
	}
	
}

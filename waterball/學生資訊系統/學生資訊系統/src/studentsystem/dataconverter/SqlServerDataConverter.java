package studentsystem.dataconverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import studentsystem.model.Course;
import studentsystem.model.CourseClass;
import studentsystem.model.CreditFeeType;
import studentsystem.model.Professor;
import studentsystem.model.Student;

public class SqlServerDataConverter implements DataConverter {

	public Student sqlToStudent(ResultSet rs) throws SQLException {
		Student student = parseStudent(rs);
		CreditFeeType creditFeeType = parseCreditType(rs);
		
		student.setCreditFeeType(creditFeeType);
		return student;
	}
	
	private Student parseStudent(ResultSet rs) throws SQLException{
		Student student = new Student();
		student.setId(rs.getInt("id"));
		student.setAccount(rs.getString("account"));
		student.setPassword(rs.getString("password"));
		student.setName(rs.getString("name"));
		return student;
	}
	
	private CreditFeeType parseCreditType(ResultSet rs) throws SQLException{
		CreditFeeType creditFeeType = new CreditFeeType();
		creditFeeType.setId(rs.getInt("feeType"));
		creditFeeType.setFeePerCredit(rs.getInt("feePerCredit"));
		creditFeeType.setName(rs.getString("name"));
		return creditFeeType;
	}

	public List<CourseClass> sqlToClassList(ResultSet rs) throws SQLException {
		List<CourseClass> classes = new ArrayList<>();
		while(rs.next())
		{
			CourseClass clazz = parseCourseClass(rs);
			classes.add(clazz);
		}
		
		Collections.sort(classes);
		return classes;
	}
	
	private CourseClass parseCourseClass(ResultSet rs) throws SQLException{
		CourseClass clazz = new CourseClass();
		Course course = parseCourse(rs);
		Professor professor = parseProfessor(rs);
		
		clazz.setClassNumber(rs.getString("classNumber"));
		clazz.setCourse(course);
		clazz.setProfessor(professor);

		return clazz;
	}
	
	private Course parseCourse(ResultSet rs) throws SQLException{
		Course course = new Course();
		course.setNumber(rs.getString("courseNumber"));
		course.setCredit(rs.getInt("credit"));
		course.setName(rs.getString("name"));
		course.setContent(rs.getString("content"));
		return course;
	}
	
	private Professor parseProfessor(ResultSet rs) throws SQLException{
		Professor professor = new Professor();
		professor.setId(rs.getInt("professorId"));
		professor.setName(rs.getString("professorName"));
		return professor;
	}
}

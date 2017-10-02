package studentsystem.dataconverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import studentsystem.model.CourseClass;
import studentsystem.model.Student;

public interface DataConverter {

	public Student sqlToStudent(ResultSet rs) throws SQLException;

	public List<CourseClass> sqlToClassList(ResultSet rs) throws SQLException;

}

package studentsystem;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import studentsystem.dataconverter.DataConverter;
import studentsystem.exception.AccountNotFoundException;
import studentsystem.exception.AuthorizationFailedException;
import studentsystem.model.CourseClass;
import studentsystem.model.Student;
import studentsystem.model.modelview.ClassesAndCreditSum;
import studentsystem.sql.SqlCore;

public class StudentSystem {
	private SqlCore sqlCore;
	private DataConverter dataConverter;
	private final String QUERY_SIGN_IN = "select S.id, S.account, S.password, S.name, CF.id as feeType, CF.feePerCredit, CF.name " +
			" from Student as S inner join CreditFeeType as CF on S.creditFeeTypeId = CF.id " +
			" where account = '%s' and password = '%s' ";
	
	private final String QUERY_SIGN_UP = "insert into Student ([name], account, [password], creditFeeTypeId ) values" +
			"(N'%s', '%s', '%s', %d)";
	private final String QUERY_REGISTER_CLASS = "insert into RegisterClass values (%d, '%s', '%s')";
	private final String QUERY_ALL_CLASSES = "select C.number as classNumber, CR.number as courseNumber, " +
			"CR.name as name, CR.content as content, CR.credit as credit, P.id as professorId, P.name as professorName" +
			" from CourseClass as C" +
			" inner join Professor as P on C.professorId = P.id" +
			" inner join Course as CR on C.courseNumber = CR.number";
	
	private final String QUERY_STUDENT_CLASSES = "select C.number as classNumber, C.courseNumber, CO.credit, CO.name, CO.content, P.id as professorId, P.name as professorName "+
			" from CourseClass as C " +
			" inner join RegisterClass as R on C.number = R.classNumber and C.courseNumber = R.courseNumber" +
			" inner join Course as CO on C.courseNumber = CO.number" +
			" inner join Professor as P on C.professorId = P.id" +
			" where R.studentId = %d";
	
	private Student currentStudent;
	
	public void start(DataConverter dataConverter){
		this.dataConverter = dataConverter;
		this.sqlCore = SqlCore.getInstance();
		System.out.println("Student system starts.");
	}
	
	public void signOut(){
		currentStudent = null;
	}
	

	public void signIn(String account, String password, Callback<Student> cb){
		new Thread(){
			public void run() {
				try{
					String sql = String.format(QUERY_SIGN_IN, account, password);
					runSignInQuery(sql, cb);
				}catch (SQLException err) {
					cb.onError(err);
				}
			}
		}.start();
	}
	
	private void runSignInQuery(String sql, Callback<Student> cb) throws SQLException{
		ResultSet resultSet = sqlCore.executeQuery(sql);
		if (resultSet.next())
		{
			currentStudent = dataConverter.sqlToStudent(resultSet);
			cb.onQueryFinish(currentStudent);
		}
		else
			cb.onError(new AccountNotFoundException("Account not found."));
	}
	
	public void signUp(String account, String password, String name, int feeType, Callback<Student> cb){
		new Thread(){
			public void run() {
				try{
					String sql = String.format(QUERY_SIGN_UP, name, account, password, feeType);
					sqlCore.execute(sql); //first insert the new student
					signIn(account, password, cb); // then sign in the student
				}catch (Exception err) {
					cb.onError(err);
				}
			}
		}.start();
	}

	public void registerClass(String classNumber, String courseNumber, Callback<Void> cb) {
		new Thread(){
			@Override
			public void run() {
				try{
					String sql = String.format(QUERY_REGISTER_CLASS, currentStudent.getId(), courseNumber, classNumber);
					sqlCore.execute(sql);
					cb.onQueryFinish(null);
				}catch (NullPointerException e) {
					// if the currentStudent == null, exception goes here.
					cb.onError(new AuthorizationFailedException("你必須先登入。"));
				}catch (Exception err) {
					cb.onError(err);
				}
			}
		}.start();
	}
	

	public void getAllClassesAndCreditSum(Callback<ClassesAndCreditSum> cb) {
		new Thread(){
			@Override
			public void run() {
				try{
					String sql = String.format(QUERY_STUDENT_CLASSES, currentStudent.getId());
					runAllClassesCreditFeeSumSql(sql, cb);
				}catch (NullPointerException err) {
					// if the currentStudent == null, exception goes here.
					cb.onError(new AuthorizationFailedException("你必須先登入。"));
				}catch (SQLException err) {
					cb.onError(err);
				}
			}
		}.start();
	}
	
	private void runAllClassesCreditFeeSumSql(String sql, Callback<ClassesAndCreditSum> cb)throws SQLException{
		ResultSet resultSet = sqlCore.executeQuery(sql);
		List<CourseClass> courseClasses = dataConverter.sqlToClassList(resultSet);
		int sum = countCreditSum(courseClasses);
		cb.onQueryFinish(new ClassesAndCreditSum(courseClasses, sum));
	}
	
	private int countCreditSum(List<CourseClass> courseClasses){
		int sum = 0;
		for (CourseClass clazz : courseClasses)
			sum += clazz.getCredit();
		return sum;
	}

	public void showCourseClassList(Callback<List<CourseClass>> cb) {
		new Thread(){
			@Override
			public void run() {
				try{
					runAllClassesQuery(QUERY_ALL_CLASSES, cb);
				}catch (SQLException err) {
					cb.onError(err);
				}
			}
		}.start();
	}
	
	private void runAllClassesQuery(String sql, Callback<List<CourseClass>> cb) throws SQLException{
		ResultSet resultSet = sqlCore.executeQuery(sql);
		List<CourseClass> classes = dataConverter.sqlToClassList(resultSet);
		cb.onQueryFinish(classes);
	}
	
	public Student getCurrentStudent() {
		return currentStudent;
	}

}

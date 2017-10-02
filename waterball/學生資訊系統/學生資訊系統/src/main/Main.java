package main;

import java.util.List;

import Util.Input;
import studentsystem.Callback;
import studentsystem.StudentSystem;
import studentsystem.dataconverter.SqlServerDataConverter;
import studentsystem.model.CourseClass;
import studentsystem.model.Student;
import studentsystem.model.modelview.ClassesAndCreditSum;

public class Main {
	private static StudentSystem studentSystem;
	
	public static void main(String[] args) {
		studentSystem = new StudentSystem();  //�Ұʨt��
		studentSystem.start(new SqlServerDataConverter());  //�]�m����ഫ strategy
		
		startSignView();
	}
	
	private static void startSignView(){
		int option = Input.nextInt("Choose (1) Sign in (2) Sign up : ", 1, 2);
		switch (option) {
		case 1:
			signIn();
			break;
		case 2:
			signUp();
			break;
		}
	}
	
	private static void signIn(){
		String account = Input.next("Account : ");
		String password = Input.next("Password : ");
		studentSystem.signIn(account, password, new StudentCallback());
	}
	
	private static void signUp(){
		String name = Input.next("Name : ");
		String account = Input.next("Account : ");
		String password = Input.next("Password : ");
		int type = Input.nextInt("Input your degree (1) University (2) Master" ,1 ,2);
		studentSystem.signUp(account, password, name, type, new StudentCallback());
	}

	private static class StudentCallback implements Callback<Student>{
		@Override
		public void onQueryFinish(Student student) {
			System.out.println("Hello! " + student.getName());
			startStudentView();  // �n�J���\ �e���ǥͰ򥻥\�୶��
		}
		@Override
		public void onError(Exception err) {
			System.out.println("Sign in error : " + err.getMessage());
			startSignView(); // �n�J���� �A�@��
		}
	}
	
	private static void startStudentView(){
		int option = Input.nextInt("\n\nChoose (1) Watch All Classes (2) Watch Signed Classes (3) Sign Up Class (4) Sign Out :", 1, 4);
		switch (option) {
		case 1:
			watchAllClasses();
			break;
		case 2:
			watchSignedClasses();			
			break;
		case 3:
			signUpClass();
			break;		
		case 4:
			signOut();
			break;
		}
	}


	private static void watchAllClasses() {
		studentSystem.showCourseClassList(new Callback<List<CourseClass>>() {
			@Override
			public void onQueryFinish(List<CourseClass> classes) {
				for (CourseClass clazz : classes)
					System.out.println(clazz);
				
				startStudentView();
			}
			@Override
			public void onError(Exception err) {
				System.out.println("System error : " + err.getMessage());
				startStudentView();
			} 
		});
	}
	
	private static void watchSignedClasses() {
		studentSystem.getAllClassesAndCreditSum(new Callback<ClassesAndCreditSum>() {
			@Override
			public void onQueryFinish(ClassesAndCreditSum info) {
				Student student = studentSystem.getCurrentStudent();
				int creditFeeSum = student.getCreditFeeType().getFeePerCredit() * info.getCreditSum();  //�p��Ǥ��O = �C�Ǥ��O�� * �Ǥ���
				List<CourseClass> classes = info.getClasses();
				System.out.println("Credit sum : " + info.getCreditSum() + ", Credit fee sum : " + creditFeeSum);
				System.out.println("Signed Class list : ");
				for (CourseClass clazz : classes)
					System.out.println(clazz);
				
				startStudentView();
			}

			@Override
			public void onError(Exception err) {
				System.out.println("System error : " + err.getMessage());
				startStudentView();
			}
		});
	}
	
	private static void signUpClass(){
		String classNumber = Input.next("Input class number (0 cancel): ");
		String courseNumber = Input.next("Input course number (0 cancel): ");
		if (!classNumber.equals("0") && !courseNumber.equals("0"))
			studentSystem.registerClass(classNumber, courseNumber, new Callback<Void>() {
				@Override
				public void onQueryFinish(Void v) {
					System.out.println("Operation succeeds !");
					startStudentView();
				}
				
				@Override
				public void onError(Exception err) {
					System.out.println("Error occurs, please input the correct numbers.");
					startStudentView();
				}
			});
	}
	
	private static void signOut() {
		studentSystem.signOut();
		startSignView();
	}
}

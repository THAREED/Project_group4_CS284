package TestJUnit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;

import Controller.FillScoresController;
import model.Course;
import model.CourseList;
import model.Member;
import model.MemberList;
import model.StudentList;

public class FillScoresControllerTest {

	Member member;
	MemberList mList;
	Course course;
	CourseList courseList;
	StudentList studentList;
	File file;
	FillScoresController scores;

	@Before
	public void setUp() throws Exception {
		mList = new MemberList();
		courseList = new CourseList();
		JOptionPane.showMessageDialog(null, "Strat test Score.!");
	}

	
	@Test
	public void testFillScore() throws IOException {

		mList = new MemberList();
		member = mList.getMemberList(3);
		course = new Course(courseList.getCourse(0).getCourseID(), courseList.getCourse(0).getCourseName(),
				courseList.getCourse(0).getAssFull(), courseList.getCourse(0).getAssAcc(),
				courseList.getCourse(0).getMidFull(), courseList.getCourse(0).getMidAcc(),
				courseList.getCourse(0).getFinalFull(), courseList.getCourse(0).getFinalFull());
		
		//studentList = new StudentList(member, course);
		 /*   studentList.loadList();
	  	studentList.setStudentList();
		studentList.saveList();
*/
		file = new File(member.getUsername() + "_" + course.getCourseName() + "List.txt");
		//studentList.setStudentShowInFill(file);
		scores = new FillScoresController(member, course, file);

	//	scores.fillScore();
		studentList = scores.calculate();
		studentList.getIndex(2).setAssFull(20);
		studentList.getIndex(2).setMidFull(80);
		studentList.getIndex(2).setFinalFull(10);

	//	studentList.saveList();
		//scores.calculate();
		System.out.println(studentList.getIndex(2).getTotalScore());

		studentList = scores.calculate();
        scores.fillScore();
		scores.updateTable();
		System.out.println(courseList.getCourse(2).toString());
		System.out.println(studentList.getIndex(2).toString());
		System.out.println();
		for (int i = 0; i < studentList.getSize(); i++) {
			System.out.println(studentList.getIndex(i).toString());
		}
		//System.out.println(studentList.getSize());
		
		assertEquals(scores.isUpdate(), true);
		//assertEquals(studentList, true);
	}

}

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
		JOptionPane.showMessageDialog(null, "Strat test FillScore.!");
	}

	
	@Test
	public void testFillScore() throws IOException {

		mList = new MemberList();
		member = mList.getMemberList(3);
		course = new Course(courseList.getCourse(0).getCourseID(), courseList.getCourse(0).getCourseName(),
				courseList.getCourse(0).getAssFull(), courseList.getCourse(0).getAssAcc(),
				courseList.getCourse(0).getMidFull(), courseList.getCourse(0).getMidAcc(),
				courseList.getCourse(0).getFinalFull(), courseList.getCourse(0).getFinalAcc());
		
		file = new File(member.getUsername() + "_" + course.getCourseName() + "List.txt");
		scores = new FillScoresController(member, course, file);

		studentList = new StudentList(member, course);
		studentList = scores.calculate();
		studentList.getIndex(0).setAssFull(50);
		studentList.getIndex(0).setMidFull(80);
		studentList.getIndex(0).setFinalFull(40);
		
		System.out.println(studentList.getIndex(0).getTotalScore());

		studentList = scores.calculate();
        scores.fillScore();
		scores.updateTable();
		System.out.println(courseList.getCourse(0).toString());
		
		Double totalTure = new Double(studentList.getIndex(0).getTotalScore());
		System.out.println(totalTure);
		Double total = new Double(studentList.getIndex(0).getAssAcc() + studentList.getIndex(0).getMidAcc() +studentList.getIndex(0).getFinalAcc());
		System.out.println(studentList.getIndex(0).toString());
		System.out.println(total);
	
		assertEquals(totalTure, total);
		assertEquals(scores.isUpdate(),true);
		
	}




}

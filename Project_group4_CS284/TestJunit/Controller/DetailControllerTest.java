package Controller;

import static org.junit.Assert.*;

import java.io.IOException;


import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;

import Controller.DetailController;
import model.Course;
import model.CourseList;

public class DetailControllerTest {

	Course course;
	CourseList courseList;

	@Before
	public void setUp() throws Exception {
		courseList = new CourseList();
		JOptionPane.showMessageDialog(null, "Strat test SetScore!");
	}

	@Test
	public void testSetFullScore() throws IOException {

		course = new Course(courseList.getCourse(0).getCourseID(), courseList.getCourse(0).getCourseName(),
				courseList.getCourse(0).getAssFull(), courseList.getCourse(0).getAssAcc(),
				courseList.getCourse(0).getMidFull(), courseList.getCourse(0).getMidAcc(),
				courseList.getCourse(0).getFinalFull(), courseList.getCourse(0).getFinalFull());

		DetailController detail = new DetailController(course);

		// set full score is true
		detail.setFullScore("100", "100", "100");
		assertEquals(detail.isCheckScoreFull(), true);

		// set full score is false
		detail.setFullScore("50", "str", "100");
		assertEquals(detail.isCheckScoreFull(), false);

	}

	@Test
	public void testSetAccumulatedScore() throws IOException {

		course = new Course(courseList.getCourse(0).getCourseID(), courseList.getCourse(0).getCourseName(),
				courseList.getCourse(0).getAssFull(), courseList.getCourse(0).getAssAcc(),
				courseList.getCourse(0).getMidFull(), courseList.getCourse(0).getMidAcc(),
				courseList.getCourse(0).getFinalFull(), courseList.getCourse(0).getFinalFull());

		DetailController detail = new DetailController(course);

		// set accumulated score is true
		detail.setAccumulatedScore("50", "20", "30");
		assertEquals(detail.isCheckScoreAcc(), true);
		
		// set accumulated score is false
		detail.setAccumulatedScore("53", "20", "30");
		assertEquals(detail.isCheckScoreAcc(), false);

	}

}

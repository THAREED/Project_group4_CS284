package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Course;
import model.DetailException;
import model.Member;
import model.StudentList;

public class FillScoresController extends JPanel
{
	private Member member;
	private Course course;
	private StudentList studentList;
	private Object[][] data;
	private JTable table;
	private File file;
	private boolean update;
	public FillScoresController(Member member, Course course, File file) 
	{
		studentList = new StudentList(member, course);
		this.file = file;
		this.update = true;
		this.member = member;
		this.course = course;
		studentList.setStudentShowInFill(file);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		String[] columnNames = { "Number", "Student ID", "Assignment Full Score (" + course.getAssFull() + ")",
				"Midterm Full Score  (" + course.getMidFull() + ")",
				"Final Full Score  (" + course.getFinalFull() + ")",
				"Assignment Accumulated Scores  (" + course.getAssAcc() + ")",
				"Midterm Accumulated Scores  (" + course.getMidAcc() + ")",
				"Final Accumulated Scores  (" + course.getFinalAcc() + ")", "Total Score" };

		data = new Object[studentList.getSize()][9];
		for (int i = 0; i < studentList.getSize(); i++) {
			data[i][0] = studentList.getIndex(i).getNumber();
			data[i][1] = studentList.getIndex(i).getId();
			data[i][2] = studentList.getIndex(i).getAssFull();
			data[i][3] = studentList.getIndex(i).getMidFull();
			data[i][4] = studentList.getIndex(i).getFinalFull();
			data[i][5] = studentList.getIndex(i).getAssAcc();
			data[i][6] = studentList.getIndex(i).getMidAcc();
			data[i][7] = studentList.getIndex(i).getFinalAcc();
		//	data[i][8] = studentList.getIndex(i).getAssAcc() + studentList.getIndex(i).getMidAcc()
		//			+ studentList.getIndex(i).getFinalAcc();
			data[i][8] = studentList.getIndex(i).getTotalScore();
		}

		table = new JTable(data, columnNames);
		JScrollPane tableScroll = new JScrollPane(table);
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(tableScroll);
		table.setSelectionBackground(new Color(255, 255, 204));
		table.setGridColor(new Color(179, 235, 255));
		table.setEnabled(false);
	}
	public void setEnabled(boolean enabled)
	{
		if(enabled)
		{
			table.setEnabled(true);
		}
		else
		{
			table.setEnabled(false);
		}
	}
	public void calculate() {
		double totalAss;
		double totalMid;
		double totalFinal;
		for (int i = 0; i < studentList.getSize(); i++) {
			totalAss = (studentList.getIndex(i).getAssFull() / course.getAssFull()) * course.getAssAcc();
			totalMid = (studentList.getIndex(i).getMidFull() / course.getMidFull()) * course.getMidAcc();
			totalFinal = (studentList.getIndex(i).getFinalFull() / course.getFinalFull()) * course.getFinalAcc();
			
			studentList.getIndex(i).setAssAcc(totalAss);
			studentList.getIndex(i).setMidAcc(totalMid);
			studentList.getIndex(i).setFinalAcc(totalFinal);
		}
		studentList.saveList();
	}

	public void updateTable() {
		for (int i = 0; i < studentList.getSize(); i++) {
			table.setValueAt(studentList.getIndex(i).getAssAcc(), i, 5);
			table.setValueAt(studentList.getIndex(i).getMidAcc(), i, 6);
			table.setValueAt(studentList.getIndex(i).getFinalAcc(), i, 7);
			table.setValueAt(studentList.getIndex(i).getAssAcc() + studentList.getIndex(i).getMidAcc()
					+ studentList.getIndex(i).getFinalAcc(), i, 8);
		}
	}

	public void fillScore() {
		for (int i = 0; i < studentList.getSize(); i++) {
			try {
				double assFull = new Double(table.getValueAt(i, 2).toString());
				double midFull = new Double(table.getValueAt(i, 3).toString());
				double finalFull = new Double(table.getValueAt(i, 4).toString());
				if (finalFull < 0 || midFull < 0 || assFull < 0) {
					throw new DetailException(
							"For number " + studentList.getIndex(i).getNumber() + ": Score not be negative!");
				}
				if (finalFull > course.getFinalFull() || midFull > course.getMidFull()
						|| assFull > course.getAssFull()) {
					throw new DetailException("For number " + studentList.getIndex(i).getNumber() + ": Student ID "
							+ studentList.getIndex(i).getId() + " :\n Final Full Score must be in the range of 0 - "
							+ course.getFinalFull() + "\n" + "Or Midterm Full Score must be in the range of 0 - "
							+ course.getMidFull() + "\n" + "Or Assignment Full Score must be in the range of 0 - "
							+ course.getAssFull() + "\n");
				}
				studentList.getIndex(i).setAssFull(assFull);
				studentList.getIndex(i).setMidFull(midFull);
				studentList.getIndex(i).setFinalFull(finalFull);
			} catch (NumberFormatException | DetailException ex) {
				setUpdate(false);
				table.setValueAt(studentList.getIndex(i).getAssFull(), i, 2);
				table.setValueAt(studentList.getIndex(i).getMidFull(), i, 3);
				table.setValueAt(studentList.getIndex(i).getFinalFull(), i, 4);
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(this.getClass().getResource("/exceptionIcon.png")));
			}
		}
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
}

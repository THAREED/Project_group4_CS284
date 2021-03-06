package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Course;
import model.Member;
import model.StudentList;

public class EmailFrame extends JFrame
{
	private Member member;
	private Course course;
	private StudentList studentList;
	private Object[][] data;
	private JTable table;
	private File file;
	public EmailFrame(Member member, Course course) throws IOException 
	{
		this.member = member;
		this.course = course;
		studentList = new StudentList(member, course);
		file = new File( "TotalGrade" + "List.txt" );
		
		showTopPage();
		setCenterPage();
		studentList.getEmailStudentFromFile();
		studentList.getGradeStudentFromFile();
		showGradeStudent();
		showDownPage();
		
		setTitle("Transmission of email and file.");
		setVisible(true);
		setLocation(300, 100);
		pack();
	}
	public void setCenterPage() 
	{
		file = new File(member.getUsername() + "_" + course.getCourseName() + "List.txt");
		studentList.setStudentShowInFill(file);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		String[] columnNames = { "Number", "Student ID", "Email address", "Total", "Grade"};

		data = new Object[studentList.getSize()][9];
		
		for (int i = 0; i < studentList.getSize(); i++) {
			data[i][0] = studentList.getIndex(i).getNumber();
			data[i][1] = studentList.getIndex(i).getId();
			data[i][2] = studentList.getIndex(i).getEmail();
			data[i][3] = studentList.getIndex(i).getTotalScore();
			data[i][4] = studentList.getIndex(i).getGrad();
		}

		table = new JTable(data, columnNames);
		JScrollPane tableScroll = new JScrollPane(table);
		centerPanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		centerPanel.add(tableScroll);
		table.setSelectionBackground(new Color(255, 255, 204));
		table.setGridColor(new Color(179, 235, 255));
		table.setEnabled(false);
		this.add(centerPanel, BorderLayout.CENTER);
	}
	public void showTopPage() throws IOException {
		JPanel usrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel(member.getName());
		nameLabel.setIcon(new ImageIcon(this.getClass().getResource("/userIcon.png")));
		usrePanel.add(nameLabel);
		usrePanel.setBackground(new Color(179, 235, 255));

		JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel subjectLabel = new JLabel(course.getCourseID().toUpperCase() + " " + course.getCourseName());
		subjectPanel.add(subjectLabel);
		subjectLabel.setIcon(new ImageIcon(this.getClass().getResource("/criterionIcon.png")));
		subjectPanel.add(subjectLabel);
		subjectPanel.setBackground(new Color(179, 235, 255));

		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		topPanel.add(usrePanel);
		topPanel.add(subjectPanel);
		topPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		this.add(topPanel, BorderLayout.NORTH);

		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem logOut = new JMenuItem("Log out");
		JMenuItem back = new JMenuItem("Back");
	
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try {
					dispose();
					SettingGradFrame set = new SettingGradFrame(member, course, new File("emailStudent.txt"));
					set.setGrade();
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
			}
		});
		JMenuItem profile = new JMenuItem("Profile");
		bar.add(menu);
		menu.add(profile);
		menu.addSeparator();
		menu.add(back);
		menu.addSeparator();
		menu.add(logOut);
		setJMenuBar(bar);
		profile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ProfileMember(member);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		logOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				try {
					new LoginForm();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	public void showDownPage() throws IOException {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton sendEmailButton = new JButton("Send Email");

		sendEmailButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		sendEmailButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new ProgressBarFrame(member,course,file);
			}
		});
		JButton convertToExelButton = new JButton("Convert File To Exel Format");
		convertToExelButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		convertToExelButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				new CastToExcelFileFrame(member, course);
			}

		});
		panel.add(sendEmailButton);
		panel.add(convertToExelButton);
		add(panel, BorderLayout.SOUTH);

	}
	public void showGradeStudent()
	{
		for (int i = 0; i < studentList.getSize(); i++) 
		{
			table.setValueAt(studentList.getIndex(i).getEmail(), i, 2);
			table.setValueAt(studentList.getIndex(i).getGrad(), i, 4);
		}
	}
	
	


}

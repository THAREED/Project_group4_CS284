package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.Course;
import model.Member;
import model.StudentList;

public class FillScoresFrame extends JFrame 
{
	private Member member;
	private Course course;
	private StudentList studentList;
	private Object[][] data;
	private JTable table;
	public FillScoresFrame(Member member, Course course) throws IOException 
	{
		this.member = member;
		this.course = course;
		studentList = new StudentList(member, course);
		this.setTitle("Fill Score.");
		showTopPage();
		if (member.isImportClassList()) 
		{
			File file = new File(member.getUsername() + "_" + course.getCourseName() + "List.txt");
			if (file.exists()) 
			{
				studentList.setStudentShowInFill(file);
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				String[] columnNames = { "Number", "Student ID", "Assignment Full Score",
						"Assignment Accumulated Scores", "Midterm Full Score", "Midterm Accumulated Scores",
						"Final Full Score", "Final Accumulated Scores" };
				data = new Object[studentList.getSize()][8];
				for (int i = 0; i < studentList.getSize(); i++) 
				{
					data[i][0] = studentList.getIndex(i).getNumber();
					data[i][1] = studentList.getIndex(i).getId();
					data[i][2] = studentList.getIndex(i).getAssFull();
					data[i][3] = studentList.getIndex(i).getAssAcc();
					data[i][4] = studentList.getIndex(i).getMidFull();
					data[i][5] = studentList.getIndex(i).getMidAcc();
					data[i][6] = studentList.getIndex(i).getFinalFull();
					data[i][7] = studentList.getIndex(i).getFinalAcc();
				}
				table = new JTable(data, columnNames);
				JScrollPane tableScroll = new JScrollPane ( table );
				panel.add(table.getTableHeader(), BorderLayout.PAGE_START);
				panel.add(tableScroll);
				table.setSelectionBackground(new Color(255, 255, 204));
				table.setGridColor(new Color(179, 235, 255));
				this.add(panel);
				this.setVisible(true);
				this.setLocationRelativeTo(null);
			}
			else
			{
				studentList.loadList();
			}
		}
		else
		{
			studentList.loadList();
		}
		pack();
	}
	
	public void showTopPage() throws IOException 
	{		
		JPanel usrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel(member.getName());
		nameLabel.setIcon(new ImageIcon(this.getClass().getResource("/userIcon.png")));
		usrePanel.add(nameLabel);
		usrePanel.setBackground(new Color(179, 235, 255));
		
		JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel subjectLabel = new JLabel(course.getCourseID().toUpperCase()+" "+course.getCourseName());
		subjectPanel.add(subjectLabel);
		subjectLabel.setIcon(new ImageIcon(this.getClass().getResource("/criterionIcon.png")));
		subjectPanel.add(subjectLabel);
		subjectPanel.setBackground(new Color(179, 235, 255));
		
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		topPanel.add(usrePanel);
		topPanel.add(subjectPanel);
		topPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		add(topPanel, BorderLayout.NORTH);
		
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem logOut = new JMenuItem("Log out");
		JMenuItem back = new JMenuItem("Back");
		JMenuItem importFile = new JMenuItem("Import file student list (.csv)");
		importFile.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				StudentList list = new StudentList(member, course);	
				if(list.loadList())
				{
					list.setStudentList();
				}	
				
			}
		});
		
		back.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				try 
				{
					new DetailFrame(member, course);
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}				
			}
		});
		JMenuItem profile = new JMenuItem("Profile");
		bar.add(menu);
		menu.add(profile);
		menu.addSeparator();
		menu.add(importFile);
		menu.addSeparator();
		menu.add(back);
		menu.addSeparator();
		menu.add(logOut);
		setJMenuBar(bar);	
		profile.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try {
					new ProfileMember(member);
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		});
		logOut.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				try 
				{
					new LoginForm();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});	
		
	}

}

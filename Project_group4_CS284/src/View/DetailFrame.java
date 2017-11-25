package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.Course;
import model.Member;
import model.StudentList;

public class DetailFrame extends JFrame 
{
	private DetailPanel detail;
	private String subject;
	private Member member;
	private Course course;
	public DetailFrame(Member member , Course course) throws IOException 
	{
		this.member = member;
		this.subject = course.getCourseID()+" "+course.getCourseName();		
		this.course = course;
	
		showTopPage();
		detail = new DetailPanel(member, course);
		this.add(detail, BorderLayout.CENTER);
		this.setTitle("Course Detail");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();	
		this.setLocationRelativeTo(null);
	}
	public void showTopPage() throws IOException 
	{		
		JPanel usrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel(member.getName());
		nameLabel.setIcon(new ImageIcon(this.getClass().getResource("/userIcon.png")));
		usrePanel.add(nameLabel);
		usrePanel.setBackground(new Color(179, 235, 255));
		
		JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel subjectLabel = new JLabel(subject);
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
					new CourseFrame(member);
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
				try 
				{
					new ProfileMember(member);
				} 
				catch (IOException e1) 
				{
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

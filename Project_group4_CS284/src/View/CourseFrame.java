package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import Controller.CourseController;
import model.Course;
import model.CourseList;
import model.Member;
import model.MemberList;

public class CourseFrame extends JFrame 
{
	private JPanel mini;
	private JList<String> subject;
	private String[] mySubject;
	private CourseList courseList;
	private Member member;
	private CourseController controller;

	public CourseFrame(Member member) throws IOException
	{
		this.member = member;		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
				
		mini = new JPanel();
		mini.setBorder(new TitledBorder("Select Courses"));
		mini.setLayout(new BorderLayout());
		
		showSubjectList();
		subjectChose();
		showTopPage();
		
		main.add(mini, BorderLayout.CENTER);
		add(main);
		setSize(450, 450);
		setTitle("Grading System");
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void showTopPage() 
	{
		JPanel usrePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel nameLabel = new JLabel(member.getName());
		nameLabel.setHorizontalAlignment (SwingConstants.RIGHT);
		nameLabel.setIcon(new ImageIcon(this.getClass().getResource("/userIcon.png")));
		usrePanel.add(nameLabel);
		usrePanel.setBackground(new Color(179, 235, 255));
		usrePanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		add(usrePanel, BorderLayout.NORTH);
		
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem profile = new JMenuItem("Profile");
		JMenuItem logOut = new JMenuItem("Log out");
		bar.add(menu);
		menu.add(profile);
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
		
	public void subjectChose() 
	{
		subject.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if (e.getClickCount() == 1 || e.getClickCount() == KeyEvent.VK_ENTER) 
				{
					try 
					{
						controller = new CourseController(subject.getSelectedValue());
						Course course = controller.getCourseSelect();
						new DetailFrame(member , course);
					} 
					catch (IOException ex) 
					{
						JOptionPane.showMessageDialog(null, ex.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(this.getClass().getResource("/exceptionIcon.png")));
					}
					dispose();
				}
			}
		});
	}
	Course course;
	public void showSubjectList() throws IOException 
	{
		courseList = new CourseList();
		mySubject = new String[member.getSizeSubject()];
		for (int i = 0; i < member.getSizeSubject(); i++) 
		{
			for(int j = 0 ; j<courseList.getSize() ; j++ )
			{
				if(member.getSubjectInIndex(i).equalsIgnoreCase(courseList.getCourse(j).getCourseID()))
				{
					mySubject[i] = courseList.getCourse(j).getCourseID().toUpperCase()+" "+courseList.getCourse(j).getCourseName();
					course = courseList.getCourse(j);
				}
			}
		}
		subject = new JList<String>(mySubject);
		mini.add(subject);
	}

}

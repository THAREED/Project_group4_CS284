package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import model.Course;
import model.Member;
import model.Student;
import model.StudentList;

public class GradingGroupFrame extends JFrame{
	
	StudentList studentList;
	Student student;
	Member member;
	Course course;
	double count = 0;
	String sumGrade[];
	double resultGrade,resultGrade2;
	double sum,avg,sd,min=0,max=0;
	JPanel main,p3,p2,p1,pscore, pbutton;
	JLabel a, b, bb, c, cc, d, dd, f;
	double a2,bb2,b2,cc2,c2,dd2,d2,f2;
	JLabel[] arrPoint;
	private JTextField aTxt, bTxt, bbTxt, cTxt, ccTxt, dTxt, ddTxt, fTxt;
	JButton calculatebtn;
	
	public GradingGroupFrame(Member member, Course course, File file) throws IOException{
		this.member = member;
		this.course = course;
		this.student = student;
		studentList = new StudentList(member, course);
		studentList.setStudentShowInFill(file);
		
		main = new JPanel();
		main.setLayout(new BorderLayout());
		main.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Grading Group"));
		GroupGrading();
		setValueGrade();
		setScoreGrad();
		setTxtEditableFalse();
		setPoint();
		
		pscore = new JPanel();
		pscore.add(p1);
		pscore.add(p2);
		pscore.add(p3);
		pscore.add(new JLabel(" "));
		
		pbutton = new JPanel();
		calculatebtn = new JButton("Calculate");
		calculatebtn.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		calculatebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getActionCommand().equals("Calculate")) {
					setTxtEditableFalse();
					sumGrade = new String[studentList.getSize()];
					for (int i = 0; i < studentList.getSize(); i++) {
						if (studentList.getIndex(i).getTotalScore() >= bb2 ) {
							sumGrade[i] = studentList.getIndex(i).getId() + " A";
						} else if (studentList.getIndex(i).getTotalScore() >= b2) {
							sumGrade[i] = studentList.getIndex(i).getId() + " B+";
						} else if (studentList.getIndex(i).getTotalScore() >= cc2) {
							sumGrade[i] = studentList.getIndex(i).getId() + " B";
						} else if (studentList.getIndex(i).getTotalScore() >= c2) {
							sumGrade[i] = studentList.getIndex(i).getId() + " C+";
						} else if (studentList.getIndex(i).getTotalScore() >= dd2) {
							sumGrade[i] = studentList.getIndex(i).getId() + " C";
						} else if (studentList.getIndex(i).getTotalScore() >= d2) {
							sumGrade[i] = studentList.getIndex(i).getId() + " D+";
						} else if (studentList.getIndex(i).getTotalScore() >= f2) {
							sumGrade[i] = studentList.getIndex(i).getId() + " D";
						} else {
							sumGrade[i] = studentList.getIndex(i).getId() + " F";
						}
					}
					saveGrade();
					
				}
				dispose();
				try {
					new EmailFrame(member, course);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		pbutton.add(calculatebtn);
		
		main.add(new NormalDistributionDemo("",avg).createChartPanel(), BorderLayout.NORTH);
		main.add(pscore, BorderLayout.CENTER);
		main.add(pbutton, BorderLayout.SOUTH);

		showTopPage();
	       
		this.add(main, BorderLayout.CENTER);
		this.setTitle("Setting Grading Group");
		this.setSize(450, 350);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		this.setVisible(true);
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
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					new FillScoresFrame(member, course);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
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
	
	public void setValueGrade() {
		p1 = new JPanel();
		p1.setLayout(new GridLayout(0, 1));
		a = new JLabel("A");
		b = new JLabel("  B+");
		bb = new JLabel("B");
		c = new JLabel("  C+");
		cc = new JLabel("C");
		d = new JLabel("  D+");
		dd = new JLabel("D");
		f = new JLabel(" F ");

		a.setPreferredSize(new Dimension(100, 20));
		b.setPreferredSize(new Dimension(100, 20));
		bb.setPreferredSize(new Dimension(100, 20));
		c.setPreferredSize(new Dimension(100, 20));
		cc.setPreferredSize(new Dimension(100, 20));
		d.setPreferredSize(new Dimension(100, 20));
		dd.setPreferredSize(new Dimension(100, 20));
		f.setPreferredSize(new Dimension(100, 20));

		a.setHorizontalAlignment(SwingConstants.CENTER);
		b.setHorizontalAlignment(SwingConstants.CENTER);
		bb.setHorizontalAlignment(SwingConstants.CENTER);
		c.setHorizontalAlignment(SwingConstants.CENTER);
		cc.setHorizontalAlignment(SwingConstants.CENTER);
		d.setHorizontalAlignment(SwingConstants.CENTER);
		dd.setHorizontalAlignment(SwingConstants.CENTER);
		f.setHorizontalAlignment(SwingConstants.CENTER);

		p1.add(a);
		p1.add(b);
		p1.add(bb);
		p1.add(c);
		p1.add(cc);
		p1.add(d);
		p1.add(dd);
		p1.add(f);
	}
	
	public void setScoreGrad() {
		
		p2 = new JPanel();
		p2.setLayout(new GridLayout(0, 1));
		aTxt = new JTextField(bb2+"-100");
		bbTxt = new JTextField(b2+"-"+(bb2-1));
		bTxt = new JTextField(cc2+"-" +(b2-1));
		ccTxt = new JTextField(c2+"-" +(cc2-1));
		cTxt = new JTextField(dd2+"-" +(c2-1));
		ddTxt = new JTextField(d2+"-" +(dd2-1));
		dTxt = new JTextField(f2+"-" +(d2-1));
		fTxt = new JTextField("00.0-" +(f2-1));
		
		aTxt.setPreferredSize(new Dimension(100, 20));
		bbTxt.setPreferredSize(new Dimension(100, 20));
		bTxt.setPreferredSize(new Dimension(100, 20));
		ccTxt.setPreferredSize(new Dimension(100, 20));
		cTxt.setPreferredSize(new Dimension(100, 20));
		ddTxt.setPreferredSize(new Dimension(100, 20));
		dTxt.setPreferredSize(new Dimension(100, 20));
		fTxt.setPreferredSize(new Dimension(100, 20));

		aTxt.setHorizontalAlignment(SwingConstants.CENTER);
		bbTxt.setHorizontalAlignment(SwingConstants.CENTER);
		bTxt.setHorizontalAlignment(SwingConstants.CENTER);
		ccTxt.setHorizontalAlignment(SwingConstants.CENTER);
		cTxt.setHorizontalAlignment(SwingConstants.CENTER);
		ddTxt.setHorizontalAlignment(SwingConstants.CENTER);
		dTxt.setHorizontalAlignment(SwingConstants.CENTER);
		fTxt.setHorizontalAlignment(SwingConstants.CENTER);

		p2.add(aTxt);
		p2.add(bbTxt);
		p2.add(bTxt);
		p2.add(ccTxt);
		p2.add(cTxt);
		p2.add(ddTxt);
		p2.add(dTxt);
		p2.add(fTxt);

	}
	
	public void GroupGrading(){
		double sumpow[] = new double[studentList.getSize()];
		
		for(int i = 0; i < studentList.getSize();i++){
			resultGrade += studentList.getIndex(i).getTotalScore();
			count++;
		}
		
		avg = Math.round(resultGrade/count);
		
		for(int i = 0; i < studentList.getSize();i++){ 
			sumpow[i] = Math.pow((studentList.getIndex(i).getTotalScore()-avg), 2);
		}
		
		for(int i = 0; i < studentList.getSize();i++){ 
			sum += sumpow[i];
		}
		double z = Math.sqrt(sum/count);
		double newsum = Math.round(z);
		sd = newsum/2;
		a2 = (avg+(sd*3.5)); bb2 = (avg+(sd*2.5)); b2 = (avg+(sd*1.5)); cc2 = (avg+(sd*0.5));
		c2 = (avg-(sd*0.5)); dd2 = (avg-(sd*1.5)); d2 = (avg-(sd*2.5)); f2 = (avg-(sd*3.5));
	}
	
	public void setPoint() {
		p3 = new JPanel();
		p3.setLayout(new GridLayout(0, 1));
		arrPoint = new JLabel[8];
		for (int i = 0; i < 8; i++) {
			arrPoint[i] = new JLabel("Points");
			arrPoint[i].setPreferredSize(new Dimension(100, 20));
			arrPoint[i].setHorizontalAlignment(SwingConstants.CENTER);
			p3.add(arrPoint[i]);
		}

	}
	
	public void setTxtEditableFalse() {
		aTxt.setEditable(false);
		bTxt.setEditable(false);
		bbTxt.setEditable(false);
		cTxt.setEditable(false);
		ccTxt.setEditable(false);
		dTxt.setEditable(false);
		ddTxt.setEditable(false);
		fTxt.setEditable(false);
	}

	public void setTxtEditableTrue() {
		aTxt.setEditable(true);
		bTxt.setEditable(true);
		bbTxt.setEditable(true);
		cTxt.setEditable(true);
		ccTxt.setEditable(true);
		dTxt.setEditable(true);
		ddTxt.setEditable(true);
		fTxt.setEditable(true);
	}
	
	public boolean saveGrade() {
		try {
			FileWriter fileWriter = new FileWriter(member.getUsername() + "_" + course.getCourseName() + "TotalGradeList.txt", false);
			PrintWriter writer = new PrintWriter(fileWriter);
			for (int i = 0; i < studentList.getSize(); i++) {
				System.out.println(sumGrade[i]);
				writer.println(sumGrade[i]);
			}
			writer.close();
			fileWriter.close();

			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon("exceptionIcon.png"));
			return false;
		}
	}

}

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

import model.Course;
import model.Member;
import model.Student;
import model.StudentList;

public class SettingGradFrame extends JFrame implements ActionListener {
	private JPanel mainPanel, pscore, pbutton;
	private JPanel p1, p2, p3;
	private JLabel a, b, bb, c, cc, d, dd, f;
	private JLabel[] arrPoint;
	private JButton calculatebtn;
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem item1, item2;
	private JTextField aTxt, bTxt, bbTxt, cTxt, ccTxt, dTxt, ddTxt, fTxt;
	StudentList studentList;
	Student student;
	private Member member;
	private Course course;
	private String sumGrade[];

	public SettingGradFrame(Member member, Course course, File file) throws IOException {
		this.member = member;
		this.course = course;
		studentList = new StudentList(member, course);
		studentList.setStudentShowInFill(file);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Setting Grade"));

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
		pbutton.add(calculatebtn);
		calculatebtn.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		calculatebtn.addActionListener(this);

		mainPanel.add(pscore, BorderLayout.NORTH);
		mainPanel.add(pbutton, BorderLayout.SOUTH);

		setMenuBar();

		showTopPage();
		this.add(mainPanel, BorderLayout.CENTER);
		this.setTitle("Setting Grade");
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Calculate")) {
			setGrade();
		}
		try {
			dispose();
			new EmailFrame(member, course);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void setGrade() {
		String[] a = aTxt.getText().split("-");
		String[] bb = bTxt.getText().split("-");
		String[] b = bbTxt.getText().split("-");
		String[] cc = ccTxt.getText().split("-");
		String[] c = cTxt.getText().split("-");
		String[] dd = ddTxt.getText().split("-");
		String[] d = dTxt.getText().split("-");
		String[] f = fTxt.getText().split("-");
		dispose();
		setTxtEditableFalse();
		sumGrade = new String[studentList.getSize()];
		for (int i = 0; i < studentList.getSize(); i++) {
			if (studentList.getIndex(i).getTotalScore() >= Integer.valueOf(a[0])) {
				sumGrade[i] = studentList.getIndex(i).getId() + " A";
			} else if (studentList.getIndex(i).getTotalScore() >= Integer.valueOf(bb[0])) {
				sumGrade[i] = studentList.getIndex(i).getId() + " B+";
			} else if (studentList.getIndex(i).getTotalScore() >= Integer.valueOf(b[0])) {
				sumGrade[i] = studentList.getIndex(i).getId() + " B";
			} else if (studentList.getIndex(i).getTotalScore() >= Integer.valueOf(cc[0])) {
				sumGrade[i] = studentList.getIndex(i).getId() + " C+";
			} else if (studentList.getIndex(i).getTotalScore() >= Integer.valueOf(c[0])) {
				sumGrade[i] = studentList.getIndex(i).getId() + " C";
			} else if (studentList.getIndex(i).getTotalScore() >= Integer.valueOf(dd[0])) {
				sumGrade[i] = studentList.getIndex(i).getId() + " D+";
			} else if (studentList.getIndex(i).getTotalScore() >= Integer.valueOf(d[0])) {
				sumGrade[i] = studentList.getIndex(i).getId() + " D";
			} else if (studentList.getIndex(i).getTotalScore() >= Integer.valueOf(f[0])) {
				sumGrade[i] = studentList.getIndex(i).getId() + " F";
			}
		}
		saveGrade();
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
		aTxt = new JTextField("80-100");
		bbTxt = new JTextField("75-79");
		bTxt = new JTextField("70-74");
		ccTxt = new JTextField("65-69");
		cTxt = new JTextField("60-64");
		ddTxt = new JTextField("55-59");
		dTxt = new JTextField("50-54");
		fTxt = new JTextField("0-49");

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

	public void setMenuBar() {
		menubar = new JMenuBar();
		setJMenuBar(menubar);

		menu = new JMenu("Menu");
		menubar.add(menu);
		item1 = new JMenuItem("Criterion-Referenced Evaluation");
		item2 = new JMenuItem("Norm-Referenced Evaluation");
		menu.add(item1);
		menu.add(item2);

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
			FileWriter fileWriter = new FileWriter(
					new File(member.getUsername() + "_" + course.getCourseName() + "TotalGrade" + "List.txt"), false);
			PrintWriter writer = new PrintWriter(fileWriter);
			for (int i = 0; i < studentList.getSize(); i++) {
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

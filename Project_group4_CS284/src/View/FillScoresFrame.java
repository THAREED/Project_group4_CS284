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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controller.FillScoresController;
import model.Course;
import model.Member;
import model.StudentList;

public class FillScoresFrame extends JFrame {
	private Member member;
	private Course course;
	private StudentList studentList;
	private File file;
	private FillScoresController fillScoresController;

	public FillScoresFrame(Member member, Course course) throws IOException {
		this.member = member;
		this.course = course;
		studentList = new StudentList(member, course);
		this.setTitle("Fill Score.");
		showTopPage();
		if (member.isImportClassList()) {
			file = new File(member.getUsername() + "_" + course.getCourseName() + "List.txt");
			if (file.exists()) {
				fillScoresController = new FillScoresController(member, course, file);
				this.add(fillScoresController);
				this.setVisible(true);
				this.setLocationRelativeTo(null);
			} else {
				if (studentList.loadList()) {
					studentList.setStudentList();
				}
			}
		} else {
			if (studentList.loadList()) {
				studentList.setStudentList();
			}
		}
		showDownPage();
		setLocation(100, 100);
		pack();
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
		add(topPanel, BorderLayout.NORTH);

		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem logOut = new JMenuItem("Log out");
		JMenuItem back = new JMenuItem("Back");

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
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
		JButton settingButton = new JButton("Setting");

		settingButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		settingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getActionCommand().equals("Setting")) {
					fillScoresController.setEnabled(true);
					settingButton.setText("Save");

				}
				fillScoresController.fillScore();
				if (e.getActionCommand().equals("Save")) {
					if (fillScoresController.isUpdate()) {
						studentList.saveList();
					}
					fillScoresController.setEnabled(false);
					settingButton.setText("Setting");
				}
				if (e.getActionCommand().equals("Norm-Referenced Evaluation")) {
					try {
						dispose();
						File file = new File(member.getUsername() + "_" + course.getCourseName() + "List.txt");
						new GradingGroupFrame(member, course, file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		JButton calculateButton = new JButton("Calculate");
		calculateButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		calculateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Calculate")) {
					if (settingButton.getActionCommand().equals("Save")) {
						JOptionPane.showMessageDialog(null, "       Save update score.", "Message",
								JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon(this.getClass().getResource("/gearsIcon.png")));
						fillScoresController.fillScore();
						studentList.saveList();
						fillScoresController.setEnabled(false);
						settingButton.setText("Setting");
					} else {
						settingButton.setEnabled(true);
						fillScoresController.setEnabled(false);
						settingButton.setText("Norm-Referenced Evaluation");
						calculateButton.setText("Criterion-Referenced Evaluation");
					}
				}

				fillScoresController.calculate();
				fillScoresController.updateTable();
				if (e.getActionCommand().equals("Criterion-Referenced Evaluation")) {
					try {
						dispose();
						new SettingGradFrame(member, course, file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		panel.add(settingButton);
		panel.add(calculateButton);
		add(panel, BorderLayout.SOUTH);
	}

}

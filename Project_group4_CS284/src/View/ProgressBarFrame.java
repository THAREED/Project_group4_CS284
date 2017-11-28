package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import Controller.OnSuccessListener;
import Controller.SendMailController;
import Controller.SendMailDataController;
import model.Course;
import model.Member;
import model.StudentList;

public class ProgressBarFrame extends JFrame {
	private Random ran;
	private Member member;
	private Course course;
	private StudentList studentList;
	private JLabel logo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			}
		});
	}

	public ProgressBarFrame(Member member, Course course, File file) {
		setBounds(100, 100, 362, 249);
		setLocation(500, 350);
		setTitle("Sending mail");
		getContentPane().setLayout(null);
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						} catch (Exception ex) {
						}
						new BackgroundWorker().execute();
					}
				});
			}
		});
		btnStart.setBounds(128, 113, 89, 23);
		getContentPane().add(btnStart);
		setVisible(true);
		this.course = course;
		this.member = member;
		studentList = new StudentList(member, course);
		file = new File(member.getUsername() + "_" + course.getCourseName() + "List.txt");
		studentList.setStudentShowInFill(file);
		studentList.getGradeStudentFromFile();
		studentList.getEmailStudentFromFile();
		ran = new Random();
	}

	public class BackgroundWorker extends SwingWorker<Void, Void> {
		private JProgressBar pb;
		private JDialog dialog;

		public BackgroundWorker() {
			addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if ("progress".equalsIgnoreCase(evt.getPropertyName())) {
						if (dialog == null) {
							dialog = new JDialog();
							dialog.setTitle("Processing");
							dialog.setLayout(new GridBagLayout());
							dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
							GridBagConstraints gbc = new GridBagConstraints();
							gbc.insets = new Insets(2, 2, 2, 2);
							gbc.weightx = 1;
							gbc.gridy = 0;
							dialog.add(new JLabel("Processing..."), gbc);
							pb = new JProgressBar();
							pb.setStringPainted(true);
							gbc.gridy = 1;
							dialog.add(pb, gbc);
							dialog.setSize(new Dimension(300, 100));
							dialog.setLocationRelativeTo(null);
							dialog.setModal(true);
							JDialog.setDefaultLookAndFeelDecorated(true);
							dialog.setVisible(true);
						}
						pb.setValue(getProgress());
					}
				}
			});
		}

		@Override
		protected void done() {
			if (dialog != null) {
				dialog.dispose();
				ImageIcon icon = new ImageIcon(this.getClass().getResource("/email.gif"));
				icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
				logo = new JLabel(icon);
				logo.setText("Send mail is successfully!!!");
				logo.setHorizontalTextPosition(SwingConstants.CENTER);
				logo.setVerticalTextPosition(SwingConstants.BOTTOM);
				logo.setFont(new Font(Font.SERIF, Font.BOLD, 30));
				logo.setForeground(new Color(255, 132, 100));
				JFrame f = new JFrame();
				JPanel panel = new JPanel(new BorderLayout());
				JPanel logoPanel = new JPanel();
				panel.setVisible(true);
				panel.setBackground(Color.WHITE);
				panel.setBorder(BorderFactory.createLoweredBevelBorder());
				JPanel btnPanel = new JPanel();
				JButton doneBtn = new JButton("Done");
				doneBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						f.dispose();
					}
				});
				btnPanel.add(doneBtn);
				logoPanel.add(logo);
				panel.add(logoPanel, BorderLayout.CENTER);
				panel.add(btnPanel, BorderLayout.SOUTH);

				f.add(panel, BorderLayout.CENTER);
				f.setVisible(true);
				f.setLocation(500, 350);
				f.pack();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		}

		@Override
		protected Void doInBackground() throws Exception {
			int percent = ran.nextInt(98) + 1;
			for (int index = 0; index <= 100; index++) {
				setProgress(index);
				if (index == percent) {
					sendMail();
				} else if (index == 100) {
					sendDataMail();
				}
				Thread.sleep(50);
			}
			return null;
		}

		private void sendDataMail() {
			SendMailDataController mailData = new SendMailDataController(member, course);
			mailData.initSender("tncpbee12@gmail.com", "0823224985");
			mailData.send("tncpbee12@gmail.com", "beeza_8464@hotmail.com", "Summit Grade",
					"text File of Students of " + course.getCourseName(), new OnSuccessListener() {
						@Override
						public void OnSuccess() {
						}
					});

		}
	}

	public void sendMail() {
		SendMailController mail = new SendMailController();
		mail.initSender("tncpbee12@gmail.com", "0823224985");
		mail.send("tncpbee12@gmail.com", "beeza_8464@hotmail.com", "Summit Grade",
				"your grade is " + studentList.getIndex(15).getGrad() + " of course " + course.getCourseName(),
				new OnSuccessListener() {
					@Override
					public void OnSuccess() {
					}
				});

		/*
		 * for (int i = 0; i < studentList.getSize(); i++) {
		 * 
		 * mail.send("tncpbee12@gmail.com", studentList.getIndex(i).getEmail(),
		 * "Summit Grade", "your grade is " + studentList.getIndex(i).getGrad(),
		 * new OnSuccessListener() {
		 * 
		 * @Override public void OnSuccess() {
		 * 
		 * } }); }
		 */

	}

}
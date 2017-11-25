package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import Controller.OnSuccessListener;
import Controller.SendMailController;
import model.Course;
import model.Member;
import model.StudentList;

public class ProgressBarFrame extends JFrame {

	Random ran = new Random();
	Member member;
	Course course;
	StudentList studentList;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				 //ProgressBarFrame frame = new ProgressBarFrame();

				// frame.setVisible(true);

			}

		});

	}

	public ProgressBarFrame(Member member, Course course,File file) {

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
				ImageIcon icon = new ImageIcon(this.getClass().getResource("/email.png"));
				JOptionPane.showMessageDialog(null, "Send mail is successfully!!!", null ,JOptionPane.INFORMATION_MESSAGE,icon);
			//	System.exit(0);
			}

		}

		@Override

		protected Void doInBackground() throws Exception {
			int percent = ran.nextInt(98) + 1;

			for (int index = 0; index < 100; index++) {
				setProgress(index);
				if (index == percent) {
					sendMail();
				}

				Thread.sleep(50);

			}

			return null;
		}
	}

	public void sendMail() {
		
		SendMailController mail = new SendMailController();
		mail.initSender("tncpbee12@gmail.com", "0823224985");
		mail.send("tncpbee12@gmail.com", "beeza_8464@hotmail.com", "Summit Grade",
				"your grade is "+studentList.getIndex(15).getGrad(), new OnSuccessListener() {

					@Override
					public void OnSuccess() {

					}
				});
		/*for (int i = 0; i < studentList.getSize(); i++) {

			mail.send("tncpbee12@gmail.com", studentList.getIndex(i).getEmail(), "Summit Grade",
					"your grade is " + studentList.getIndex(i).getGrad(), new OnSuccessListener() {

						@Override
						public void OnSuccess() {

						}
					});
		}*/

	}

}

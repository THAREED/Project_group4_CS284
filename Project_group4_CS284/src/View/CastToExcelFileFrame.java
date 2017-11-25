package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Controller.CastToExcelFile;
import model.Course;
import model.Member;

public class CastToExcelFileFrame extends JFrame implements ActionListener
{
	private ImageIcon icon;
	private JLabel logo, fileNameLabel, xlsLabel;
	private JTextField fileNameTxt;
	private JButton convertButton;
	private String fileNamePattern = "^[A-Za-z0-9_]+$";
	private Member member;
	private Course course;
	public CastToExcelFileFrame(Member member, Course course) 
	{
		this.member = member;
		this.course = course;
		showLogoPage();
		showControlPage();
		
		setTitle("Covert File To Excel Format.");
		pack();
		setResizable(false);
		setLocationRelativeTo(null);  
		setVisible(true);
	}
	public void showLogoPage()
	{
		icon = new ImageIcon(this.getClass().getResource("/castToExcel.gif"));
		icon.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT);
		logo = new JLabel(icon);
		logo.setText("Convert To Excel Format");
		logo.setHorizontalTextPosition(SwingConstants.CENTER);
		logo.setVerticalTextPosition(SwingConstants.BOTTOM);
		logo.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		logo.setForeground(new Color(0, 128, 128));
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(logo);
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLoweredBevelBorder());
		add(panel, BorderLayout.CENTER);		
	}
	public void showControlPage()
	{
		fileNameLabel = new JLabel("File name: ");
		fileNameTxt = new JTextField(20);
		xlsLabel = new JLabel(".xls");
		convertButton = new JButton("Convert");
		convertButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		convertButton.addActionListener(this);
		fileNameTxt.addActionListener(this);
		
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createBevelBorder(0, Color.WHITE, Color.LIGHT_GRAY));
		panel.setBackground(new Color(179, 235, 255));
		panel.add(fileNameLabel);
		panel.add(fileNameTxt);
		panel.add(xlsLabel);
		panel.add(convertButton);
		
		add(panel, BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String fileName = fileNameTxt.getText();
		if(fileName.matches(fileNamePattern))
		{
			dispose();
			new CastToExcelFile(member, course, fileName);
		}
		else
		{
			fileNameTxt.setText("");
			JOptionPane.showMessageDialog(null, "Please enter the desired file name, such as CS280_SubmitGrade.","Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(this.getClass().getResource("/exceptionIcon.png")));
		}
		
	}

}

package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.DetailController;
import model.Course;
import model.CourseList;
import model.Member;

public class DetailPanel extends JPanel {
	private JButton settingBtn, fillScoreBtn;
	private JLabel fullLabel, accumulatedLabel, assignLabael, midLabel, finalLabel;
	private JTextField assfTxt, midfTxt, finalfTxt, assaTxt, midaTxt, finalaTxt;
	private JPanel panel, panelS, panelG;
	private Course course;
	private DetailController detailController;

	public DetailPanel(Member member, Course course) throws IOException 
	{
		detailController = new DetailController(course);
		this.course = course;
		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.setBorder(BorderFactory.createTitledBorder("Course Detail"));

		JPanel panelBtn = new JPanel();
		settingBtn = new JButton("Setting");
		fillScoreBtn = new JButton("FillScore");
		panelBtn.add(settingBtn);
		panelBtn.add(fillScoreBtn);
		settingBtn.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		fillScoreBtn.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		settingBtn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (e.getActionCommand().equals("Setting")) 
				{
					setTxt(true);
					settingBtn.setText("Save");
				}
				try 
				{
					detailController.setFullScore(assfTxt.getText(), midfTxt.getText(), finalfTxt.getText());
					detailController.setAccumulatedScore(assaTxt.getText(), midaTxt.getText(), finalaTxt.getText());
					if(!detailController.isCheckScoreFull())
					{
						assfTxt.setText(course.getAssFull() + "");
						midfTxt.setText(course.getMidFull() + "");
						finalfTxt.setText(course.getFinalFull() + "");
					}
					if(detailController.isUpdatePieChart())
					{
						panelG.add(new PieChart("",course).createDemoPanel(), BorderLayout.CENTER);
					}
					if(!detailController.isCheckScoreAcc())
					{
						assaTxt.setText(course.getAssAcc() + "");
						midaTxt.setText(course.getMidAcc() + "");
						finalaTxt.setText(course.getFinalAcc() + "");
					}
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				if (e.getActionCommand().equals("Save")) 
				{
					setTxt(false);
					settingBtn.setText("Setting");
				}

			}
		});

		fillScoreBtn.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					new FillScoresFrame(member, course);
				} 
				catch (IOException e1) 
				{
					System.out.println(e1.getMessage());
				}
			}
		});
		panelG = new JPanel();
		panelG.setLayout(new BorderLayout());
		panelG.add(new PieChart("",course).createDemoPanel(), BorderLayout.CENTER);
		panel.add(panelG, BorderLayout.CENTER);

		panelS = new JPanel();
		panelS.setLayout(new GridLayout(4, 0));
		JPanel panelLabel = new JPanel();
		JLabel empty = new JLabel("                              ");
		fullLabel = new JLabel("                                                    Full Score");
		accumulatedLabel = new JLabel("Accumulated Score");

		panelLabel.add(fullLabel);
		panelLabel.add(empty);
		panelLabel.add(accumulatedLabel);
		panelS.add(panelLabel);
		setAssign();
		setMidterm();
		setfinalterm();
		setTxt(false);
		panel.add(panelS);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(450, 450));
		this.add(panel, BorderLayout.CENTER);
		this.add(panelBtn, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void setAssign() 
	{
		JPanel assPanel = new JPanel();
		JLabel assLabel = new JLabel("                        ");
		assignLabael = new JLabel("Assignment                 ");
		assfTxt = new JTextField();
		assfTxt.setPreferredSize(new Dimension(100, 20));
		assfTxt.setText(course.getAssFull() + "");
		assaTxt = new JTextField();
		assaTxt.setPreferredSize(new Dimension(100, 20));
		assaTxt.setText(course.getAssAcc() + "");
		assPanel.add(assignLabael);
		assPanel.add(assfTxt);
		assPanel.add(assLabel);
		assPanel.add(assaTxt);
		panelS.add(assPanel);
	}

	public void setMidterm() 
	{
		JPanel midPanel = new JPanel();
		JLabel midtermLabel = new JLabel("                         ");
		midLabel = new JLabel("Midterm                        ");
		midfTxt = new JTextField();
		midfTxt.setPreferredSize(new Dimension(100, 20));
		midfTxt.setText(course.getMidFull() + "");
		midaTxt = new JTextField();
		midaTxt.setPreferredSize(new Dimension(100, 20));
		midaTxt.setText(course.getMidAcc() + "");

		midPanel.add(midLabel);
		midPanel.add(midfTxt);
		midPanel.add(midtermLabel);
		midPanel.add(midaTxt);
		panelS.add(midPanel);
	}

	public void setfinalterm() 
	{
		JPanel finalPanel = new JPanel();
		JLabel fiLabel = new JLabel("                         ");
		finalLabel = new JLabel("Final                               ");
		finalfTxt = new JTextField();
		finalfTxt.setPreferredSize(new Dimension(100, 20));
		finalfTxt.setText(course.getFinalFull() + "");
		finalaTxt = new JTextField();
		finalaTxt.setPreferredSize(new Dimension(100, 20));
		finalaTxt.setText(course.getFinalAcc() + "");

		finalPanel.add(finalLabel);
		finalPanel.add(finalfTxt);
		finalPanel.add(fiLabel);
		finalPanel.add(finalaTxt);
		panelS.add(finalPanel);
	}
	public void setTxt(boolean check) 
	{
		if (check) 
		{
			assfTxt.setEditable(true);
			assaTxt.setEditable(true);
			midfTxt.setEditable(true);
			midaTxt.setEditable(true);
			finalfTxt.setEditable(true);
			finalaTxt.setEditable(true);
		} 
		else 
		{
			assfTxt.setEditable(false);
			assaTxt.setEditable(false);
			midfTxt.setEditable(false);
			midaTxt.setEditable(false);
			finalfTxt.setEditable(false);
			finalaTxt.setEditable(false);
		}
	}

}






package View;

import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

import model.Course;

public class PieChart extends ApplicationFrame 
{
	private static Course course;
	public PieChart(String title,Course course) 
	{
		super(title);
		this.course = course;
	}
	public static PieDataset createDataset()
	{
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Assignment", new Double(course.getAssAcc()));
		dataset.setValue("Midterm", new Double(course.getMidAcc()));
		dataset.setValue("Final", new Double(course.getFinalAcc()));
		return dataset;
	}
	public static JFreeChart createChart(PieDataset dataset) 
	{
		JFreeChart chart = ChartFactory.createPieChart("Score Ratio", dataset, true, true, false);
		chart.getPlot().setBackgroundPaint( new Color(102,51,0) );	
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionPaint("Assignment", new Color(255,137,176));
		plot.setSectionPaint("Midterm", new Color(102,255,204));
		plot.setSectionPaint("Final", new Color(255,204,102));
		return chart;
	}
	public static JPanel createDemoPanel() 
	{
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
}

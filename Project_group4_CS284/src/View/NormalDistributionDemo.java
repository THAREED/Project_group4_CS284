package View;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

public class NormalDistributionDemo extends ApplicationFrame 
{
	private static double avg;
	public NormalDistributionDemo(final String title,double avg) 
	{
		super(title);
        this.avg = avg;
	}
	public static Function2D function2d()
	{
		Function2D normal = new NormalDistributionFunction2D(avg, 15.0);
		return normal;
	}
	public static XYDataset Dataset()
	{
		XYDataset dataset = DatasetUtilities.sampleFunction2D(function2d(), 0.0, 100.0, 1000, "Normal" );
		return dataset;
	}
	public static JFreeChart freeChart()
	{
		JFreeChart chart = ChartFactory.createXYLineChart("Grading Group","","", Dataset(),PlotOrientation.VERTICAL,false,false,false);
		return chart;
	}
	public static JPanel createChartPanel() 
	{
		JFreeChart chartPanel = freeChart(); 
		return new ChartPanel(chartPanel);
	}

}

package uncertaintyCalculator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import java.awt.Dimension;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {
	
	private ArrayList<Double> xAxis;
	private ArrayList<Double> yAxis;
	private Double maxX;
	private Double maxY;
	private double RSquared;
	private double gradient;
	private double constant;


	// The current width and height of this panel
    private Dimension size;
    
	private static final long serialVersionUID = 3657711311262092318L;

	public GraphPanel() {

		xAxis = new ArrayList<Double>();
		yAxis = new ArrayList<Double>();
		
	}
	
	public GraphPanel(ArrayList<Double> xAxis ,ArrayList<Double> yAxis){

		xAxis = new ArrayList<Double>();
		yAxis = new ArrayList<Double>();
		
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}
	
	public GraphPanel(LayoutManager arg0) {
		super(arg0);
		xAxis = new ArrayList<Double>();
		yAxis = new ArrayList<Double>();
		
	}
	
	public GraphPanel(boolean arg0) {
		super(arg0);
		xAxis = new ArrayList<Double>();
		yAxis = new ArrayList<Double>();
	}
	
	public GraphPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		xAxis = new ArrayList<Double>();
		yAxis = new ArrayList<Double>();
	}
	
	public void clearGraph()
    {
    		size = getSize();
            super.getGraphics().clearRect(0, 0, size.width, size.height);
    }

	public void setXAxis(ArrayList<Double> xAxis){
		this.xAxis = xAxis;
		findMaximums();
		repaint();
	}
	
	public void setYAxis(ArrayList<Double> yAxis){
		this.yAxis = yAxis;
		findMaximums();
		repaint();
	}
	
	public void findMaximums(){
		maxX = 0D;
		int i = 0;
		while(i<xAxis.size()){
			if(xAxis.get(i)>maxX){
				maxX = xAxis.get(i);
			}
			i++;
		}
		
		maxY = 0D;
		 i = 0;
		while(i<yAxis.size()){
			if(yAxis.get(i)>maxY){
				maxY = yAxis.get(i);
			}
			i++;
		}
		
	}
	
	public static double[] listToArray(List<Double> arr){   
	    double[] result = new double[arr.size()];
	    int i = 0;
	    for(Double d : arr) {
	        result[i++] = d.doubleValue();
	    }
	    return result;
	}
	
	public double getRSquared(){
		return RSquared;
	}
	
	public double getGradient(){
		return gradient;
	}
	
	public double getConstant(){
		return constant;
	}
	
	public int getNumberOfPoints(){
		return xAxis.size();
	}
	
	
	public void calculateData(){
		//int MAXN = 1000;
        int n = 0;
        double[] x = listToArray(xAxis);
        double[] y = listToArray(yAxis);

        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        while(n<x.length){
//            x[n] = StdIn.readDouble();
//            y[n] = StdIn.readDouble();
            sumx  += x[n];
            sumx2 += x[n] * x[n];
            sumy  += y[n];
            n++;
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        gradient = xybar / xxbar;
        constant = ybar - gradient * xbar;

        // print results
        System.out.println("y   = " + gradient + " * x + " + constant);

        // analyze results
        int df = n - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = gradient*x[i] + constant;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }
        RSquared   = ssr / yybar;
        double svar  = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar/n + xbar*xbar*svar1;
        System.out.println("R^2                 = " + RSquared);
        System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
        svar0 = svar * sumx2 / (n * xxbar);
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));

        System.out.println("SSTO = " + yybar);
        System.out.println("SSE  = " + rss);
        System.out.println("SSR  = " + ssr);
	}
	
    public void paintComponent(Graphics g)
    {
        Dimension size = getSize();
        g.clearRect(0, 0, size.width, size.height);
        if(xAxis.isEmpty()&&yAxis.isEmpty()){
        	return;
        }else{
        	int markSize = size.width/30;
        	double xMultiplier = 0D;
        	double yMultiplier = 0D;
        	int i = 0;
        	xMultiplier = (size.getWidth()/1.2)/maxX;
        	yMultiplier = (size.getHeight()/1.2)/maxY;
        	int xValue = 0;
        	int yValue = 0;
        	while(i<xAxis.size()){
        		xValue = (int)(xMultiplier*xAxis.get(i));
        		yValue = (int)(yMultiplier*yAxis.get(i));;
        		g.setColor(Color.black);
        		g.fillOval(xValue-markSize,(size.height-yValue)+markSize,markSize,markSize);
        		i++;
        	}
        
//        	This code here draws a line of best fit in
        	
//        	g.setColor(Color.MAGENTA);
//        	
//        	if(constant >= 0.0){
//        		int x1 = 0;
//        		int y1 = (int)(constant*xMultiplier);
//        		int x2 = (int)(maxX*xMultiplier);
//        		int y2 = (int)(((gradient*x2) + (int)constant*yMultiplier)*(size.getHeight()/size.getWidth()));
//        		g.drawLine(x1,size.height-y1,x2,size.height-y2);
//        	}
//        	
//        	if(constant < 0.0){
//        	      
//        	}
        }
    }
	
	
	
	
	
	
	
}

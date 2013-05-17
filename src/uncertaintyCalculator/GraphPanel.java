package uncertaintyCalculator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.ArrayList;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.andrew.util.Maths;

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

	static public GraphPanel getCopy(GraphPanel graph){
		return new GraphPanel(graph.getXAxis(), graph.getYAxis());
	}

	public void clearGraph()
	{
		size = getSize();
		super.getGraphics().clearRect(0, 0, size.width, size.height);
	}

	public ArrayList<Double> getXAxis(){
		return xAxis;
	}

	public ArrayList<Double> getYAxis(){
		return yAxis;
	}

	public void setXAxis(ArrayList<Double> xAxis){
		this.xAxis = xAxis;
		calculateData();
		findMaximums();
		repaint();
	}

	public void setYAxis(ArrayList<Double> yAxis){
		this.yAxis = yAxis;
		calculateData();
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
		if((xAxis.size() == 0)||(yAxis.size() == 0)){
			return;
		}
		//int MAXN = 1000;
		int n = 0;
		double[] x = Maths.listToArray(xAxis);
		double[] y = Maths.listToArray(yAxis);

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
		//        System.out.println("y   = " + gradient + " * x + " + constant);

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
		//        System.out.println("R^2                 = " + RSquared);
		//        System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
		//        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
		//        svar0 = svar * sumx2 / (n * xxbar);
		//        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));

		//        System.out.println("SSTO = " + yybar);
		//        System.out.println("SSE  = " + rss);
		//        System.out.println("SSR  = " + ssr);
	}
	
	public BufferedImage makeBufferedImage(Dimension imageSize){
		BufferedImage outputImage = new BufferedImage(imageSize.width,imageSize.height,BufferedImage.TYPE_INT_RGB);
		Graphics g = outputImage.getGraphics();
		
		int xOffset = imageSize.width/10;
		int yOffset = imageSize.height/10;
		
		paintPoints(g,xOffset,yOffset);
		paintGraphAxes(g,xOffset,yOffset);
		drawLineOfBestFit(g, xOffset, yOffset);
		drawValues(g,xOffset,yOffset);
		
		g.dispose();
		return outputImage;
	}

	public void paintComponent(Graphics g)
	{
		size = getSize();
		g.clearRect(0, 0, size.width, size.height);
		int xOffset = size.width/10;
		int yOffset = size.height/10;

		if(xAxis.isEmpty()&&yAxis.isEmpty()){
			return;
		}else{
			paintPoints(g,xOffset,yOffset);
			paintGraphAxes(g,xOffset,yOffset);
			drawLineOfBestFit(g, xOffset, yOffset);
			drawValues(g,xOffset,yOffset);
			g.dispose();
		}    
	}

	private void drawValues(Graphics g, int xOffset, int yOffset){

		double xMultiplier = (size.getWidth()/1.4)/maxX;
		double yMultiplier = (size.getHeight()/1.4)/maxY;

		double yValue = 0;
		double xValue = 0;
		g.setColor(Color.black);
		int i = 0;
		if(yOffset>12){
			while(i<xAxis.size()){

				xValue = Maths.roundToSignificantFigures(xAxis.get(i), 2);
				g.drawString(Double.toString(xValue),(int)((xAxis.get(i)*xMultiplier))+xOffset, size.height-((yOffset/2)-5));


				i++;
			}
		}
		i = 0;
		if(xOffset>20){
			while(i<yAxis.size()){
				yValue = Maths.roundToSignificantFigures(yAxis.get(i), 2);
				g.drawString(Double.toString(yValue),2, size.height-((int)((yAxis.get(i)*yMultiplier))+yOffset));


				i++;
			}
		}
	}

	private void paintPoints(Graphics g,int xOffset, int yOffset){
		int markSize = 10;
		double xMultiplier = 0D;
		double yMultiplier = 0D;
		int i = 0;
		xMultiplier = (size.getWidth()/1.4)/maxX;
		yMultiplier = (size.getHeight()/1.4)/maxY;
		int xValue = 0;
		int yValue = 0;

		while(i<xAxis.size()){
			xValue = (int)(xMultiplier*xAxis.get(i));
			yValue = (int)(yMultiplier*yAxis.get(i));;
			g.setColor(Color.black);
			g.fillOval(xValue+xOffset,(size.height-(yValue+yOffset))-markSize,markSize,markSize);
			i++;
		}

	}

	private void drawLineOfBestFit(Graphics g, int xOffset, int yOffset){

		g.setColor(new Color(255,20,31));
		double xMultiplier = (size.getWidth()/1.4)/maxX;
		double yMultiplier = (size.getHeight()/1.4)/maxY;
		double effectiveGradient = gradient*(maxX/maxY);
		if(constant >= 0.0){
			int x1 = xOffset;
			int y1 = yOffset + (int)(constant*yMultiplier);
			int x2 = size.width;
			int y2 =(int)(((effectiveGradient*x2)+(constant*yMultiplier))/(size.getWidth()/size.getHeight())) ;
			g.drawLine(x1,size.height-y1,x2,size.height-y2);
		}

		if(constant < 0.0){
			int x1 = (int)((-constant/gradient)*xMultiplier)+ xOffset;
			int y1 = 0;
			int x2 = size.width;
			int y2 =(int)(((effectiveGradient*x2)+(constant*yMultiplier))/(size.getWidth()/size.getHeight())) ;
			g.drawLine(x1,size.height-(y1+yOffset),x2,size.height-y2);
		}
	}

	private void paintGraphAxes(Graphics g, int xOffset, int yOffset){

		g.setColor(Color.black);
		g.drawLine(xOffset, size.height - yOffset,xOffset, 0);
		g.drawLine(xOffset, size.height - yOffset, size.width, size.height - yOffset);

	}






}

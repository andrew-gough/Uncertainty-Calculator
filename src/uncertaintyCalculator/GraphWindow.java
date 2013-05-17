package uncertaintyCalculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphWindow implements WindowListener{
	
	private GraphPanel graph;
	private JFrame frame;

	public GraphWindow() {
		graph = new GraphPanel();
		makeFrame();
	}
	
	private void makeFrame(){
		frame = new JFrame("Graph");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this);
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.add(graph, BorderLayout.CENTER);
		
		frame.pack();

	}
	
	public void setSize(Dimension d){
		graph.setPreferredSize(d);
		frame.pack();

	}
	
	public void clearGraph(){
		graph.clearGraph();
	}
	
	public void updateXAxis(ArrayList<Double> input){
		graph.setXAxis(input);
	}
	
	public void updateYAxis(ArrayList<Double> input){
		graph.setYAxis(input);
	}
	
	public void showFrame(){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
	
		frame.setVisible(true);
	}
	
	public void hideFrame(){
		frame.setVisible(false);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		hideFrame();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		hideFrame();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

package uncertaintyCalculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GraphWindow implements WindowListener{

	private GraphPanel graph;
	private JFrame frame;

	public GraphWindow() {
		graph = new GraphPanel();
		makeFrame();
		makeMenuBar(frame);
	}

	private void makeFrame(){
		frame = new JFrame("Graph");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setIconImage(new ImageIcon(GraphWindow.class.getResource("Icon.png")).getImage());
		frame.addWindowListener(this);
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.add(graph, BorderLayout.CENTER);

		frame.pack();
	}

	private void makeMenuBar(JFrame frame)
	{
		final int SHORTCUT_MASK =
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);

		JMenu menu;
		JMenuItem item;

		// create the File menu
		menu = new JMenu("File");
		menubar.add(menu);

		item = new JMenuItem("Save Graph");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				graph.saveCurrentGraph();
			}
		});
		menu.add(item);
		
		// create the labels menu
		menu = new JMenu("Labels");
		menubar.add(menu);
		
		item = new JMenuItem("Set X Axis Label");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) { 
				String input = JOptionPane.showInputDialog("X-Axis Label:");
				try{
					graph.setXAxisLabel(input);

				}catch(NullPointerException e){
					System.out.println("User Exited the Data Entry");
					return;
				}
				
			}
		});
		menu.add(item);
		
		item = new JMenuItem("Set Y Axis Label");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) { 
				String input = JOptionPane.showInputDialog("Y-Axis Label:");
				try{
					graph.setYAxisLabel(input);

				}catch(NullPointerException e){
					System.out.println("User Exited the Data Entry");
					return;
				}
				
			}
		});
		menu.add(item);
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

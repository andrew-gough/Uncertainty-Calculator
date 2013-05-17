package uncertaintyCalculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class DisplayingWindow implements ItemListener,MouseListener{
	private UncertaintyCalculator dataStorage;
	private JFrame frame;
	private JLabel xAxisLabel;
	private JLabel yAxisLabel;
	private JComboBox<String> xAxisCombo;
	private JComboBox<String> yAxisCombo;
	private JTextPane xAxisTextPane;
	private JScrollPane xAxisScrollPane;
	private JTextPane yAxisTextPane;
	private JScrollPane yAxisScrollPane;
	private JTextPane unCertaintyTextPane;
	private JScrollPane unCertaintyScrollPane;
	private GraphPanel graphDisplayer;
	private GraphWindow graphWindow;

	public DisplayingWindow(UncertaintyCalculator input){
		dataStorage = input;
		graphWindow = new GraphWindow();
	}

	public void makeFrame(){
		frame = new JFrame("Data Displayer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = (JPanel)frame.getContentPane();
		//		contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
		contentPane.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		
		xAxisLabel = new JLabel("X Axis:");
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(xAxisLabel,c);
		
		yAxisLabel = new JLabel("Y Axis:");
		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		contentPane.add(yAxisLabel,c);
		
		xAxisCombo = new JComboBox<String>();

		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		contentPane.add(xAxisCombo,c);

		yAxisCombo = new JComboBox<String>();

		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 1;
		contentPane.add(yAxisCombo,c);

		populateComboBoxes();

		xAxisTextPane = new JTextPane();
		xAxisTextPane.setText("xAxisTextPane");

		xAxisScrollPane = new JScrollPane(xAxisTextPane);
		xAxisScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.83;
		c.gridx = 0;
		c.gridy = 2;
		contentPane.add(xAxisScrollPane,c);

		yAxisTextPane = new JTextPane();
		yAxisTextPane.setText("yAxisTextPane");

		yAxisScrollPane = new JScrollPane(yAxisTextPane);
		yAxisScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.83;
		c.gridx = 1;
		c.gridy = 2;
		contentPane.add(yAxisScrollPane,c);

		unCertaintyTextPane = new JTextPane();
		unCertaintyTextPane.setText("unCertaintyTextPane");

		unCertaintyScrollPane = new JScrollPane(unCertaintyTextPane);
		unCertaintyScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		c.gridwidth = 3;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.33;
		c.gridx = 0;
		c.gridy = 3;
		contentPane.add(unCertaintyScrollPane,c);

		graphDisplayer = new GraphPanel();
		graphDisplayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		graphDisplayer.addMouseListener(this);
		c.gridwidth = 1;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.83;
		c.gridx = 2;
		c.gridy = 0;
		contentPane.add(graphDisplayer,c);

		xAxisCombo.addItemListener(this);
		yAxisCombo.addItemListener(this);

		graphDisplayer.setXAxis(dataStorage.getAverageDataColumn(Integer.parseInt((xAxisCombo.getSelectedItem().toString().substring(7)))-1));
		graphWindow.updateXAxis(dataStorage.getAverageDataColumn(Integer.parseInt((xAxisCombo.getSelectedItem().toString().substring(7)))-1));
		
		graphDisplayer.setYAxis(dataStorage.getAverageDataColumn(Integer.parseInt((yAxisCombo.getSelectedItem().toString().substring(7)))-1));
		graphWindow.updateYAxis(dataStorage.getAverageDataColumn(Integer.parseInt((yAxisCombo.getSelectedItem().toString().substring(7)))-1));
		
		xAxisTextPane.setText(arrayListIntoString(dataStorage.getAverageDataColumn(Integer.parseInt((xAxisCombo.getSelectedItem().toString().substring(7)))-1)));
		yAxisTextPane.setText(arrayListIntoString(dataStorage.getAverageDataColumn(Integer.parseInt((xAxisCombo.getSelectedItem().toString().substring(7)))-1)));

		updateUncertaintyField();
		
		frame.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
		frame.setVisible(true);
	}

	public void populateComboBoxes(){
		int i = 1;
		while(i<=dataStorage.getColumnsOfData()){
			xAxisCombo.addItem("Column " + i);
			yAxisCombo.addItem("Column " + i);
			i++;
		}
	}

	public String arrayListIntoString(ArrayList<Double> input){
		StringBuilder s = new StringBuilder();
		int i = 0;
		while(i<input.size()){
			s.append(input.get(i));
			s.append("\n");
			i++;
		}
		return s.toString().trim();

	}
	
	private void updateUncertaintyField(){
		//graphDisplayer.calculateData();
		StringBuilder s = new StringBuilder();
		s.append("Line of Best Fit : ");
		s.append("y = ");
		s.append(graphDisplayer.getGradient()); 
		s.append("x ");
		if(graphDisplayer.getConstant()>0){
			s.append("+");
			s.append(" " + graphDisplayer.getConstant());
		}
		if(graphDisplayer.getConstant()<0){
			s.append("-");
			s.append(" " + Math.abs(graphDisplayer.getConstant()));
		}
		s.append("\n");
		
		s.append("RSquared = ");
		s.append(graphDisplayer.getRSquared()); 
		s.append("\n");
		
		s.append("\n");
		s.append("Uncertainty derived from the RSquared Value:");
		s.append("\n");
		
		s.append("\n");
		s.append("Uncertainty in gradient = ±");
		s.append(100D*Math.pow(((1-graphDisplayer.getRSquared())/(graphDisplayer.getNumberOfPoints()-2)), 0.5)); 
		s.append("%");
		s.append("\n");
		
		s.append("\n");
		s.append("Uncertainties derived from the Random Uncertainty Value:");
		s.append("\n");
		
		s.append("\n");
		s.append("Uncertainty in X = ±");
		s.append(dataStorage.getColumnUnCert(Integer.parseInt((xAxisCombo.getSelectedItem().toString().substring(7)))-1));
		s.append("%");
		s.append("\n");
		
		s.append("Uncertainty in Y = ±");
		s.append(dataStorage.getColumnUnCert(Integer.parseInt((yAxisCombo.getSelectedItem().toString().substring(7)))-1));
		s.append("%");

		
		unCertaintyTextPane.setText(s.toString());
		
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {

		if(ie.getSource()==xAxisCombo){
			xAxisTextPane.setText(arrayListIntoString(dataStorage.getAverageDataColumn(Integer.parseInt((xAxisCombo.getSelectedItem().toString().substring(7)))-1)));
			graphDisplayer.setXAxis(dataStorage.getAverageDataColumn(Integer.parseInt((xAxisCombo.getSelectedItem().toString().substring(7)))-1));
			graphWindow.updateXAxis(dataStorage.getAverageDataColumn(Integer.parseInt((xAxisCombo.getSelectedItem().toString().substring(7)))-1));
		}

		if(ie.getSource()==yAxisCombo){
			yAxisTextPane.setText(arrayListIntoString(dataStorage.getAverageDataColumn(Integer.parseInt((yAxisCombo.getSelectedItem().toString().substring(7)))-1)));
			graphDisplayer.setYAxis(dataStorage.getAverageDataColumn(Integer.parseInt((yAxisCombo.getSelectedItem().toString().substring(7)))-1));
			graphWindow.updateYAxis(dataStorage.getAverageDataColumn(Integer.parseInt((yAxisCombo.getSelectedItem().toString().substring(7)))-1));
		}
		updateUncertaintyField();
	}


	public void mouseClicked(MouseEvent arg0) {

		
	}


	public void mouseEntered(MouseEvent arg0) {
		
		
	}


	public void mouseExited(MouseEvent arg0) {
		
		
	}


	public void mousePressed(MouseEvent arg0) {

		
	}


	public void mouseReleased(MouseEvent arg0) {
		graphWindow.setSize(graphDisplayer.getSize());
		graphWindow.showFrame();
	}



}

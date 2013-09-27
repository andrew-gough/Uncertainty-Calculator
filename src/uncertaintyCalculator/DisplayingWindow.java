package uncertaintyCalculator;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.andrew.util.Maths;

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
		frame.setIconImage(new ImageIcon(DisplayingWindow.class.getResource("Icon.png")).getImage());
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
		graphDisplayer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

		makeMenuBar(frame);

		updateUncertaintyField();

		frame.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
		yAxisCombo.setSelectedItem("Column 2");
		frame.setVisible(true);
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

		item = new JMenuItem("Export Data to Text File");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 

				JFileChooser chooser = new JFileChooser();
				int option = chooser.showSaveDialog(null);
				if (option == JFileChooser.APPROVE_OPTION)
				{
					File outputFile = chooser.getSelectedFile();



					if(!chooser.getSelectedFile().getAbsolutePath().endsWith(".txt")){
						outputFile = new File(chooser.getSelectedFile() + ".txt");
					}
					if(!dataStorage.outputDataToFile(outputFile)){
						JOptionPane.showMessageDialog(null, "Save Failed.","Warning",JOptionPane.WARNING_MESSAGE);
					}


				}
			}
		});
		menu.add(item);

		item = new JMenuItem("Export Computational File");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 

				JFileChooser chooser = new JFileChooser();
				int option = chooser.showSaveDialog(null);
				if (option == JFileChooser.APPROVE_OPTION)
				{
					File outputFile = chooser.getSelectedFile();



					if(!chooser.getSelectedFile().getAbsolutePath().endsWith(".txt")){
						outputFile = new File(chooser.getSelectedFile() + ".txt");
					}
					if(!dataStorage.outputProcessToFile(outputFile)||!graphDisplayer.outputProcessToFile(outputFile)){
						JOptionPane.showMessageDialog(null, "Save Failed.","Warning",JOptionPane.WARNING_MESSAGE);
					}
					try{
					Desktop.getDesktop().open(outputFile);
					}catch(Exception Ex){
					System.out.println("Exception for some reason?");
					}


				}
			}
		});
		menu.add(item);
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
		s.append(Maths.roundToSignificantFigures(graphDisplayer.getGradient(),4)); 
		s.append("x ");
		if(graphDisplayer.getConstant()>0){
			s.append("+");
			s.append(" " + graphDisplayer.getConstant());
		}
		if(graphDisplayer.getConstant()<0){
			s.append("-");
			s.append(" " + Math.abs(Maths.roundToSignificantFigures(graphDisplayer.getConstant(),4)));
		}
		s.append("\n");

		s.append("RSquared = ");
		s.append(Maths.truncate(graphDisplayer.getRSquared(),4)); 
		s.append("\n");

		s.append("\n");
		s.append("Uncertainty derived from the RSquared Value:");
		s.append("\n");

		s.append("\n");
		s.append("Uncertainty in gradient : ");
		Double gradUncert = 100D*Math.pow(((1-graphDisplayer.getRSquared())/(graphDisplayer.getNumberOfPoints()-2)), 0.5);
		if (!(gradUncert.isNaN()||(gradUncert==0.0))) {
			s.append("±");
			s.append(Maths.roundToSignificantFigures(100D*Math.pow(((1-graphDisplayer.getRSquared())/(graphDisplayer.getNumberOfPoints()-2)), 0.5),4)); 
			s.append("%");
		}else{
			s.append("There is no uncertainty in the gradient");	
		}

		s.append("\n");

		s.append("\n");
		s.append("Uncertainties derived from the Random Uncertainty Value:");
		s.append("\n");

		s.append("\n");
		s.append("Uncertainty in X Axis= ±");
		s.append(Maths.roundToSignificantFigures(dataStorage.getColumnUnCert(Integer.parseInt((xAxisCombo.getSelectedItem().toString().substring(7)))-1),4));
		s.append("%");
		s.append("\n");

		s.append("Uncertainty in Y Axis= ±");
		s.append(Maths.roundToSignificantFigures(dataStorage.getColumnUnCert(Integer.parseInt((yAxisCombo.getSelectedItem().toString().substring(7)))-1),4));
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

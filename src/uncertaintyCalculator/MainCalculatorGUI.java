package uncertaintyCalculator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class MainCalculatorGUI implements ActionListener {

	private UncertaintyCalculator calculator;
	private ArrayList<InputFrame> dataSets;
	private int numberOfSets;
	private JFrame frame;
	private JButton exitButton;
	private JButton startButton;
	private JButton infoButton;


	public MainCalculatorGUI(){
		calculator = new UncertaintyCalculator();
		dataSets = new ArrayList<InputFrame>();
	}


	private void startDataEntry() {
		calculator.clearData();
		dataSets.clear();
		System.out.println("Data Entry Started!");
		String input = JOptionPane.showInputDialog("How Many Sets of Data Are you Going to inport?");
		try{
			Integer.parseInt(input);
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(frame, "Make Sure You Typed In a Valid Number","Number Error",JOptionPane.WARNING_MESSAGE);
			return;
		}
		numberOfSets = (int) Integer.parseInt(input);
		dataSets.add(new InputFrame(this,0));
		dataSets.get(0).makeFrame();
	}

	private void checkFrameSize(){
		frame.pack();
		if((Toolkit.getDefaultToolkit().getScreenSize().height<frame.getHeight())||(Toolkit.getDefaultToolkit().getScreenSize().width<frame.getWidth())){
			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
	}

	public void makeFrame(){
		frame = new JFrame("Uncertainty Calculator");
		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
		//makeMenu(frame);
		JPanel buttonPanel = new JPanel(new GridLayout(1,3));

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		infoButton = new JButton("Info");
		infoButton.addActionListener(this);
		startButton = new JButton("Start");
		startButton.addActionListener(this);

		buttonPanel.add(exitButton);
		buttonPanel.add(infoButton);
		buttonPanel.add(startButton);
		contentPane.add(buttonPanel);

		frame.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
		frame.setVisible(true);
	}

	private void makeMenu(JFrame inputFrame){
		final int SHORTCUT_MASK =
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

		JMenuBar menubar = new JMenuBar();
		inputFrame.setJMenuBar(menubar);

		JMenu menu;
		JMenuItem item;

		// create the File menu
		menu = new JMenu("Actions");
		menubar.add(menu);

		//		item = new JMenuItem("Exit");
		//		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
		//		item.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) { 
		//				System.out.println("It worked!");
		//			}
		//		});
		//		menu.add(item);

		item = new JMenuItem("Exit");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				System.exit(0);
			}
		});
		menu.add(item);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource() == exitButton){
			System.exit(0);
		}

		if(ae.getSource() == infoButton){
			System.out.println("Info Should Output");
		}

		if(ae.getSource() == startButton){
			startDataEntry();
		}

		if(ae.getActionCommand().equals("DataIn")){
			System.out.println("Data Input!");
			calculator.addDataSet(dataSets.get(dataSets.size()-1).getInput());
			if (dataSets.size()<numberOfSets){
				dataSets.add(new InputFrame(this,dataSets.size()));
				dataSets.get(dataSets.size()-1).makeFrame();
			}else{
				System.out.println("Data is all in!");
				calculator.calculateAverageDataSet();
				calculator.printAllDataSetsToConsole();
				calculator.printAverageDataSetToConsole();
			}
			
		}

		if(ae.getActionCommand().equals("DataExit")){
			System.out.println("Data Exit!");
		}

	}



}

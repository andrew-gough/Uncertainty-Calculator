package uncertaintyCalculator;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainCalculatorGUI implements ActionListener {

	private DisplayingWindow display;
	private UncertaintyCalculator calculator;
	private ArrayList<InputFrame> dataSets;
	private int numberOfSets;
	private JFrame frame;
	private JLabel infoLbl;
	private JButton exitButton;
	private JButton startButton;
	private JButton infoButton;


	public MainCalculatorGUI(){
		calculator = new UncertaintyCalculator();
		display = new DisplayingWindow(calculator);
		dataSets = new ArrayList<InputFrame>();
	}


	private void startDataEntry() {
		calculator.clearData();
		dataSets.clear();
		String input = JOptionPane.showInputDialog("How Many Sets of Data Are you Going to input?");
		try{
			Integer.parseInt(input.trim());
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(frame, "Make Sure You Typed In a Valid Number","Number Error",JOptionPane.WARNING_MESSAGE);
			return;
		}catch(NullPointerException e){
			return;
		}
		frame.setVisible(false);
		numberOfSets = (int) Integer.parseInt(input.trim());
		dataSets.add(new InputFrame(this,0));
		dataSets.get(0).makeFrame();
	}

//	private void checkFrameSize(){
//		frame.pack();
//		if((Toolkit.getDefaultToolkit().getScreenSize().height<frame.getHeight())||(Toolkit.getDefaultToolkit().getScreenSize().width<frame.getWidth())){
//			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
//		}
//	}

	public void makeFrame(){
		frame = new JFrame("Uncertainty Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon(MainCalculatorGUI.class.getResource("Icon.png")).getImage());
		JPanel contentPane = (JPanel) frame.getContentPane();
//		contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
		//makeMenu(frame);
		contentPane.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		infoLbl = new JLabel("Uncertainty Calculator");
		infoLbl.setHorizontalAlignment( SwingConstants.CENTER );
		infoLbl.setVerticalAlignment( SwingConstants.CENTER );
		
		
		c.gridwidth = 3;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(infoLbl,c);
		

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.33;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		contentPane.add(exitButton,c);
		
		
		infoButton = new JButton("Info");
		infoButton.addActionListener(this);
		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.33;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 1;
		contentPane.add(infoButton,c);
		
		
		startButton = new JButton("Start");
		startButton.addActionListener(this);

		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.33;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 1;
		contentPane.add(startButton,c);
		


//		frame.pack();

		frame.setSize(270, 160);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
		frame.setVisible(true);
	}

//	private void makeMenu(JFrame inputFrame){
//		final int SHORTCUT_MASK =
//				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
//
//		JMenuBar menubar = new JMenuBar();
//		inputFrame.setJMenuBar(menubar);
//
//		JMenu menu;
//		JMenuItem item;
//
//		// create the File menu
//		menu = new JMenu("Actions");
//		menubar.add(menu);
//
//		//		item = new JMenuItem("Exit");
//		//		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
//		//		item.addActionListener(new ActionListener() {
//		//			public void actionPerformed(ActionEvent e) { 
//		//				System.out.println("It worked!");
//		//			}
//		//		});
//		//		menu.add(item);
//
//		item = new JMenuItem("Exit");
//		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
//		item.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) { 
//				System.exit(0);
//			}
//		});
//		menu.add(item);
//
//	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource() == exitButton){
			System.exit(0);
		}

		if(ae.getSource() == infoButton){
			try{
			URI url = new URI("https://github.com/andrew-gough/Uncertainty-Calculator/blob/master/README.md");
			Desktop.getDesktop().browse(url);
			}catch(Exception e){
				System.out.println("Exception for some reason?");
			}
			System.out.println("Info Should Output");
		}

		if(ae.getSource() == startButton){
			startDataEntry();
		}

		if(ae.getActionCommand().equals("DataIn")){
			if(!calculator.addDataSet(dataSets.get(dataSets.size()-1).getInput())){
				frame.setVisible(true);
				return;
			}
			if (dataSets.size()<numberOfSets){
				dataSets.add(new InputFrame(this,dataSets.size()));
				dataSets.get(dataSets.size()-1).makeFrame();
			}else{
				calculator.calculateAverageDataSet();
				calculator.fillMaxMinusMin();
				//calculator.printAllDataSetsToConsole();
				//calculator.printAverageDataSetToConsole();
				frame.dispose();
				display.makeFrame();
				
			}
			
		}

		if(ae.getActionCommand().equals("DataExit")){
			frame.setVisible(true);
		}

	}



}

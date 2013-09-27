package uncertaintyCalculator;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputFrame implements DocumentListener,ActionListener,WindowListener {

	private ActionListener owner;

	private String dataInput;
	private int dataSet = 0;

	private JFrame frame;
	private JTextArea inputField;
	private JButton exitButton;
	private JButton inputButton;
	private JScrollPane scrollPane;

	public InputFrame(ActionListener owner,int dataSetNumber){
		this.owner = owner;
		dataSet = dataSetNumber;
	}
	
	public String getInput(){
		return dataInput;
	}

	public void makeFrame(){
		frame = new JFrame("Data Set " + (dataSet+1));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this);
		frame.setIconImage(new ImageIcon(InputFrame.class.getResource("Icon.png")).getImage());
		JPanel contentPane = (JPanel)frame.getContentPane();
//		contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
		contentPane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		inputField = new JTextArea();
		inputField.getDocument().addDocumentListener(this);
		
		scrollPane = new JScrollPane(inputField);
		scrollPane.setPreferredSize(new Dimension(200,100));
		
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(scrollPane,c);

		
		inputButton = new JButton("Input");
		inputButton.addActionListener(this);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		contentPane.add(inputButton,c);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		contentPane.add(exitButton,c);



		frame.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
		frame.setVisible(true);


	}

	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource() == inputButton){
			dataInput = inputField.getText();
			this.owner.actionPerformed(new java.awt.event.ActionEvent(this,0,"DataIn"));
			frame.dispose();
		}

		if(ae.getSource() == exitButton){
			this.owner.actionPerformed(new java.awt.event.ActionEvent(this,0,"DataExit"));
			frame.dispose();
		}

	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		frame.repaint();
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		frame.repaint();
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		frame.repaint();
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.owner.actionPerformed(new java.awt.event.ActionEvent(this,0,"DataExit"));
		frame.dispose();
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

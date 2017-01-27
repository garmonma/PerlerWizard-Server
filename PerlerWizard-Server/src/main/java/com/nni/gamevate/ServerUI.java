package com.nni.gamevate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class ServerUI extends JFrame {

	private static final long serialVersionUID = -2894569217834232347L;

	private PerlerWizardServer serverInstance;

	private BorderLayout mainLayout;
	private JPanel westPanel, centralPanel, northPanel, southPanel, eastPanel;
	private JTextArea textDisplay;
	
	private ArrayList<String> serverInfo;

	public ServerUI() {
		super("Perler Wizard Server UI");

		setSize(800, 600);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				ServerUI.this.setVisible(false);
				ServerUI.this.dispose();
			}
		});

		mainLayout = new BorderLayout();
		setLayout(mainLayout);

		init();
		
		initWestPanel();
		initEastPanel();
		initCentralPanel();
		initNorthPanel();
		initSouthPanel();
		
		updateTextDisplay();
	}
	
	private void init() {
		serverInstance = new PerlerWizardServer();
		serverInfo = new ArrayList<String>();
	}

	private void initSouthPanel() {
		southPanel = new JPanel();
		
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
		
		JButton startButton = new JButton("Start Server");
		JButton stopButton = new JButton("Stop Server");
		
		startButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				serverInstance.start();
				serverInfo.add("Server Started");	
				serverInfo.add(serverInstance.getCharacters());
				updateTextDisplay();
			}
		});
		
		stopButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				serverInstance.stop();
				serverInfo.add("Server Stopped");	
				updateTextDisplay();
			}
		});
		
		southPanel.add(startButton);
		southPanel.add(stopButton);
		add(southPanel, BorderLayout.SOUTH);
		
	}

	private void initNorthPanel() {
		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));
		
	}

	private void initCentralPanel() {
		centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
		
		textDisplay = new JTextArea();
		textDisplay.setSize(500, 400);
		textDisplay.setBackground(Color.BLACK);
		textDisplay.setForeground(Color.GREEN);
		textDisplay.setEditable(false);
		
		serverInfo.add("Welcome to the Perler Wizard Server");
		centralPanel.add(textDisplay);
		add(centralPanel, BorderLayout.CENTER);	
	}
	
	
	
	private void initEastPanel() {
		eastPanel = new JPanel();
		add(eastPanel, BorderLayout.EAST);
		
	}

	private void initWestPanel() {
		JLabel headerLabel = new JLabel("Connected Clients");
		JList<String> clientList = new JList<String>();
		JScrollPane scrollPane = new JScrollPane(clientList);
		JButton refreshList = new JButton("Refresh Clients");
		
		refreshList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientList.setListData(serverInstance.getConnectedClients());
				clientList.updateUI();
			}
		});
		
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));
		westPanel.add(headerLabel);
		westPanel.add(scrollPane);
		westPanel.add(refreshList);
		add(westPanel, BorderLayout.WEST);	
	}

	private void updateTextDisplay(){
		textDisplay.setText("");
		for(String data: serverInfo){
			textDisplay.append(data + "\n");
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				final ServerUI lg = new ServerUI();
				lg.setVisible(true);
			}
		});
	}

}

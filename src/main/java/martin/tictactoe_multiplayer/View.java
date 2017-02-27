package martin.tictactoe_multiplayer;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.util.Set;

import javax.inject.Inject;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;

public class View {

	@Inject
	private Game game;

	private JFrame frame;

	private JLabel whosTurn;
	private JLabel timeInfo;
	private JLabel connectionLbl;

	private JButton startNewGameBtn;
	private ConnectionBtn connectionBtn;

	/**
	 * Launch the application.
	 */
	public void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 269, 362);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		JPanel panel_1_1 = new ClickBox(0, 0, this);
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_1_2 = new ClickBox(0, 1, this);
		panel_1_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_1_3 = new ClickBox(0, 2, this);
		panel_1_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_2_1 = new ClickBox(1, 0, this);
		panel_2_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_2_2 = new ClickBox(1, 1, this);
		panel_2_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_2_3 = new ClickBox(1, 2, this);
		panel_2_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_3_1 = new ClickBox(2, 0, this);
		panel_3_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_3_2 = new ClickBox(2, 1, this);
		panel_3_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_3_3 = new ClickBox(2, 2, this);
		panel_3_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		startNewGameBtn = new JButton("Start new game");
		startNewGameBtn.setEnabled(false);
		startNewGameBtn.addActionListener((e) -> {
			startNewGameRequest();
		});
		connectionBtn = new ConnectionBtn("Connect");

		connectionBtn.addActionListener((e) -> {
			connectionBtnClicked();
		});
		JLabel lblYourMarkerIs = new JLabel("Your marker is \"X\"");
		
		whosTurn = new JLabel("");
		
		timeInfo = new JLabel("");
		
		connectionLbl = new JLabel("");
		connectionLbl.setFont(new Font("Serif", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_3_1, Alignment.TRAILING, 70, 70, 70)
								.addComponent(panel_2_1, 70, 70, 70)
								.addComponent(panel_1_1, 70, 70, 70))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel_1_2, 70, 70, 70)
								.addComponent(panel_3_2, Alignment.LEADING, 70, 70, 70)
								.addComponent(panel_2_2, Alignment.LEADING, 70, 70, 70))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_1_3, 70, 70, 70)
								.addComponent(panel_2_3, Alignment.TRAILING, 70, 70, 70)
								.addComponent(panel_3_3, 70, 70, 70)))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(startNewGameBtn)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(whosTurn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblYourMarkerIs)))
						.addComponent(timeInfo)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(connectionBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(connectionLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(connectionBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(connectionLbl, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblYourMarkerIs)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(whosTurn))
						.addComponent(startNewGameBtn))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(timeInfo)
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1_1, 70, 70, 70)
						.addComponent(panel_1_2, Alignment.TRAILING, 70, 70, 70)
						.addComponent(panel_1_3, 70, 70, 70))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2_1, 70, 70, 70)
						.addComponent(panel_2_2, 70, 70, 70)
						.addComponent(panel_2_3, Alignment.LEADING, 70, 70, 70))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_3_1, 70, 70, 70)
						.addComponent(panel_3_2, 70, 70, 70)
						.addComponent(panel_3_3, 70, 70, 70))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}

	private void connectionBtnClicked() {
		if (connectionBtn.isInConnectedState()) {
			game.disconnect();
		} else {
			showConnectDialog();
		}
	}

	private void showConnectDialog() {
		JTextField hostField = new JTextField();
		JTextField portField = new JTextField();

		Object[] message = {
			    "Host:", hostField,
			    "Port:", portField
			};

		int option = JOptionPane.showConfirmDialog(null, message, "Connect", JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
		    String host = hostField.getText();
		    String port = portField.getText();
		    connectionLbl.setText("Connecting to: "+host+":"+port);

		    game.connect(host, Integer.valueOf(port));
		}
	}

	public void startNewGameRequest() {
		Object[] options = {"Me","Other Player", "Cancel"};

		int option = JOptionPane.showOptionDialog(null, 
				"Who do you want to go first?", 
				"Who's first?", 
				JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[2]);

		switch (option) {
		case 0:
			game.receiveNewGameRequestFromUI(true);
			break;
		case 1:
			game.receiveNewGameRequestFromUI(false);
			break;
		default:
			return;
		}
	}

	public void startNewGame(boolean imFirst) {
		clearBoard();

		if (imFirst) {
			setMyTurn();
		} else {
			setOtherPlayerTurn();
		}
	}

	public void setOtherPlayerTurn() {
		setTurn("Other player thinks.", Color.BLACK);
	}

	private void setMyTurn() {
		setTurn("Your turn.", Color.RED);
	}

	private void setTurn(String text, Color color) {
		whosTurn.setText(text);
		whosTurn.setForeground(color);
		whosTurn.paintImmediately(whosTurn.getVisibleRect());
		Dimension prefSize = whosTurn.getPreferredSize();
		prefSize.setSize(prefSize.getWidth()+10, prefSize.getHeight());
		whosTurn.setSize(prefSize);		
	}

	private void clearBoard() {
		Set<ClickBox> boxes = ClickBox.getBoxes();
		for (ClickBox box : boxes) {
			box.clearBackground();
			box.updateUI();
		}
	}

	public void notifyTimeOver(boolean otherPlayerTurn) {
		JOptionPane.showMessageDialog(null, "Time's up!");
	}

	public void makeMove(Position pos, Player player) {
		String file = player.getSymbol().getFIle()+".png";
		
		ClickBox box = ClickBox.getBoxForPos(pos.getX(), pos.getY());
		box.putBackground(file);
	}

	public void setTimeLeft(int timeLeft, boolean otherPlayerTurn) {
		String whoHas = otherPlayerTurn ? "Bot has" : "You have";
		String text = whoHas + " " + timeLeft+ 
				" second" + (timeLeft == 1 ? "" : "s") + " to move";

		timeInfo.setText(text);
		timeInfo.paintImmediately(timeInfo.getVisibleRect());
	}

	public void gotWinner(String who) {
		JOptionPane.showMessageDialog(null, who+" Won!");
	}

	public void setConnected(String client) {
		enableStartGameBtn();
		visualiseConnectionInfoLabel(client);
		changeButtonToConnectedState();
	}

	private void changeButtonToConnectedState() {
		connectionBtn.setText("Disconnect");
		connectionBtn.setInConnectedState(true);
	}

	private void changeButtonToDisconnectedState() {
		connectionBtn.setText("Connect");
		connectionBtn.setInConnectedState(false);
	}

	private void visualiseConnectionInfoLabel(String client) {
		connectionLbl.setText("You are now connected with: "+client);
		connectionLbl.setForeground(Color.red);
	}

	private void enableStartGameBtn() {
		startNewGameBtn.setEnabled(true);
	}

	public void setDisconnected() {
		disableStartGameBtn();
		clearConnectionInfoLabel();
		changeButtonToDisconnectedState();
	}

	private void clearConnectionInfoLabel() {
		connectionLbl.setText("");
	}

	private void disableStartGameBtn() {
		startNewGameBtn.setEnabled(false);
	}

	public void receiveStartNewGameRequestFromOtherPlayer(boolean imFirst) {
		showPropositionFromOtherPlayer(imFirst);
	}

	private void showPropositionFromOtherPlayer(boolean imFirst) {
		Object[] options = {"Accept","Decline"};

		int option = JOptionPane.showOptionDialog(null, 
				"Hey, let's play? " + (imFirst ? "You can go first." : "I want to go first."), 
				"Accept?", 
				JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[0]);

		switch (option) {
		case 0:
			game.receiveNewGameResponseFromUI(true);
			break;
		case 1:
			game.receiveNewGameResponseFromUI(false);
			break;
		default:
			return;
		}
	}

	public int retrievePort() {
		String portStr = JOptionPane.showInputDialog(null, "Please enter a port to listen for connection.");

		int port = Integer.valueOf(portStr);

		return port;
	}

	public void notifyConnectionFailed(String host) {
		connectionLbl.setText("Connection to " + host + " failed.");
	}
}
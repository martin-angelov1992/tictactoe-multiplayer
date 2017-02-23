package martin.tictactoe_multiplayer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View {

	@Inject
	private Game game;

	private JFrame frame;

	private JLabel whosTurn;
	private JLabel timeInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frame.setVisible(true);
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
		
		JButton btnStartNewGame = new JButton("Start new game");
		btnStartNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startNewGame();
			}
		});
		
		JLabel lblYourMarkerIs = new JLabel("Your marker is \"X\"");
		
		whosTurn = new JLabel("");
		
		timeInfo = new JLabel("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
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
							.addComponent(btnStartNewGame)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(whosTurn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblYourMarkerIs)))
						.addComponent(timeInfo))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblYourMarkerIs)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(whosTurn))
						.addComponent(btnStartNewGame))
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

	public void startNewGame() {
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
			game.startNewGame(false);
			clearBoard();
			setMyTurn();
			break;
		case 1:
			game.startNewGame(true);
			clearBoard();
			setOtherPlayerTurn();
			break;
		default:
			return;
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

	public void setConnected(String string) {
		// TODO Auto-generated method stub
		
	}
}
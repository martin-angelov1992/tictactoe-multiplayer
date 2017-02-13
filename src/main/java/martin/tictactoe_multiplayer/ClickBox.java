package martin.tictactoe_multiplayer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ClickBox extends JPanel {

	private byte x;
	private byte y;

	private static Set<ClickBox> boxes = new HashSet<>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClickBox(int x, int y, View view) {
		this((byte)x, (byte)y, view);
	}

	public ClickBox(byte x, byte y, final View view) {
		this.x = x;
		this.y = y;
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
            	Board board = Board.getInstance();
            	Player me = board.getPlayer();
            	Position pos = Position.getPosition(ClickBox.this.x, ClickBox.this.y);
            	boolean success = board.tryMakeMove(me, pos);
            	if (success) {
            		String file = me.getSymbol().getFIle()+".png";
            		putBackground(file);
            		view.setOtherPlayerTurn();
            		view.notifyMadeMove(false);
            		Player winner = board.getWinner();
            		if (winner != null) {
            			view.gotWinner("You");
	            	}
            		if (!board.isGameOver() && winner == null) {
            			view.makeOtherPlayerMove();
            		}
            	}
            }
        });

        boxes.add(this);
	}

	public void putBackground(String fileName) {
		try {
			Image backgroundImage = ImageIO.read(new File(fileName));
			Graphics g = this.getGraphics();
			g.drawImage(backgroundImage, 0, 0, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearBackground() {
		this.removeAll();
	}

	public static Set<ClickBox> getBoxes() {
		return boxes;
	}

	public static ClickBox getBoxForPos(int x, int y) {
		for (ClickBox box : boxes) {
			if (box.x == x && box.y == y) {
				return box;
			}
		}

		throw new IllegalArgumentException("Box not found for ("+x+", "+y+").");
	}
}
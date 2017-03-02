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

	private Game game;

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
		game = view.getGame();
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
            	if (!game.isMyTurn()) {
            		System.out.println("It's not your turn.");
            		return;
            	}

            	Position pos = Position.getPosition(ClickBox.this.x, ClickBox.this.y);
            	game.makeMoveRequestFromUI(pos);
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
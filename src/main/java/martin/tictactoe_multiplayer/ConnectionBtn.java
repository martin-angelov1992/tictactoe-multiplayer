package martin.tictactoe_multiplayer;

import javax.swing.JButton;

public class ConnectionBtn extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean inConnectedState;

	public ConnectionBtn(String name) {
		super(name);
	}

	public boolean isInConnectedState() {
		return inConnectedState;
	}

	public void setInConnectedState(boolean inConnectedState) {
		this.inConnectedState = inConnectedState;
	}
}
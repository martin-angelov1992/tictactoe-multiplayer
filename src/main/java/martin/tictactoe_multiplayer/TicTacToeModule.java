package martin.tictactoe_multiplayer;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

import martin.tictactoe_multiplayer.communication.Communication;
import martin.tictactoe_multiplayer.communication.CommunicationHandler;
import martin.tictactoe_multiplayer.communication.CommunicationInitializer;

public class TicTacToeModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(View.class).in(Singleton.class);
		bind(Game.class).in(Singleton.class);
		bind(Board.class).in(Singleton.class);

		bind(Communication.class).in(Singleton.class);
		bind(CommunicationHandler.class).in(Singleton.class);
		bind(CommunicationInitializer.class).in(Singleton.class);
	}
}
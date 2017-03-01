package martin.tictactoe_multiplayer;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

import martin.tictactoe_multiplayer.communication.Communication;
import martin.tictactoe_multiplayer.communication.CommunicationHandler;
import martin.tictactoe_multiplayer.communication.CommunicationInitializer;
import martin.tictactoe_multiplayer.communication.handlers.MoveHandler;
import martin.tictactoe_multiplayer.communication.handlers.StartGameRequestHandler;
import martin.tictactoe_multiplayer.communication.handlers.StartNewGameResponseHandler;

public class TicTacToeModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(View.class).in(Singleton.class);
		bind(Game.class).in(Singleton.class);
		bind(Board.class).in(Singleton.class);

		bind(Communication.class).in(Singleton.class);
		bind(CommunicationHandler.class).in(Singleton.class);
		bind(CommunicationInitializer.class).in(Singleton.class);

		bind(MoveHandler.class).in(Singleton.class);
		bind(StartGameRequestHandler.class).in(Singleton.class);
		bind(StartNewGameResponseHandler.class).in(Singleton.class);
	}
}
package martin.tictactoe_multiplayer;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public class ProxyModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(View.class).in(Singleton.class);
		bind(Communication.class).in(Singleton.class);
	}
}
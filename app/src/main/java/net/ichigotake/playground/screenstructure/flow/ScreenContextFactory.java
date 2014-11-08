package net.ichigotake.playground.screenstructure.flow;

import android.content.Context;

public interface ScreenContextFactory {

	Context setUpContext(Screen screen, Context parentContext);

	/**
	 * Tear down any services previously started by {@link #setUpContext(Screen, Context)}. Note that
	 * the Context instance given here may be a wrapper around an instance that this factory
	 * created.
	 */
	void tearDownContext(Context context);

}

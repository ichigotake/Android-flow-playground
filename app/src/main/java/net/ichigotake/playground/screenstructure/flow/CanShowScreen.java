package net.ichigotake.playground.screenstructure.flow;

import flow.Flow;

public interface CanShowScreen {

	void showScreen(Screen screen, Flow.Direction direction, Flow.Callback callback);

}

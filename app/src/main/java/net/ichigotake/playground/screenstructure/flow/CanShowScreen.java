package net.ichigotake.playground.screenstructure.flow;

import flow.Flow;

/**
 * 可視可能かどうかを表す
 */
public interface CanShowScreen {

	void showScreen(Screen screen, Flow.Direction direction, Flow.Callback callback);

}

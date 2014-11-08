package net.ichigotake.playground.screenstructure.flow;

import android.view.View;

import flow.Flow;

public class UpAndBackHandler {

	private final Flow flow;

	public UpAndBackHandler(Flow flow) {
		this.flow = flow;
	}

	public boolean onUpPressed(View childView) {
		if (childView instanceof HandlesUp) {
			if (((HandlesUp)childView).onUpPressed()) {
				return true;
			}
		}
		return flow.goUp() || onBackPressed(childView);
	}

	public boolean onBackPressed(View childView) {
		if (childView instanceof HandlesBack) {
			if (((HandlesBack)childView).onBackPressed()) {
				return true;
			}
		}
		return flow.goBack();
	}

}

package net.ichigotake.playground.screenstructure.app.tutorial;

import android.content.Context;

import net.ichigotake.playground.screenstructure.flow.AppFlow;

class TutorialWelcomePresenter {

	private final Context context;

	TutorialWelcomePresenter(Context context) {
		this.context = context;
	}

	void clickedView() {
		AppFlow.get(context).goTo(new TutorialScreens.Description1());
	}
}

package net.ichigotake.playground.screenstructure.flow;

import android.view.View;

public interface Screen {

	String getTitle();
	void onViewCreated(View view);

}

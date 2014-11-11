package net.ichigotake.playground.screenstructure.app.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import net.ichigotake.playground.screenstructure.flow.AppFlow;

public class TutorialDescription1View extends TextView {

	public TutorialDescription1View(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AppFlow.get(getContext()).goTo(new TutorialScreens.Description2());
			}
		});
	}
}

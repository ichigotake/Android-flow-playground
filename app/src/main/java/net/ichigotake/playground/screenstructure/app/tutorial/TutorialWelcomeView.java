package net.ichigotake.playground.screenstructure.app.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class TutorialWelcomeView extends TextView {

	private final TutorialWelcomePresenter presenter;

	public TutorialWelcomeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		presenter = new TutorialWelcomePresenter(context);
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.clickedView();
			}
		});
	}
}

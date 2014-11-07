package net.ichigotake.playground.screenstructure.flow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import flow.Layout;

public class ContainerView extends FrameLayout {

	public ContainerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void showScreen(Screen screen) {
		// Get the layout resource ID and inflate it
		Layout layout = screen.getClass().getAnnotation(Layout.class);
		View view = LayoutInflater.from(getContext()).inflate(layout.value(), this, false);

		// Notify this screen that we've created the view so it can
		// bind whatever data it may need to the view.
		screen.onViewCreated(view);

		// A simple replace.
		removeAllViews();
		addView(view);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (isInEditMode()) {
			setBackground(new ColorDrawable(Color.GRAY));
			setAlpha(0.5f);
		}
	}

}

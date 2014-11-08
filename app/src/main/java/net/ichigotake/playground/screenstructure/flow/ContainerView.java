package net.ichigotake.playground.screenstructure.flow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.ichigotake.playground.screenstructure.R;

import flow.Flow;

public class ContainerView extends FrameLayout
		implements HandlesBack, HandlesUp, ScreenSwitcherView {

	private ScreenSwitcher screenSwitcher;
	private UpAndBackHandler upAndBackHandler;
	private boolean disabled;

	public ContainerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) {
			return;
		}
		upAndBackHandler = new UpAndBackHandler(AppFlow.get(context));
		screenSwitcher = new SimpleSwitcher.Factory(R.id.screen_switcher_tag, AppFlow.contextFactory())
				.createScreenSwitcher(this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (isInEditMode()) {
			setBackground(new ColorDrawable(Color.GRAY));
			setAlpha(0.5f);
		}
	}

	@Override
	public void showScreen(Screen screen, Flow.Direction direction, final Flow.Callback callback) {
		disabled = true;
		screenSwitcher.showScreen(screen, direction, new Flow.Callback() {
			@Override
			public void onComplete() {
				callback.onComplete();
				disabled = false;
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return !disabled && super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onBackPressed() {
		return upAndBackHandler.onBackPressed(getCurrentChild());
	}

	@Override
	public boolean onUpPressed() {
		return upAndBackHandler.onUpPressed(getCurrentChild());
	}

	@Override
	public ViewGroup getCurrentChild() {
		return (ViewGroup) getContainerView().getChildAt(0);
	}

	@Override
	public ViewGroup getContainerView() {
		return this;
	}
}

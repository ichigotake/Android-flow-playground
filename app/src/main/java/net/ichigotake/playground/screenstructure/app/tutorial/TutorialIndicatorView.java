package net.ichigotake.playground.screenstructure.app.tutorial;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TutorialIndicatorView extends LinearLayout {

	private final TutorialIndicatorPresenter presenter;

	public TutorialIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		presenter = new TutorialIndicatorPresenter(this);
	}

	public void setPosition(int position) {
		presenter.setPosition(position);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		super.onRestoreInstanceState(state);
		presenter.onRestoreInstanceState(state);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		return presenter.onSaveInstanceState(super.onSaveInstanceState());
	}

}

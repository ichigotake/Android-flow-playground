package net.ichigotake.playground.screenstructure.flow;

import android.view.View;
import android.view.ViewTreeObserver;

public class ViewMeasureUtil {

	public interface OnMeasuredCallback {
		void onMeasured(View view, int width, int height);
	}

	public static void waitForMeasure(final View view, final OnMeasuredCallback callback) {
		int width = view.getWidth();
		int height = view.getHeight();

		if (width > 0 && height > 0) {
			callback.onMeasured(view, width, height);
			return;
		}

		view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override public boolean onPreDraw() {
				final ViewTreeObserver observer = view.getViewTreeObserver();
				if (observer.isAlive()) {
					observer.removeOnPreDrawListener(this);
				}

				callback.onMeasured(view, view.getWidth(), view.getHeight());

				return true;
			}
		});
	}

	private ViewMeasureUtil() {
	}

}

package net.ichigotake.playground.screenstructure.flow;

import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.Map;

import flow.Flow;
import flow.Layout;

public abstract class ScreenSwitcher {

	public abstract static class Factory {
		protected final int tagKey;
		protected final ScreenContextFactory contextFactory;

		protected Factory(int tagKey, ScreenContextFactory contextFactory) {
			this.tagKey = tagKey;
			this.contextFactory = contextFactory;
		}

		public abstract ScreenSwitcher createScreenSwitcher(ScreenSwitcherView view);
	}

	protected static final class Tag {
		public Screen fromScreen;
		public Screen toScreen;

		public void setNextScreen(Screen screen) {
			this.fromScreen = this.toScreen;
			this.toScreen = screen;
		}
	}


	private static final Map<Class, Integer> SCREEN_LAYOUT_CACHE = new LinkedHashMap<Class, Integer>();

	private final ScreenSwitcherView view;
	protected final int tagKey;

	protected ScreenSwitcher(ScreenSwitcherView view, int tagKey) {
		this.view = view;
		this.tagKey = tagKey;
	}

	public void showScreen(Screen screen, Flow.Direction direction, final Flow.Callback callback) {
		final View oldChild = view.getCurrentChild();
		Screen oldChildScreen = null;

		// See if we already have the direct child we want, and if so delegate the transition.
		if (oldChild != null) {
			Tag tag = (Tag) view.getContainerView().getTag(tagKey);
//			oldChildScreen = checkNotNull(tag.toScreen, "Container view has child %s with no screen",
//					oldChild.toString());
			oldChildScreen = tag.toScreen;
			if (oldChildScreen.getName().equals(screen.getName())) {
				callback.onComplete();
				return;
			}
		}

		transition(view.getContainerView(), oldChildScreen, screen, direction, callback);
	}

	protected abstract void transition(ViewGroup container, Screen from, Screen to,
									   Flow.Direction direction, Flow.Callback callback);

	protected Tag ensureTag(ViewGroup container) {
		Tag tag = (Tag) container.getTag(tagKey);
		if (tag == null) {
			tag = new Tag();
			container.setTag(tagKey, tag);
		}
		return tag;
	}

	protected static int getLayout(Object screen) {
		Class<Object> screenType = (Class<Object>) screen.getClass();
		Integer layoutResId = SCREEN_LAYOUT_CACHE.get(screenType);
		if (layoutResId == null) {
			Layout layout = screenType.getAnnotation(Layout.class);
//			checkNotNull(layout, "@%s annotation not found on class %s", Layout.class.getSimpleName(),
//					screenType.getName());
			layoutResId = layout.value();
			SCREEN_LAYOUT_CACHE.put(screenType, layoutResId);
		}
		return layoutResId;
	}

}
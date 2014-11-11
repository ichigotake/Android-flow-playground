package net.ichigotake.playground.screenstructure.flow;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import flow.Flow;

/**
 * square/flow で提供される {@link Flow} のラッパー
 *
 * 公式のサンプルからコピペ
 *
 * https://github.com/square/flow/blob/master/flow-sample/src/main/java/com/example/flow/appflow/AppFlow.java
 */
public class AppFlow {

	private static final String APP_FLOW_SERVICE = "app_flow";

	public static ScreenContextFactory contextFactory() {
		return new ContextFactory();
	}

	public static ScreenContextFactory contextFactory(ScreenContextFactory delegate) {
		return new ContextFactory(delegate);
	}

	public static boolean isAppFlowSystemService(String name) {
		return APP_FLOW_SERVICE.equals(name);
	}

	public static Flow get(Context context) {
		AppFlow appFlow =  (AppFlow) context.getSystemService(APP_FLOW_SERVICE);
		return appFlow.flow;
	}

	public static <T> T getScreen(Context context) {
		// If this blows up, it's on the caller.  We hide the cast as a convenience.
		//noinspection unchecked
		return (T) LocalScreenWrapper.get(context).localScreen;
	}

	public static void loadInitialScreen(Context context) {
		Flow flow = get(context);
		Object screen = get(context).getBackstack().current().getScreen();
		flow.resetTo(screen);
	}

	private final Flow flow;

	protected AppFlow(Flow flow) {
		this.flow = flow;
	}

	private static final class LocalScreenWrapper extends ContextWrapper {
		static final String LOCAL_WRAPPER_SERVICE = "flow_local_screen_context_wrapper";
		private LayoutInflater inflater;

		static LocalScreenWrapper get(Context context) {
			//noinspection ResourceType
			return (LocalScreenWrapper) context.getSystemService(LOCAL_WRAPPER_SERVICE);
		}

		final Object localScreen;

		LocalScreenWrapper(Context base, Object localScreen) {
			super(base);
			this.localScreen = localScreen;
		}

		@Override
		public Object getSystemService(String name) {
			if (LOCAL_WRAPPER_SERVICE.equals(name)) {
				return this;
			}
			if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
				if (inflater == null) {
					inflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
				}
				return inflater;
			}
			return super.getSystemService(name);
		}
	}

	private static final class ContextFactory implements ScreenContextFactory {
		@Nullable
		private final ScreenContextFactory delegate;

		public ContextFactory() {
			delegate = null;
		}

		public ContextFactory(ScreenContextFactory delegate) {
			this.delegate = delegate;
		}

		@Override
		public Context setUpContext(Screen screen, Context parentContext) {
			if (delegate != null) {
				parentContext = delegate.setUpContext(screen, parentContext);
			}
			return new LocalScreenWrapper(parentContext, screen);
		}

		@Override
		public void tearDownContext(Context context) {
			if (delegate != null) {
				delegate.tearDownContext(context);
			}
		}
	}
}

package net.ichigotake.playground.screenstructure.app.tutorial;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.ichigotake.playground.screenstructure.R;
import net.ichigotake.playground.screenstructure.flow.AppFlow;
import net.ichigotake.playground.screenstructure.flow.ContainerView;
import net.ichigotake.playground.screenstructure.flow.FlowBundler;
import net.ichigotake.playground.screenstructure.flow.NoParameterParcer;
import net.ichigotake.playground.screenstructure.flow.Screen;

import flow.Backstack;
import flow.Flow;

public class TutorialActivity extends ActionBarActivity implements Flow.Listener {

	private FlowBundler flowBundler = new FlowBundler(
			new TutorialScreens.Welcome(), this, new NoParameterParcer());
	private AppFlow appFlow;
	private ContainerView container;
	private TutorialIndicatorView indicatorView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appFlow = flowBundler.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		container = (ContainerView) findViewById(R.id.screen_switcher_tag);
		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
		setSupportActionBar(toolbar);
		indicatorView = (TutorialIndicatorView) findViewById(R.id.activity_main_indicator);

		AppFlow.loadInitialScreen(this);
	}

	/**
	 * {@link Flow} によって {@link Screen} が遷移した後にコールバックされる。
	 */
	@Override
	public void go(Backstack nextBackstack, Flow.Direction direction, Flow.Callback callback) {
		Screen screen = (Screen) nextBackstack.current().getScreen();
		container.showScreen(screen, direction, callback);
		setTitle(screen.getTitle());

		if (screen instanceof TutorialPageNumber) {
			indicatorView.setPosition(((TutorialPageNumber) screen).getPageNumber()-1);
		}

		// Update ActionBar
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(screen.getTitle());
			if (nextBackstack.size() > 1) {
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			} else {
				getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return (item.getItemId() == android.R.id.home && container.onUpPressed())
				|| super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (!container.onBackPressed()) {
			super.onBackPressed();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		flowBundler.onSaveInstanceState(outState);
	}

	/**
	 * {@link #getSystemService} でActivityの保持する {@link AppFlow} のインスタンスを取得出来るようにする。
	 * {@link AppFlow#get(android.content.Context)} を利用して、
	 * アクティビティコンテキストを持つ所ではどこでも画面遷移を実行する事が出来る。
	 */
	@Override
	public Object getSystemService(String name) {
		if (AppFlow.isAppFlowSystemService(name)) return appFlow;
		return super.getSystemService(name);
	}

}

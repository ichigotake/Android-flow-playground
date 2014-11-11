package net.ichigotake.playground.screenstructure.flow;

import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class Screen {

	private SparseArray<Parcelable> viewState;

	public abstract String getTitle();

	@Override public boolean equals(Object o) {
		return o != null && o instanceof Screen && this.getName().equals(((Screen) o).getName());
	}

	@Override public int hashCode() {
		return getName().hashCode();
	}

	public String getName() {
		return getClass().getName();
	}

	public final List<Screen> getPath() {
		List<Screen> path = new ArrayList<Screen>();
		buildPath(path);
		// For convenience, we don't require leaf classes to override buildPath().
		if (path.isEmpty() || isPathLeaf(path)) {
			path.add(this);
		}
		return path;
	}

	public void setViewState(SparseArray<Parcelable> viewState) {
		this.viewState = viewState;
	}

	protected SparseArray<Parcelable> getViewState() {
		return viewState;
	}

	public void restoreHierarchyState(View view) {
		if (getViewState() != null) {
			view.restoreHierarchyState(getViewState());
		}
	}

	protected void buildPath(List<Screen> path) {
	}

	private boolean isPathLeaf(List<Screen> path) {
		return !equals(path.get(path.size() - 1));
	}

}

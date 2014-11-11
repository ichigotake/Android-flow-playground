package net.ichigotake.playground.screenstructure.app.tutorial;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.playground.screenstructure.R;

class TutorialIndicatorPresenter {

	private final ViewGroup container;
	private int currentPosition;

	TutorialIndicatorPresenter(ViewGroup container) {
		LayoutInflater.from(container.getContext()).inflate(R.layout.tutorial_indicator, container, true);
		this.container = (ViewGroup) container.findViewById(R.id.tutorial_indicator_container);
		setPosition(0);
	}

	void setPosition(int position) {
		for (int i=0, size=container.getChildCount(); i<size; i++) {
			if (position == i) {
				container.getChildAt(i).setBackground(new ColorDrawable(Color.GRAY));
			} else {
				container.getChildAt(i).setBackground(null);
			}
		}
	}

	Parcelable onSaveInstanceState(Parcelable parcelable) {
		if (!(parcelable instanceof SavedState)) {
			return parcelable;
		}
		SavedState state = new SavedState(parcelable);
		state.position = currentPosition;
		return state.getSuperState();
	}

	void onRestoreInstanceState(Parcelable superState) {
		if (!(superState instanceof SavedState)) {
			return;
		}
		SavedState restoredState = (SavedState)superState;
		currentPosition = restoredState.position;
		setPosition(restoredState.position);
	}

	private static class SavedState extends View.BaseSavedState implements Parcelable {

		private int position;

		public SavedState(Parcel source) {
			super(source);
			position = source.readInt();
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(this.position);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			public SavedState createFromParcel(Parcel source) {
				return new SavedState(source);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}
}

package net.ichigotake.playground.screenstructure.app.tutorial;

import net.ichigotake.playground.screenstructure.R;
import net.ichigotake.playground.screenstructure.app.tutorial.TutorialPageNumber;
import net.ichigotake.playground.screenstructure.flow.Screen;

import flow.HasParent;
import flow.Layout;

public class TutorialScreens {

	@Layout(R.layout.tutorial_welcome)
	public static class Welcome extends Screen implements TutorialPageNumber {

		@Override
		public String getTitle() {
			return "Welcome!";
		}

		@Override
		public int getPageNumber() {
			return 1;
		}
	}

	@Layout(R.layout.tutorial_description_1)
	public static class Description1 extends Screen implements HasParent<Welcome>, TutorialPageNumber {

		@Override
		public String getTitle() {
			return "Description 1";
		}

		@Override
		public Welcome getParent() {
			return new Welcome();
		}

		@Override
		public int getPageNumber() {
			return 2;
		}
	}

	@Layout(R.layout.tutorial_description_2)
	public static class Description2 extends Screen implements HasParent<Description1>, TutorialPageNumber {

		@Override
		public String getTitle() {
			return "Description 2";
		}

		@Override
		public Description1 getParent() {
			return new Description1();
		}

		@Override
		public int getPageNumber() {
			return 3;
		}
	}

	@Layout(R.layout.tutorial_description_3)
	public static class Description3 extends Screen implements HasParent<Description2>, TutorialPageNumber {

		@Override
		public String getTitle() {
			return "Description 3";
		}

		@Override
		public Description2 getParent() {
			return new Description2();
		}

		@Override
		public int getPageNumber() {
			return 4;
		}
	}

	@Layout(R.layout.tutorial_description_4)
	public static class Description4 extends Screen implements HasParent<Description3>, TutorialPageNumber {

		@Override
		public String getTitle() {
			return "Description 4";
		}

		@Override
		public Description3 getParent() {
			return new Description3();
		}

		@Override
		public int getPageNumber() {
			return 5;
		}
	}

}

package net.ichigotake.playground.screenstructure.flow;


import android.os.Parcel;
import android.os.Parcelable;

import flow.Parcer;

/**
 * {@link Screen} をシリアライズする
 *
 * このプロジェクトで利用している {@link Screen} では、コンストラクタに引数を取るものはないため、
 * クラス名をシリアライズしてリフレクションで {@link Screen} の再生成をしている
 *
 * 公式サンプルでは、Gsonを利用してシリアライズしている
 * https://github.com/square/flow/blob/master/flow-sample/src/main/java/com/example/flow/GsonParcer.java
 *
 * 注) `Parcer` はtypoではない https://github.com/square/flow/issues/29
 *
 * @param <T> シリアライズされる {@link Screen} のインスタンス
 */
public final class NoParameterParcer<T> implements Parcer<T> {

	@Override
	public Parcelable wrap(T instance) {
		return new Wrapper(instance.getClass().getName());
	}

	@Override
	public T unwrap(Parcelable parcelable) {
		Wrapper wrapper = (Wrapper)parcelable;
		try {
			return (T) getClass().getClassLoader().loadClass(wrapper.typeName).newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException();
		} catch (InstantiationException e) {
			throw new RuntimeException();
		} catch (IllegalAccessException e) {
			throw new RuntimeException();
		}
	}

	private static class Wrapper implements Parcelable {

		private final String typeName;

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(this.typeName);
		}

		public Wrapper(String typeName) {
			this.typeName = typeName;
		}

		private Wrapper(Parcel in) {
			this.typeName = in.readString();
		}

		public static final Creator<Wrapper> CREATOR = new Creator<Wrapper>() {
			public Wrapper createFromParcel(Parcel source) {
				return new Wrapper(source);
			}

			public Wrapper[] newArray(int size) {
				return new Wrapper[size];
			}
		};
	}

}

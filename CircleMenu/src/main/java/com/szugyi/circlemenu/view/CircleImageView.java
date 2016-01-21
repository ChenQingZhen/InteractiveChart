package com.szugyi.circlemenu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szugyi.circlemenu.R;

public class CircleImageView extends LinearLayout {

	private ImageView imgView;
	private TextView textView;

	private float angle = 0;
	private int position = 0;
	private String name;

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param context
	 */
	public CircleImageView(Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CircleImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	@SuppressLint("NewApi")
	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater.from(context).inflate(R.layout.img_text_bg, this, true);

		this.imgView = (ImageView) findViewById(R.id.imgview);
		this.textView = (TextView) findViewById(R.id.textview);

		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.CircleImageView);

			name = a.getString(R.styleable.CircleImageView_name);
		}
	}

	public void setImgResource(int resourceID) {
		this.imgView.setImageResource(resourceID);
	}

	public void setText(String text) {
		this.textView.setText(text);
	}

	public void setTextColor(int color) {
		this.textView.setTextColor(color);
	}

	public void setTextSize(float size) {
		this.textView.setTextSize(size);
	}
}

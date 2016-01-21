package com.szugyi.circlemenu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;
import com.szugyi.circlemenu.view.CircleLayout.OnItemClickListener;
import com.szugyi.circlemenu.view.CircleLayout.OnItemSelectedListener;

public class MainActivity extends Activity implements OnItemSelectedListener,
		OnItemClickListener {
	private int drawab[] = { R.drawable.icon_facebook, R.drawable.icon_google,
			R.drawable.icon_myspace, R.drawable.icon_twitter,
			R.drawable.icon_wordpress };

	private String titlt[] = { "facebook", "google", "myspace", "twitter",
			"wordpress" };

	private CircleLayout circleMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		circleMenu = (CircleLayout) findViewById(R.id.main_circle_layout);
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);

		for (int i = 0; i < 5; i++) {
			final CircleImageView ci = new CircleImageView(
					getApplicationContext());

			ci.setImgResource(drawab[i]);
			ci.setText(titlt[i]);
			ci.setTextColor(Color.BLACK);

			circleMenu.addView(ci);
		}
	}

	@Override
	public void onItemSelected(View view, int position, long id, String name) {
		System.out.println(position + "经过了");
	}

	@Override
	public void onItemClick(View view, int position, long id, String name) {
		System.out.println(position + "被点击了");
	}
}

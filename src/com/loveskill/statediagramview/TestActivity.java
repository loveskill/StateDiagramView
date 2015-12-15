package com.loveskill.statediagramview;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity {
	private StateDiagramView stateDiagramView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);
		initView();
	}
	
	private void initView(){
		stateDiagramView = (StateDiagramView)findViewById(R.id.my_statediagramview);
		String[] titles = getResources().getStringArray(R.array.state_title);
		stateDiagramView.setTitle(titles);
	}

}

package com.example.todolister;

import java.util.ArrayList;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyPriorityAdapter extends BaseAdapter {

	ArrayList<String> priority = new ArrayList<String>();

	public MyPriorityAdapter() {

		priority.add("1 - Very high");
		priority.add("2 - High");
		priority.add("3 - Medium");
		priority.add("4 - Low");
		priority.add("5 - Very Low");

	}

	@Override
	public int getCount() {

		return priority.size();
	}

	@Override
	public Object getItem(int index) {

		return getItem(index);
	}

	@Override
	public long getItemId(int index) {

		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {

		if (view == null) {

			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.priority_main_list, parent, false);

		}

		TextView priorityName = (TextView) view
				.findViewById(R.id.priority_name);
		TextView priorityCode = (TextView) view
				.findViewById(R.id.priority_code);
		String tmp = priority.get(index);
		if (tmp.equalsIgnoreCase("1 - Very high")) {

			priorityName.setText("1 - Very high");
			priorityName.setBackgroundColor(Color.parseColor("#f44336"));
			priorityName.setTextColor(Color.parseColor("#FFFFFF"));
			priorityCode.setText("V H");
			priorityCode.setTextColor(Color.parseColor("#f44336"));

		}
		if (tmp.equalsIgnoreCase("2 - High")) {
			priorityName.setText("2 - High");
			priorityName.setBackgroundColor(Color.parseColor("#ff9800"));
			priorityName.setTextColor(Color.parseColor("#FFFFFF"));
			priorityCode.setText(" H ");
			priorityCode.setTextColor(Color.parseColor("#ff9800"));

		}
		if (tmp.equalsIgnoreCase("3 - Medium")) {
			priorityName.setText("3 - Medium");
			priorityName.setBackgroundColor(Color.parseColor("#fbc02d"));
			priorityName.setTextColor(Color.parseColor("#FFFFFF"));
			priorityCode.setText(" M ");
			priorityCode.setTextColor(Color.parseColor("#fbc02d"));

		}
		if (tmp.equalsIgnoreCase("4 - Low")) {
			priorityName.setText("4 - Low");
			priorityName.setBackgroundColor(Color.parseColor("#8bc34a"));
			priorityName.setTextColor(Color.parseColor("#FFFFFF"));
			priorityCode.setText(" L ");
			priorityCode.setTextColor(Color.parseColor("#8bc34a"));

		}
		if (tmp.equalsIgnoreCase("5 - Very Low")) {
			priorityName.setText("5 - Very Low");
			priorityName.setBackgroundColor(Color.parseColor("#388e3c"));
			priorityName.setTextColor(Color.parseColor("#FFFFFF"));
			priorityCode.setText("V L");
			priorityCode.setTextColor(Color.parseColor("#388e3c"));

		}

		return view;
	}
}
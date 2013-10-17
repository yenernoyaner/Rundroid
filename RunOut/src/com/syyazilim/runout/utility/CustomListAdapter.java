package com.syyazilim.runout.utility;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.syyazilim.runout.R;
import com.syyazilim.runout.domain.UserSession;

public class CustomListAdapter extends BaseAdapter {

	    private ArrayList listData;
	    private Context context;
	    private LayoutInflater layoutInflater = null;
	    
	    public CustomListAdapter(Context context, ArrayList listData) {	    	
	    	this.context = context;
	        this.listData = listData;
	        layoutInflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 
	    @Override
	    public int getCount() {
	        return listData.size();
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        return listData.get(position);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder;
	        UserSession session;
	        if (convertView == null) {
	            convertView = layoutInflater.inflate(R.layout.session_history_row, null);
	            holder = new ViewHolder();
	            holder.caloriesView = (TextView) convertView.findViewById(R.id.calories);
	            holder.distanceView = (TextView) convertView.findViewById(R.id.distance);
	            holder.dateView = (TextView) convertView.findViewById(R.id.date);
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	        session = (UserSession) listData.get(position);
	        holder.caloriesView.setText(session.getCalories());
	        holder.distanceView.setText(session.getDistance());
	        holder.dateView.setText(session.getTime());
	 
	        return convertView;
	    }
	 
	    static class ViewHolder {
	        TextView caloriesView;
	        TextView distanceView;
	        TextView dateView;
	    }
	 
	}


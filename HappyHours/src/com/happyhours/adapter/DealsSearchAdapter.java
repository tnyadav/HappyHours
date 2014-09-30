package com.happyhours.adapter;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.happyhours.R;
import com.happyhours.model.ListItem;

public class DealsSearchAdapter extends ArrayAdapter<ListItem>{
	  public DealsSearchAdapter(Context context, int viewResourceId, List<ListItem> objects) {
	        super(context, viewResourceId, objects);
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        
	      ListItem  item = getItem(position);
	        
	        ViewHolder holder;
	        if (convertView == null) {
	            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	            convertView = inflater.inflate(R.layout.deals_item, parent, false);
	            holder = new ViewHolder();
	            holder = new ViewHolder();
	            holder.propertyIcon = (ImageView) convertView.findViewById(R.id.imageview_property_image);
	            holder .off = (TextView) convertView.findViewById(R.id.off);
	            holder .title1 = (TextView) convertView.findViewById(R.id.title1);
	            holder .title2 = (TextView) convertView.findViewById(R.id.title2);
	            holder .title3 = (TextView) convertView.findViewById(R.id.title3);
	            holder .oldPrice = (TextView) convertView.findViewById(R.id.oldPrice);
	            holder .newPrice = (TextView) convertView.findViewById(R.id.newPrice);
	          
		         
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	            
	        }
	        holder.propertyIcon.setImageResource(item.propertyIcon);
	        holder .off.setText(item.off+"%");
            holder .title1.setText(item.title1);
            holder .title2.setText(item.title2);
            holder .title3.setText(item.title3);
            holder .oldPrice.setText(item.oldPrice);
            holder .oldPrice.setPaintFlags(holder .oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder .newPrice.setText(item.newPrice);
	       
	        
	        return convertView;
	    }
	    
	    /**
	* ViewHolder.
	*/
	    private class ViewHolder {
	        
	    	private ImageView propertyIcon;
	    	private TextView off;
	    	private TextView title1;
	    	private TextView title2;
	    	private TextView title3;
	    	private TextView oldPrice;
	    	private TextView newPrice;
	    	
	    	
	        
	    }
	    
	    
}

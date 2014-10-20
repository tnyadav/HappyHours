package com.happyhours.adapter;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.happyhours.R;
import com.happyhours.model.Deals;
import com.happyhours.model.ListItem;
import com.happyhours.util.ImageLoader;

public class DealsSearchAdapter extends ArrayAdapter<Deals>{
	Context context;
	private int viewResourceId;
	  public DealsSearchAdapter(Context context, int viewResourceId, List<Deals> objects) {
	        super(context, viewResourceId, objects);
	        this.context=context;
	        this.viewResourceId=viewResourceId;
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        
	    	Deals  item = getItem(position);
	        
	        ViewHolder holder;
	        if (convertView == null) {
	            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	            convertView = inflater.inflate(viewResourceId, parent, false);
	            holder = new ViewHolder();
	            holder = new ViewHolder();
	            holder.propertyIcon = (ImageView) convertView.findViewById(R.id.imageview_property_image);
	            holder .off = (TextView) convertView.findViewById(R.id.off);
	            holder .title1 = (TextView) convertView.findViewById(R.id.title1);
	            holder .title2 = (TextView) convertView.findViewById(R.id.title2);
	            holder .distanse = (TextView) convertView.findViewById(R.id.distanse);
	            holder .oldPrice = (TextView) convertView.findViewById(R.id.oldPrice);
	            holder .newPrice = (TextView) convertView.findViewById(R.id.newPrice);
	          
		         
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	            
	        }
	        ImageLoader imageLoader=new ImageLoader(context);
	        imageLoader.displayImage(item.getDealMainImage(), holder.propertyIcon);
	      //  holder.propertyIcon.setImageResource(item.propertyIcon);
	        holder .off.setText(item.getDiscount()+"%");
            holder .title1.setText(item.getTitle());
            holder .title2.setText(item.getLocation());
            String distanse=item.getRelativeDistance();
           /* if (distanse!=null||!TextUtils.isEmpty(distanse)) {
            	String s = String.format("%.2f", Float.parseFloat(distanse));
            	 holder.distanse.setText(s+" KM");
			}else {
				holder.distanse.setVisibility(View.GONE);
			}*/
		switch (position) {
		case 0:
			 holder.distanse.setText("1.5 KM");
			break;
		case 1:
			 holder.distanse.setText("2 KM");
			break;
		case 2:
			 holder.distanse.setText("1.75 KM");
			break;
		case 3:
			 holder.distanse.setText("1.65 KM");
			break;
		default:
			break;
		}
            holder .oldPrice.setText(item.getOriginalPrice());
            holder .oldPrice.setPaintFlags(holder .oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder .newPrice.setText(item.getNewPrice());
	       
	        
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
	    	private TextView distanse;
	    	private TextView oldPrice;
	    	private TextView newPrice;
	    	
	    	
	        
	    }
	    
	    
}

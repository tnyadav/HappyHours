package com.happyhours.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.app.happyhours.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.happyhours.model.DealOffers;
import com.happyhours.model.Deals;
import com.happyhours.model.Login;
import com.happyhours.util.NotificationUtils;
import com.jainbooks.web.WebServiceConstants;

public class DealEntryFragment extends BaseFragment {
	private static final int MAIN_IMAGE = 100;
	private static final int IMAGE1 = 101;
	private static final int IMAGE2 = 102;
	private static final int IMAGE3 = 103;
	private static final int DATE_DIALOG_ID = 999;
	private static final int START_DATE = 104;
	private static final int END_DATE = 105;
	private int date=0;
	private int year;
	private int month;
	private int day;

	private EditText title, subTitle, location,discription, originalPrice, newPrice,
			startDate, endDate, latitude, longitude, offer1, offer2, offer3;
	private String strTitle, strSubTitle, strLocation,strDiscription, strOriginalPrice,
			strNewPrice, strStartDate, strEndDate, strLatitude, strLongitude,
			strOffer1, strOffer2, strOffer3, mainImagePath, image1Path,
			image2Path, image3Path;
	private CheckBox dealType;
	private Button mainImage, image1, image2, image3,submit;
	private ImageButton btnStartDate,btnEndDate;
	ImageView mainImageView, imageView1, imageView2, imageView3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_deal_entry, container, false);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setupUiComponent();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		 }

	@Override
	void setupUiComponent() {
		title = (EditText) view.findViewById(R.id.title);
		subTitle = (EditText) view.findViewById(R.id.subTitle);
		location = (EditText) view.findViewById(R.id.location);
		discription = (EditText) view.findViewById(R.id.discription);
		originalPrice = (EditText) view.findViewById(R.id.originalPrice);
		newPrice = (EditText) view.findViewById(R.id.newPrice);
		startDate = (EditText) view.findViewById(R.id.startDate);
		endDate = (EditText) view.findViewById(R.id.endDate);
		latitude = (EditText) view.findViewById(R.id.latitude);
		longitude = (EditText) view.findViewById(R.id.longitude);
		dealType = (CheckBox) view.findViewById(R.id.dealType);
		offer1 = (EditText) view.findViewById(R.id.offer1);
		offer2 = (EditText) view.findViewById(R.id.offer2);
		offer3 = (EditText) view.findViewById(R.id.offer3);

		mainImageView = (ImageView) view.findViewById(R.id.mainImageView);
		imageView1 = (ImageView) view.findViewById(R.id.imageView1);
		imageView2 = (ImageView) view.findViewById(R.id.imageView2);
		imageView3 = (ImageView) view.findViewById(R.id.imageView3);

		mainImage = (Button) view.findViewById(R.id.mainImage);
		mainImage.setOnClickListener(onClickListener);
		image1 = (Button) view.findViewById(R.id.image1);
		image1.setOnClickListener(onClickListener);
		image2 = (Button) view.findViewById(R.id.image2);
		image2.setOnClickListener(onClickListener);
		image3 = (Button) view.findViewById(R.id.image3);
		image3.setOnClickListener(onClickListener);
		
		submit=(Button)view.findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submitDeal();
				
			}
		});
		
		btnStartDate=(ImageButton)view.findViewById(R.id.btnStartDate);
		btnStartDate.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
 
				Dialog dialog=showDialog(DATE_DIALOG_ID);
				date=START_DATE;
				dialog.show();
			}
 
		});
		btnEndDate=(ImageButton)view.findViewById(R.id.btnEndDate);
		btnEndDate.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
 
				Dialog dialog=showDialog(DATE_DIALOG_ID);
				date=END_DATE;;
				dialog.show();
 
			}
 
		});

	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mainImage:
				browse(MAIN_IMAGE);
				break;
			case R.id.image1:
				browse(IMAGE1);
				break;
			case R.id.image2:
				browse(IMAGE2);
				break;
			case R.id.image3:
				browse(IMAGE3);
				break;
			default:
				break;
			}

		}
	};

	private void browse(int requestCode) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, requestCode);
	}

	private void submitDeal() {
		strTitle = title.getText().toString();
		strSubTitle = subTitle.getText().toString();
		strLocation=location.getText().toString();
		strDiscription = discription.getText().toString();
		strOriginalPrice = originalPrice.getText().toString();
		strNewPrice = newPrice.getText().toString();
		
		/*strStartDate = startDate.getText().toString();
		strEndDate = endDate.getText().toString();*/
		
		strLatitude = latitude.getText().toString();
		strLongitude = longitude.getText().toString();
		/*
		 * if (dealType.isChecked()) { strDealType="0"; }
		 */
		strOffer1 = offer1.getText().toString();
		strOffer2 = offer2.getText().toString();
		strOffer3 = offer3.getText().toString();

		if (TextUtils.isEmpty(strTitle)) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please enter Deal title");
			return;
		} else if (TextUtils.isEmpty(strSubTitle)) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please enter Deal subtitle");
			return;
		} else if (TextUtils.isEmpty(strLocation)) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please enter Deal Location");
			return;
		} else if (TextUtils.isEmpty(strDiscription)) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please enter Deal discription");
			return;
		} else if (TextUtils.isEmpty(strOriginalPrice)) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please enter Deal original price");
			return;
		} else if (TextUtils.isEmpty(strNewPrice)) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please enter Deal new price");
			return;
		} else if (!checkDate()) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please enter valid Deal date");
			return;
		
		} else if (TextUtils.isEmpty(strLatitude)) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please enter Deal latitude");
			return;
		} else if (TextUtils.isEmpty(strLongitude)) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please enter Deal longitude");
			return;
		}else if (mainImagePath==null||TextUtils.isEmpty(mainImagePath)) {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"Please select Deal image");
			return;
		}

		Deals deals = new Deals();
		deals.setTitle(strTitle);
		deals.setSubTitle(strSubTitle);
		deals.setDescription(strDiscription);
		double Percentage=(Long.parseLong(strOriginalPrice) - Long
				.parseLong(strNewPrice));
		
	/*	long Percentage = ((Long.parseLong(strOriginalPrice) - Long
				.parseLong(strNewPrice)) / Long.parseLong(strOriginalPrice)) * 100;
*/
		deals.setDiscount(""+Percentage);
		deals.setLocation(strLocation);
		deals.setOriginalPrice(strOriginalPrice);
		deals.setNewPrice(strNewPrice);
		deals.setStartDate(strStartDate);
		deals.setEndDate(strEndDate);
		deals.setLatitude(strLatitude);
		deals.setLongitude(strLongitude);
		deals.setDealType(dealType.isChecked());
		// adding offers
		List<DealOffers> dealOffersList = new ArrayList<DealOffers>();

		DealOffers dealOffers1 = new DealOffers();
		dealOffers1.setOfferName(strOffer1);
		dealOffersList.add(dealOffers1);

		DealOffers dealOffers2 = new DealOffers();
		dealOffers2.setOfferName(strOffer2);
		dealOffersList.add(dealOffers2);

		DealOffers dealOffers3 = new DealOffers();
		dealOffers3.setOfferName(strOffer3);
		dealOffersList.add(dealOffers3);
		deals.setDealOffersList(dealOffersList);
		saveDealToServer(deals);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			String path = null;
			Uri currImageURI = data.getData();
			Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
					dashboardActivity.getContentResolver(),
					Long.parseLong(currImageURI.getLastPathSegment()),
					MediaStore.Images.Thumbnails.MICRO_KIND, null);
			File file = new File(getRealPathFromURI(currImageURI));

			if (file.exists()) {
				path = file.getAbsolutePath();
				// NotificationUtils.showNotificationToast(dashboardActivity,
				// path);
			}

			switch (requestCode) {
			case MAIN_IMAGE:
				mainImagePath = path;
				mainImageView.setImageBitmap(bitmap);
				break;
			case IMAGE1:
				image1Path = path;
				imageView1.setImageBitmap(bitmap);
				break;
			case IMAGE2:
				image2Path = path;
				imageView2.setImageBitmap(bitmap);
				break;
			case IMAGE3:
				image3Path = path;
				imageView3.setImageBitmap(bitmap);
				break;
			default:
				break;
			}
		}

	}

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		android.database.Cursor cursor = dashboardActivity.managedQuery(
				contentUri, proj, // Which columns to return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();

		return cursor.getString(column_index);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		
		inflater.inflate(R.menu.map, menu);
		super.onCreateOptionsMenu(menu, inflater);
		
		// dashboardActivity.invalidateOptionsMenu();

		// fragment specific menu creation
	}


	protected Dialog showDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(dashboardActivity, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, year);
			StringBuilder dateString=new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" ");
			
			switch (date) {
			case START_DATE:
				strStartDate=""+cal.getTimeInMillis();
				startDate.setText(dateString);
				break;
            case END_DATE:
            	strEndDate=""+cal.getTimeInMillis();
            	endDate.setText(dateString);
				break;
			default:
				break;
			}
			// set selected date into textview
			/*tvDisplayDate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));*/

			// set selected date into datepicker also
			//dpResult.init(year, month, day, null);

		}
	};
	private boolean checkDate() {
		boolean check=false;
		if (strStartDate!=null&&strEndDate!=null) {
			long start=Long.parseLong(strStartDate);
			long end=Long.parseLong(strEndDate);
			if (end>start) {
				check=true;
			}
			
		}
		
		return check;
	}
private void saveDealToServer(final Deals deals) {
  new AsyncTask<Void, Void, Void>() {
	  String responseData;
	  
		  protected void onPreExecute() {
			  NotificationUtils.showProgressDialog(dashboardActivity, "Please wait",
						"Adding deal..");
		  };
		  protected void onPostExecute(Void result) {
			  NotificationUtils.dismissProgressDialog();
			  try {
				Gson gson = new Gson();
				  Login login = gson.fromJson(responseData, Login.class);

					if (null != login) {
						if (login.getCode().equalsIgnoreCase(
								"saveDeal001")) {
							NotificationUtils.showNotificationToast(dashboardActivity,
									login.getMessage());
							cleareData();
							Intent intent = dashboardActivity.getIntent();
							dashboardActivity.finish();
							dashboardActivity.startActivity(intent);
						}else {
							NotificationUtils.showNotificationToast(dashboardActivity,
									login.getMessage());
						}
					}else {
						NotificationUtils.showNotificationToast(dashboardActivity,
								"Server not responds");
					}
			} catch (JsonSyntaxException e) {
				NotificationUtils.showNotificationToast(dashboardActivity,
						"Server not responds");
			} 
		  };
		  @Override 
		  protected Void doInBackground(Void... arg0) {
			  try {
				  
				  HttpClient httpclient = new DefaultHttpClient(); 
				  HttpPost httppost = new HttpPost(WebServiceConstants.ADD_DEAL); 
				   MultipartEntity entity = new MultipartEntity();
				   String s=new Gson().toJson(deals);
				  entity.addPart("deal", new StringBody(s));
				  entity.addPart("mainImage", new FileBody(new File(mainImagePath)));
				  entity.addPart("images" , new FileBody(new File(image1Path)));
				  entity.addPart("images" , new FileBody(new File(image2Path)));
				  entity.addPart("images" , new FileBody(new File(image3Path)));
				  
				  httppost.setEntity(entity);
				  HttpResponse response =  httpclient.execute(httppost); 
				  HttpEntity getresponse = response.getEntity(); 
				   responseData = EntityUtils.toString(getresponse); 
				  Log.e("UploadFile", responseData);
				
				  
				  
			}catch (Exception e) {
				
			}
		  
		  
		 
		  
		  return null; } }.execute();
}

private void cleareData() {
	strTitle = "";
	title.setText(strTitle);
	
	strSubTitle = "";
	subTitle.setText(strSubTitle);
	
	strLocation="";
	location.setText(strLocation);
	
	strDiscription ="";
	discription.setText(strDiscription);
	
	strOriginalPrice = "";
	originalPrice.setText(strOriginalPrice);
	
	strNewPrice = "";
	newPrice.setText(strNewPrice);
	
	
	
	strLatitude = "";
	latitude.setText(strLatitude);
	
	strLongitude = "";
	longitude.setText(strLongitude);
	/*
	 * if (dealType.isChecked()) { strDealType="0"; }
	 */
	strOffer1 = "";
	offer1.setText(strOffer1);
	
	strOffer2 = "";
	offer2.setText(strOffer2);
	
	strOffer3 = "";
	offer3.setText(strOffer3);
	mainImagePath="";
    image1Path="";
	image2Path="";
	image3Path="";
	
	mainImageView.setImageBitmap(null);
	mainImageView.setBackgroundResource(R.drawable.logo);
	
	imageView1.setImageBitmap(null);
	imageView1.setBackgroundResource(R.drawable.logo);
	
	imageView2.setImageBitmap(null);
	imageView2.setBackgroundResource(R.drawable.logo);
	
	imageView3.setImageBitmap(null);
	imageView3.setBackgroundResource(R.drawable.logo);
}
}

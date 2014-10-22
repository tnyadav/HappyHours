package com.happyhours.activity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.app.happyhours.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.happyhours.model.Category;
import com.happyhours.model.DealOffers;
import com.happyhours.model.Deals;
import com.happyhours.model.Login;
import com.happyhours.util.CustomDateTimePicker;
import com.happyhours.util.NotificationUtils;
import com.happyhours.util.TAListener;
import com.jainbooks.web.TAWebServiceAsyncTask;
import com.jainbooks.web.WebServiceConstants;

public class DealEntryActivity extends BaseActivity implements
ConnectionCallbacks,
OnConnectionFailedListener,
LocationListener{
	private static final int MAIN_IMAGE = 100;
	private static final int IMAGE1 = 101;
	private static final int IMAGE2 = 102;
	private static final int IMAGE3 = 103;
	private static final int DATE_DIALOG_ID = 999;
	private static final int START_DATE = 104;
	private static final int END_DATE = 105;
	private static final int CHANGE_ADDRESS = 106;
//	private int date=0;
	private int year;
	private int month;
	private int day;
	private LocationClient mLocationClient;
	private EditText title, subTitle, location,discription, originalPrice, newPrice,
			startDate, endDate, latitude, longitude, offer1, offer2, offer3;
	private String strTitle, strSubTitle, strLocation,strDiscription, strOriginalPrice,
			strNewPrice, strStartDate, strEndDate, strLatitude, strLongitude,
			strOffer1, strOffer2, strOffer3, mainImagePath, image1Path,
			image2Path, image3Path;
	//private CheckBox dealType;
	private Button mainImage, image1, image2, image3,submit,changeAddress;
	private ImageButton btnStartDate,btnEndDate;
	private ImageView mainImageView, imageView1, imageView2, imageView3;
	private Spinner spinner;
	private Activity dashboardActivity;
	private boolean isLocationAdded=false;
	private static final LocationRequest REQUEST = LocationRequest.create()
	            .setInterval(5000)         // 5 seconds
	            .setFastestInterval(16)    // 16ms = 60fps
	            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	List<Category> categories;
@Override
@SuppressLint("NewApi")
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_deal_entry);
	final Calendar c = Calendar.getInstance();
	year = c.get(Calendar.YEAR);
	month = c.get(Calendar.MONTH);
	day = c.get(Calendar.DAY_OF_MONTH);
	dashboardActivity=this;
	setupUiComponent();
}



	void setupUiComponent() {
		title = (EditText) findViewById(R.id.title);
		subTitle = (EditText) findViewById(R.id.subTitle);
		location = (EditText) findViewById(R.id.location);
		discription = (EditText) findViewById(R.id.discription);
		originalPrice = (EditText) findViewById(R.id.originalPrice);
		newPrice = (EditText) findViewById(R.id.newPrice);
		startDate = (EditText) findViewById(R.id.startDate);
		endDate = (EditText) findViewById(R.id.endDate);
		latitude = (EditText) findViewById(R.id.latitude);
		longitude = (EditText) findViewById(R.id.longitude);
		//dealType = (CheckBox) findViewById(R.id.dealType);
		offer1 = (EditText) findViewById(R.id.offer1);
		offer2 = (EditText) findViewById(R.id.offer2);
		offer3 = (EditText) findViewById(R.id.offer3);

		mainImageView = (ImageView) findViewById(R.id.mainImageView);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);

		mainImage = (Button) findViewById(R.id.mainImage);
		mainImage.setOnClickListener(onClickListener);
		image1 = (Button) findViewById(R.id.image1);
		image1.setOnClickListener(onClickListener);
		image2 = (Button) findViewById(R.id.image2);
		image2.setOnClickListener(onClickListener);
		image3 = (Button) findViewById(R.id.image3);
		image3.setOnClickListener(onClickListener);
		
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(dashboardActivity, MyLocationDemoActivity.class));
				submitDeal();
				
			}
		});
		changeAddress=(Button)findViewById(R.id.changeAddress);
		changeAddress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(dashboardActivity,
						MyLocationDemoActivity.class), CHANGE_ADDRESS);
			}
		});
		btnStartDate=(ImageButton)findViewById(R.id.btnStartDate);
		btnStartDate.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
 
		      showDateTimeDialog(START_DATE);
				//date=START_DATE;
				
			}
 
		});
		btnEndDate=(ImageButton)findViewById(R.id.btnEndDate);
		btnEndDate.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
 
				showDateTimeDialog(END_DATE);
				//date=END_DATE;;
				
 
			}
 
		});
		spinner=(Spinner)findViewById(R.id.category);
		
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
		Deals deals = new Deals();
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
		if (categories!=null&&categories.size()>0) {
			Category category=(Category) spinner.getSelectedItem();
			deals.setDealCategory(category);
		}else {
			NotificationUtils.showNotificationToast(dashboardActivity,
					"No deal category selected");
			return;
		}
		
		deals.setTitle(strTitle);
		deals.setSubTitle(strSubTitle);
		deals.setDescription(strDiscription);
		double dOriginal=Long.parseLong(strOriginalPrice);
		double dNewpice=Long.parseLong(strNewPrice);
		
		double diff=dOriginal-dNewpice;
		
		double Percentage = (diff*100)/dOriginal;

		deals.setDiscount(""+Math.round(Percentage));
		deals.setLocation(strLocation);
		deals.setOriginalPrice(strOriginalPrice);
		deals.setNewPrice(strNewPrice);
		deals.setStartDate(strStartDate);
		deals.setEndDate(strEndDate);
		deals.setLatitude(strLatitude);
		deals.setLongitude(strLongitude);
		deals.setDealType(false);
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
			Bitmap bitmap = null;
			if (requestCode!=CHANGE_ADDRESS) {
			 path = null;
		 	 Uri currImageURI = data.getData();
			 bitmap = MediaStore.Images.Thumbnails.getThumbnail(
					dashboardActivity.getContentResolver(),
					Long.parseLong(currImageURI.getLastPathSegment()),
					MediaStore.Images.Thumbnails.MICRO_KIND, null);
			File file = new File(getRealPathFromURI(currImageURI));

			if (file.exists()) {
				path = file.getAbsolutePath();
				// NotificationUtils.showNotificationToast(dashboardActivity,
				// path);
			}
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
			case CHANGE_ADDRESS:
				String stAddress=data.getStringExtra(MyLocationDemoActivity.ADDRESS);
				String stLatitude=data.getStringExtra(MyLocationDemoActivity.LATITUDE);
				String stLongitude=data.getStringExtra(MyLocationDemoActivity.LONGITUDE);
				location.setText(stAddress);
				latitude.setText(stLatitude);
				longitude.setText(stLongitude);
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

	private void showDateTimeDialog(final int id) {

		CustomDateTimePicker custom;
		custom = new CustomDateTimePicker(dashboardActivity,
				new CustomDateTimePicker.ICustomDateTimeListener() {
					@Override
					public void onSet(Dialog dialog, Calendar calendar,
							Date dateSelected, int year, String monthFullName,
							String monthShortName, int monthNumber, int date,
							String weekDayFullName, String weekDayShortName,
							int hour24, int hour12, int min, int sec,
							String AM_PM) {
						
						
						String dateString=year + "-" + (monthNumber + 1) + "-"
								+ calendar.get(Calendar.DAY_OF_MONTH)
								+ " " + hour12 + ":" + min;
						switch (id) {
						case START_DATE:
							strStartDate=""+calendar.getTimeInMillis();
							startDate.setText(dateString);
							break;
			            case END_DATE:
			            	strEndDate=""+calendar.getTimeInMillis();
			            	endDate.setText(dateString);
							break;
						default:
							break;
						}
						
						/*editText.setText(year + "-" + (monthNumber + 1) + "-"
								+ calendarSelected.get(Calendar.DAY_OF_MONTH)
								+ " " + hour12 + ":" + min);*/
					}

					@Override
					public void onCancel() {
					}
				});
		
		custom.set24HourFormat(true);
	    custom.setDate(Calendar.getInstance());
		custom.showDialog();

	}


	
	
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
	isLocationAdded=false;
	mainImageView.setImageBitmap(null);
	mainImageView.setBackgroundResource(R.drawable.logo);
	
	imageView1.setImageBitmap(null);
	imageView1.setBackgroundResource(R.drawable.logo);
	
	imageView2.setImageBitmap(null);
	imageView2.setBackgroundResource(R.drawable.logo);
	
	imageView3.setImageBitmap(null);
	imageView3.setBackgroundResource(R.drawable.logo);
}
@Override
protected void onResume() {
    super.onResume();
    setUpLocationClientIfNeeded();
    mLocationClient.connect();
}

@Override
public void onPause() {
    super.onPause();
    if (mLocationClient != null) {
        mLocationClient.disconnect();
    }
}

private void setUpLocationClientIfNeeded() {
    if (mLocationClient == null) {
        mLocationClient = new LocationClient(
                getApplicationContext(),
                this,  // ConnectionCallbacks
                this); // OnConnectionFailedListener
    }
}

@Override
public void onLocationChanged(Location location) {
	if (location!=null&&!isLocationAdded) {
		latitude.setText(""+location.getLatitude());
		longitude.setText(""+location.getLongitude());
		isLocationAdded=true;
		(new GetAddressTask(DealEntryActivity.this)).execute(new LatLng(location.getLatitude(),location.getLongitude()));
		
	}
	
}


@Override
public void onConnectionFailed(ConnectionResult arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void onConnected(Bundle arg0) {
	 mLocationClient.requestLocationUpdates(
             REQUEST,
             this);  // LocationListener
}


@Override
public void onDisconnected() {
	// TODO Auto-generated method stub
	
}
private class GetAddressTask extends AsyncTask<LatLng, Void, String> {
	Context mContext;

	public GetAddressTask(Context context) {
		super();
		mContext = context;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub// TODO Auto-generated method stub
		super.onPreExecute();
		NotificationUtils.showProgressDialog(mContext,"Pleasewait", "Getting Location");
	}
	  @Override
        protected void onPostExecute(String address) {
           NotificationUtils.dismissProgressDialog();
           location.setText(address);
           getCategory();
        }

	/**
	 * Get a Geocoder instance, get the latitude and longitude look up the
	 * address, and return it
	 * 
	 * @params params One or more Location objects
	 * @return A string containing the address of the current location, or
	 *         an empty string if no address can be found, or an error
	 *         message
	 */
	@Override
	protected String doInBackground(LatLng... params) {
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		// Get the current location from the input parameter list
		LatLng loc = params[0];
		// Create a list to contain the result address
		List<Address> addresses = null;
		try {
			/*
			 * Return 1 address.
			 */
			addresses = geocoder.getFromLocation(loc.latitude,
					loc.longitude, 1);
		} catch (IOException e1) {
			Log.e("LocationSampleActivity",
					"IO Exception in getFromLocation()");
			e1.printStackTrace();
			return ("Unable to get address");
		} catch (IllegalArgumentException e2) {
			// Error message to post in the log
			String errorString = "Illegal arguments "
					+ Double.toString(loc.latitude) + " , "
					+ Double.toString(loc.longitude)
					+ " passed to address service";
			Log.e("LocationSampleActivity", errorString);
			e2.printStackTrace();
			return ("Unable to get address");
		}
		// If the reverse geocode returned an address
		if (addresses != null && addresses.size() > 0) {
			// Get the first address
			Address address = addresses.get(0);
			/*
			 * Format the first line of address (if available), city, and
			 * country name.
			 */
			String addressText = String.format(
					"%s, %s, %s",
					// If there's a street address, add it
					address.getMaxAddressLineIndex() > 0 ? address
							.getAddressLine(0) : "",
					// Locality is usually a city
					address.getLocality(),
					// The country of the address
					address.getCountryName());
			// Return the text
			return addressText;
		} else {
			return "Unable to get address";
		}
	}

}
private void getCategory() {
	TAListener taListener=new TAListener() {
		
		@Override
		public void onTaskFailed(Bundle argBundle) {
		NotificationUtils.showNotificationToast(dashboardActivity, "Server not responds");
			
		}
		
		@Override
		public void onTaskCompleted(Bundle argBundle) {
			String responseJSON = argBundle
					.getString(TAListener.LISTENER_BUNDLE_STRING_1);
			 Type type = new TypeToken<List<Category>>() {}.getType();
			 Gson gson=new Gson();
			
			try {
				categories= gson.fromJson(responseJSON, type);
				if (categories!=null&&categories.size()>0) {
					spinner.setAdapter(new ArrayAdapter<Category>(activity,
			                android.R.layout.simple_spinner_dropdown_item,
			                categories));
				}
				
			} catch (JsonSyntaxException e) {
				Log.e("****", ""+e);
				
			}
			
		}
		};
		new TAWebServiceAsyncTask(dashboardActivity, null, taListener,
				WebServiceConstants.GET_DEAL_CATEGORY, false)
				.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
}
}

package activity;






import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mode.City;
import mode.MyCity;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sort.CharacterParser;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.theweather.R;

import fragment.WeatherFragment;

public class WeatherActivity extends FragmentActivity {
	ImageView cityImageView;
	ViewPager viewPager;
	List<Fragment> fragments =new ArrayList<Fragment>();
	int currentab=-1;
	int screenWidth;
	MyFrageStatePagerAdapter adapter;
	private CharacterParser characterParser;
	int mposition=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		
		SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
		
		
		Bundle bundle = new Bundle();
		bundle=this.getIntent().getExtras();
		if(bundle!=null){
	    mposition = bundle.getInt("Position");
		}
		
		cityImageView = (ImageView)findViewById(R.id.title_city_manager);
		cityImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    Intent intent = new Intent(WeatherActivity.this,MyCityActivity.class);
			    startActivity(intent);
			    
			}
		});
		
		characterParser = CharacterParser.getInstance();
		
		viewPager = (ViewPager)findViewById(R.id.viewpager);
		// new httpgetcurrentcity();
		int n = 0;
		 for(City city:MyCity.getAllCity()){
			 if(city.getName().equals("����"))
				 n=1;
			      
		 }
		 if(n==0){
			 City acity = new City();
			   acity.setName("����");
				acity.setSortLetters("c");
				MyCity.addCity(acity);
		 }
		if(MyCity.getAllCity()!=null){
		for(City city:MyCity.getAllCity()){
			WeatherFragment  cityFragment = new WeatherFragment(city.getName());
			fragments.add(cityFragment);
		}
		}
		if(fragments==null){
			Log.e("ffff", "fragments is null");
		}
		adapter = new MyFrageStatePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(mposition);
		
	}
	   class MyFrageStatePagerAdapter extends FragmentPagerAdapter  
	    {  
	  
	        public MyFrageStatePagerAdapter(android.support.v4.app.FragmentManager fm)   
	        {  
	            super(fm);  
	        }  
	  
	        @Override  
	        public Fragment getItem(int position) {  
	            return fragments.get(position);  
	        }  
	  
	        @Override  
	        public int getCount() {  
	            return fragments.size();  
	        }  
	          
	        /** 
	         * ÿ�θ������ViewPager�����ݺ󣬵��øýӿڣ��˴���д��Ҫ��Ϊ���õ�����ť�ϲ�ĸ��ǲ��ܹ���̬���ƶ� 
	         */  
	        @Override  
	        public void finishUpdate(ViewGroup container)   
	        {  
	            super.finishUpdate(container);//��仰Ҫ������ǰ�棬����ᱨ��  
	            //��ȡ��ǰ����ͼ��λ��ViewGroup�ĵڼ���λ�ã��������¶�Ӧ�ĸ��ǲ����ڵ�λ��  
//	            int currentItem=viewPager.getCurrentItem();  
//	            if (currentItem==currentab)  
//	            {  
//	                return ;  
//	            }  
//	           // imageMove(viewPager.getCurrentItem());  
//	            currentab=viewPager.getCurrentItem();  
	        }  
	          
	    }  
	      
//	    /** 
//	     * �ƶ����ǲ� 
//	     * @param moveToTab Ŀ��Tab��Ҳ����Ҫ�ƶ����ĵ���ѡ�ť��λ�� 
//	     * ��һ��������ť��Ӧ0���ڶ�����Ӧ1���Դ����� 
//	     */  
//	    private void imageMove(int moveToTab)  
//	    {  
//	        int startPosition=0;  
//	        int movetoPosition=0;  
//	          
//	        startPosition=currentab*(screenWidth/4);  
//	        movetoPosition=moveToTab*(screenWidth/4);  
//	        //ƽ�ƶ���  
//	        TranslateAnimation translateAnimation=new TranslateAnimation(startPosition,movetoPosition, 0, 0);  
//	        translateAnimation.setFillAfter(true);  
//	        translateAnimation.setDuration(200);  
//	        //imageviewOvertab.startAnimation(translateAnimation);  
//	    }
	   @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		fragments.clear();
		for(City city:MyCity.getAllCity()){
			WeatherFragment  cityFragment = new WeatherFragment(city.getName());
			fragments.add(cityFragment);
		}
		adapter.notifyDataSetChanged();
	}
	   
	   
	   public class httpgetcurrentcity extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			 String city = "";
		        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		        GsmCellLocation glc = (GsmCellLocation) telManager.getCellLocation();
		        
		        if (glc != null){
		            int cid = glc.getCid(); // value ��վID��
		            int lac = glc.getLac();// д���������
		            String strOperator = telManager.getNetworkOperator();
		            int mcc = Integer.valueOf(strOperator.substring(0, 3));// д�뵱ǰ���д���
		            int mnc = Integer.valueOf(strOperator.substring(3, 5));// д���������
		            String getNumber = "";
		            getNumber += ("cid:" + cid + "\n");
		            getNumber += ("cid:" + lac + "\n");
		            getNumber += ("cid:" + mcc + "\n");
		            getNumber += ("cid:" + mnc + "\n");
		            DefaultHttpClient client = new DefaultHttpClient();
		            BasicHttpParams params1 = new BasicHttpParams();
		            HttpConnectionParams.setSoTimeout(params1, 20000);
		            HttpPost post = new HttpPost("http://www.google.com/loc/json");
		            
		            try{
		                JSONObject jObject = new JSONObject();
		                jObject.put("version", "1.1.0");
		                jObject.put("host", "maps.google.com");
		                jObject.put("request_address", true);
		                if (mcc == 460)
		                    jObject.put("address_language", "zh_CN");
		                else
		                    jObject.put("address_language", "en_US");

		                JSONArray jArray = new JSONArray();
		                JSONObject jData = new JSONObject();
		                jData.put("cell_id", cid);
		                jData.put("location_area_code", lac);
		                jData.put("mobile_country_code", mcc);
		                jData.put("mobile_network_code", mnc);
		                jArray.put(jData);
		                jObject.put("cell_towers", jArray);
		                StringEntity se = new StringEntity(jObject.toString());
		                post.setEntity(se);

		                HttpResponse resp = client.execute(post);
		                BufferedReader br = null;
		                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
		                    br = new BufferedReader(new InputStreamReader(resp
		                            .getEntity().getContent()));
		                    StringBuffer sb = new StringBuffer();

		                    String result = br.readLine();
		                    while (result != null){
		                        sb.append(getNumber);
		                        sb.append(result);
		                        result = br.readLine();
		                    }
		                    String s = sb.toString();
		                    s = s.substring(s.indexOf("{"));
		                    JSONObject jo = new JSONObject(s);
		                    JSONObject arr = jo.getJSONObject("location");
		                    JSONObject address = arr.getJSONObject("address");
		                    city = address.getString("city");
		                }
		            }
		            catch (JSONException e){
		                e.printStackTrace();
		            }
		            catch (UnsupportedEncodingException e){
		                e.printStackTrace();
		            }
		            catch (ClientProtocolException e){
		                e.printStackTrace();
		            }
		            catch (IOException e){
		                e.printStackTrace();
		            }
		            finally{
		                post.abort();
		                client = null;
		            }
		        }
		        return city;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			City mcity = new City();
			mcity.setName("����");
			mcity.setSortLetters("c");
			MyCity.addCity(mcity);
		}
		   
	   }
	   
//	   public String getCurrentCityName(){
//	        String city = "";
//	        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//	        GsmCellLocation glc = (GsmCellLocation) telManager.getCellLocation();
//	        
//	        if (glc != null){
//	            int cid = glc.getCid(); // value ��վID��
//	            int lac = glc.getLac();// д���������
//	            String strOperator = telManager.getNetworkOperator();
//	            int mcc = Integer.valueOf(strOperator.substring(0, 3));// д�뵱ǰ���д���
//	            int mnc = Integer.valueOf(strOperator.substring(3, 5));// д���������
//	            String getNumber = "";
//	            getNumber += ("cid:" + cid + "\n");
//	            getNumber += ("cid:" + lac + "\n");
//	            getNumber += ("cid:" + mcc + "\n");
//	            getNumber += ("cid:" + mnc + "\n");
//	            DefaultHttpClient client = new DefaultHttpClient();
//	            BasicHttpParams params = new BasicHttpParams();
//	            HttpConnectionParams.setSoTimeout(params, 20000);
//	            HttpPost post = new HttpPost("http://www.google.com/loc/json");
//	            
//	            try{
//	                JSONObject jObject = new JSONObject();
//	                jObject.put("version", "1.1.0");
//	                jObject.put("host", "maps.google.com");
//	                jObject.put("request_address", true);
//	                if (mcc == 460)
//	                    jObject.put("address_language", "zh_CN");
//	                else
//	                    jObject.put("address_language", "en_US");
//
//	                JSONArray jArray = new JSONArray();
//	                JSONObject jData = new JSONObject();
//	                jData.put("cell_id", cid);
//	                jData.put("location_area_code", lac);
//	                jData.put("mobile_country_code", mcc);
//	                jData.put("mobile_network_code", mnc);
//	                jArray.put(jData);
//	                jObject.put("cell_towers", jArray);
//	                StringEntity se = new StringEntity(jObject.toString());
//	                post.setEntity(se);
//
//	                HttpResponse resp = client.execute(post);
//	                BufferedReader br = null;
//	                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//	                    br = new BufferedReader(new InputStreamReader(resp
//	                            .getEntity().getContent()));
//	                    StringBuffer sb = new StringBuffer();
//
//	                    String result = br.readLine();
//	                    while (result != null){
//	                        sb.append(getNumber);
//	                        sb.append(result);
//	                        result = br.readLine();
//	                    }
//	                    String s = sb.toString();
//	                    s = s.substring(s.indexOf("{"));
//	                    JSONObject jo = new JSONObject(s);
//	                    JSONObject arr = jo.getJSONObject("location");
//	                    JSONObject address = arr.getJSONObject("address");
//	                    city = address.getString("city");
//	                }
//	            }
//	            catch (JSONException e){
//	                e.printStackTrace();
//	            }
//	            catch (UnsupportedEncodingException e){
//	                e.printStackTrace();
//	            }
//	            catch (ClientProtocolException e){
//	                e.printStackTrace();
//	            }
//	            catch (IOException e){
//	                e.printStackTrace();
//	            }
//	            finally{
//	                post.abort();
//	                client = null;
//	            }
//	        }
//	        return city;
//	    }

}

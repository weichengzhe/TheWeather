package fragment;


import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theweather.R;
import com.example.theweather.R.string;

public class WeatherFragment extends Fragment{
	 private final int  showtextString = 0;
	 View view;
	 private TextView tv_oneweek,tv_onetmp,tv_oneclimate, tv_onewind,tv_twoweek,tv_twotmp,tv_twoclimate, tv_twowind,
	 tv_threeweek,tv_threetmp,tv_threelimate, tv_threewind;
	 private ImageView ig_today,ig_one,ig_two,ig_three;
	public  TextView tv_city,tv_time,tv_humidity,tv_week_today,tv_temperature,tv_climate,tv_wind;
	private String mcity_name=null;
    public WeatherFragment (String cityname){
    	this.mcity_name=cityname ;
    	
    }
    public WeatherFragment getintance(String cityname){
    	return new WeatherFragment(cityname);
    }
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view = inflater.inflate(R.layout.weatherdetail_fragment, null);
		
		
		init();
		new httpgetweather().execute(mcity_name);
		tv_city.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//sendRequestWithHttpClient();	
			}
		});
		return view ;
	}
	
private void init() {
	// TODO Auto-generated method stub
	tv_city =(TextView) view.findViewById(R.id.city);
	tv_time = (TextView)view.findViewById(R.id.time);
	tv_humidity = (TextView)view.findViewById(R.id.humidity);
	tv_week_today = (TextView)view.findViewById(R.id.week_today);
	tv_temperature = (TextView)view.findViewById(R.id.temperature);
	tv_climate = (TextView)view.findViewById(R.id.climate);
	tv_wind = (TextView)view.findViewById(R.id.wind);
	tv_oneclimate = (TextView)view.findViewById(R.id.oneclimate);
	tv_onetmp = (TextView)view.findViewById(R.id.onetemp);
	tv_oneweek = (TextView)view.findViewById(R.id.oneweek);
	tv_onewind = (TextView)view.findViewById(R.id.onewind);
	
	tv_twoclimate = (TextView)view.findViewById(R.id.twoclimate);
	tv_twotmp = (TextView)view.findViewById(R.id.twotemp);
	tv_twoweek = (TextView)view.findViewById(R.id.twoweek);
	tv_twowind = (TextView)view.findViewById(R.id.twowind);
	
	tv_threelimate = (TextView)view.findViewById(R.id.threeclimate);
	tv_threetmp = (TextView)view.findViewById(R.id.threetemp);
	tv_threeweek = (TextView)view.findViewById(R.id.threeweek);
	tv_threewind = (TextView)view.findViewById(R.id.threewind);
	
	ig_today = (ImageView)view.findViewById(R.id.weather_img);
	ig_one = (ImageView)view.findViewById(R.id.oneimage);
	ig_two = (ImageView)view.findViewById(R.id.twoimage);
	ig_three = (ImageView)view.findViewById(R.id.threeimage);

	
}

//	private void sendRequestWithHttpClient(){
//  	  new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//			    try {
//					HttpClient httpClient = new DefaultHttpClient();
//					HttpGet httpGet = new HttpGet("http://op.juhe.cn/onebox/weather/query?cityname=%E9%87%8D%E5%BA%86&dtype=&key=07dc2094c8aa260825e64b6f45b1cf00");
//					HttpResponse response = httpClient.execute(httpGet);
//					if(response.getStatusLine().getStatusCode()==200){
//						HttpEntity entity = response.getEntity();
//						String res = EntityUtils.toString(entity, "utf-8");
//						Message message = new Message();
//						message.what = showtextString;
//						message.obj=res.toString();
//						handler.sendMessage(message);
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//			}
//		}).start();
//    }
	
	public class httpgetweather extends AsyncTask<String, Void, String>{
		String city;
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			 city = params[0];
			String url = "http://op.juhe.cn/onebox/weather/query?cityname="+city+"&key= 07dc2094c8aa260825e64b6f45b1cf00";
			 //HttpGet httpRequest = new HttpGet("http://op.juhe.cn/onebox/weather/query?cityname=%E9%87%8D%E5%BA%86&dtype=&key=07dc2094c8aa260825e64b6f45b1cf00");  
		        try {  
		        	HttpClient httpClient = new DefaultHttpClient();
					//HttpGet httpGet = new HttpGet("http://op.juhe.cn/onebox/weather/query?cityname=%E9%87%8D%E5%BA%86&dtype=&key=07dc2094c8aa260825e64b6f45b1cf00");
		        	HttpGet httpGet = new HttpGet(url);
					HttpResponse response = httpClient.execute(httpGet);
					if(response.getStatusLine().getStatusCode()==200){
						HttpEntity entity = response.getEntity();
						String res = EntityUtils.toString(entity, "utf-8");
						return res;
		            } 
		        } catch (IOException e) {  
		            e.printStackTrace();  
		            Log.e("ffff",e.toString());
		        }  
		        
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			 if(result!=null){
				 try {
					 //Toast.makeText(getActivity(), "fffff", Toast.LENGTH_LONG).show();
					JSONObject jsonObject = new JSONObject(result);
					//int resultcode = jsonObject.getInt("code");
					
						//Toast.makeText(getActivity(), "fffff", Toast.LENGTH_LONG).show();
						 JSONObject reJsonObject = jsonObject.getJSONObject("result");
						 JSONObject dataJsonObject = reJsonObject.getJSONObject("data");
						 JSONObject realtimeJsonObject = dataJsonObject.getJSONObject("realtime");
						 JSONObject weatherJsonObject = realtimeJsonObject.getJSONObject("weather");
						 JSONObject windJsonObject = realtimeJsonObject.getJSONObject("wind");
						 JSONArray  nextweaJsonArray = dataJsonObject.getJSONArray("weather");
						 
						 String weekString = "";
					        final String dayNames[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
					        Calendar calendar = Calendar.getInstance();
					        Date date = new Date(System.currentTimeMillis());
					        calendar.setTime(date);
					        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
					        weekString = dayNames[dayOfWeek - 1];
					       
						 
						 tv_city.setText(city.toString());
						 tv_time.setText(realtimeJsonObject.getString("time").toString());
						 tv_humidity.setText(weatherJsonObject.getString("humidity").toString());
						 tv_week_today.setText(weekString.toString());
						 tv_temperature.setText(weatherJsonObject.getString("temperature").toString());
						 tv_climate.setText(weatherJsonObject.getString("info").toString());
						 tv_wind.setText(windJsonObject.getString("direct").toString()+windJsonObject.getString("power").toString());
						 ig_today.setImageResource(getimage(weatherJsonObject.getString("info")));
						 
				            JSONArray jsonArray = dataJsonObject.getJSONArray("weather");
				             
				            JSONObject oneJsonObject = jsonArray.getJSONObject(0);
				             
				            JSONObject oneinfo2 = oneJsonObject.getJSONObject("info");
				             
				           
				           String oneinfo =  oneinfo2.getString("day");
				           
				             
				         
				            String [] stringArr= oneinfo.split(",");  //注意分隔符是需要转译滴...
				             
				            String onetemp1 = stringArr[0].replace("[", "");
				            String oneweather1 = stringArr[1];
				            String onetemp2 = stringArr[2];
				            String onewind  = stringArr[4];
				           
				            
				            JSONObject twoJsonObject = jsonArray.getJSONObject(1);
				            JSONObject twoinfo2 = twoJsonObject.getJSONObject("info");
				             
					          
					           String twoinfo =  oneinfo2.getString("day");
					             
					         
					            String [] twostringArr= oneinfo.split(",");  //注意分隔符是需要转译滴...
					             
					            String twotemp1 = twostringArr[0].replace("[", "");
					            String twoweather1 = twostringArr[1];
					            String twotemp2 = twostringArr[2];
					            String twowind  = twostringArr[4];
					            
					         
					            JSONObject threeJsonObject = jsonArray.getJSONObject(2);
					            JSONObject threeinfo2 = threeJsonObject.getJSONObject("info");
					             
						           
						           String threeinfo =  threeinfo2.getString("day");
						             
						         
						            String [] threestringArr= threeinfo.split(",");  //注意分隔符是需要转译滴...
						             
						            String threetemp1 = threestringArr[0].replace("[", "");
						            String threeweather1 = threestringArr[1];
						            String threetemp2 = threestringArr[2];
						            String threewind  = threestringArr[4];
				            tv_oneweek.setText(dayNames[dayOfWeek ]);
				            tv_onetmp.setText(onetemp1+"-"+onetemp2);
				            tv_oneclimate.setText(oneweather1);
				            tv_onewind.setText(onewind);
				            ig_one.setImageResource(getimage(oneweather1.substring(1,oneweather1.length()-1)));
				            
				            tv_twoweek.setText(dayNames[dayOfWeek +1]);
				            tv_twotmp.setText(twotemp1+"-"+twotemp2);
				            tv_twoclimate.setText(twoweather1);
				            tv_twowind.setText(twowind);
				            ig_two.setImageResource(getimage(twoweather1.substring(1,oneweather1.length()-1)));
				            
				            tv_threeweek.setText(dayNames[(dayOfWeek +2)%7]);
				            tv_threetmp.setText(threetemp1+"-"+threetemp2);
				            tv_threelimate.setText(threeweather1);
				            tv_threewind.setText(threewind);
				            ig_three.setImageResource(getimage(threeweather1.substring(1,oneweather1.length()-1)));
						    
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			 }
		}
		public  int getimage (String tinqi ){
			if(tinqi.equals("暴雪")){
				return R.drawable.biz_plugin_weather_baoxue;
			}else if (tinqi.equals("暴雨")) {
				return R.drawable.biz_plugin_weather_baoyu;
				
			} else if (tinqi.equals("小雨")) {
				return R.drawable.biz_plugin_weather_xiaoyu;
			}else if (tinqi.equals("雾")) {
				return R.drawable.biz_plugin_weather_wu;
			}  else if (tinqi.equals("阴")) {
				return R.drawable.biz_plugin_weather_yin;
			}else {
				return R.drawable.biz_plugin_weather_qing;
			}
		}
		
	}

}

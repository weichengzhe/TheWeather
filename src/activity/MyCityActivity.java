package activity;

import java.util.ArrayList;
import java.util.List;

import sort.CharacterParser;

import mode.City;
import mode.MyCity;
import activity.SlideCutListView.RemoveDirection;
import activity.SlideCutListView.RemoveListener;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.theweather.R;

public class MyCityActivity extends Activity implements RemoveListener{
	private SlideCutListView slideCutListView ;
	public ArrayAdapter<String> adapter;
	private List<String> dataSourceList = new ArrayList<String>();
	private ImageView ig_addcity,ig_left;
	private String [] mlists =null;
	private List<City>  mcitylist = new ArrayList<City>();
	private CharacterParser characterParser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mycity_activity);
		ig_left = (ImageView)findViewById(R.id.mycity_igLeft);
		ig_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				mcitylist = filledData(mlists);
//				for(City c:mcitylist){
//					MyCity.removeCity(c);
//				}
//				mlists=null;
//				mcitylist.clear();
				finish();
				
			}
		});
		ig_addcity = (ImageView)findViewById(R.id.ig_addcity);
		ig_addcity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			     Intent intent = new Intent(MyCityActivity.this,AllCityActivity.class);
			     startActivity(intent);
			}
		});
		init();
	}

	private void init() {
		slideCutListView = (SlideCutListView) findViewById(R.id.slideCutListView);
		slideCutListView.setRemoveListener(this);
		
		for(City c:MyCity.getAllCity()){
			dataSourceList.add(c.getName().toString());
		}
		
		adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.list_item, dataSourceList);
		slideCutListView.setAdapter(adapter);
		
		slideCutListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MyCityActivity.this, dataSourceList.get(position), Toast.LENGTH_SHORT).show();
				Intent  intent = new Intent(MyCityActivity.this,WeatherActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("Position", position);
				intent.putExtras(bundle);
			    startActivity(intent);
			}
		});
	}

	
	//滑动删除之后的回调方法
	@Override
	public void removeItem(RemoveDirection direction, int position) {
		String s = adapter .getItem(position);
		adapter.remove(adapter.getItem(position));
		//MyCity.romoveCityFromName(s);
		//mlists[mlists.length]=s;
		
		
		//MyCity.romoveCityFromName(adapter.getItem(position-1));
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		dataSourceList.clear();
		for(City c:MyCity.getAllCity()){
			dataSourceList.add(c.getName().toString());
		}
		adapter.notifyDataSetChanged();
	}
	public void updateUi(){
		adapter.notifyDataSetChanged();
	}
	private List<City> filledData(String [] date){
		List<City> mSortList = new ArrayList<City>();
		
		for(int i=0; i<date.length; i++){
			City sortModel = new City();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}


}

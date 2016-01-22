package activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mode.City;
import mode.MyCity;
import sort.CharacterParser;
import sort.ClearEditText;
import sort.PinyinComparator;
import sort.SideBar;
import sort.SideBar.OnTouchingLetterChangedListener;
import sort.SortAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theweather.R;

public class AllCityActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private String[] mcities={"������","������","������","������","������","������","�Ϸ���","������","������","��ɽ��","������","��ɽ��","������","ͭ����","�ߺ���","������","������","������","��ƽ��","������","������","Ȫ����","������","������","������","������","���ϲ���������","��������","�����","��Ȫ��","������","���Ļ���������","¤����","ƽ����","������","��ˮ��","������","��Ҵ��","������","��ݸ��","��ɽ��","������","��Դ��","������","������","������","ï����","÷����","��Զ��","��ͷ��","��β��","�ع���","������","������","�Ƹ���","տ����","������","��ɽ��","�麣��","��ɫ��","������","������","���Ǹ���","�����","������","�ӳ���","������","������","������","������","������","������","������","��˳��","�Ͻڵ���","������","����ˮ��","ǭ�������嶱��������","ǭ�ϲ���������������","ǭ���ϲ���������������","ͭ�ʵ���","������","������","������","������","������","�е���","������","��ˮ��","�ȷ���","�ػʵ���","ʯ��ׯ��","��ɽ��","��̨��","�żҿ���","������","�ױ���","������","������","������","�����","������","ƽ��ɽ��","�����","����Ͽ��","������","������","������","�����","֣����","�ܿ���","פ�����","������","���˰������","��������","�׸���","�ں���","������","��ľ˹��","ĵ������","��̨����","���������","˫Ѽɽ��","�绯��","������","������","��ʩ����������������","�Ƹ���","��ʯ��","������","������","ʮ����","������","�人��","������","�差��","Т����","�˲���","��ɳ��","������","������","������","������","¦����","������","��̶��","��������������������","������","������","������","�żҽ���","������","�׳���","��ɽ��","������","������","��Դ��","��ƽ��","��ԭ��","ͨ����","�ӱ߳�����������","������","������","���Ƹ���","�Ͼ���","��ͨ��","������","��Ǩ��","̩����","������","������","�γ���","������","����","������","������","������","��������","�Ž���","�ϲ���","Ƽ����","������","������","�˴���","ӥ̶��","��ɽ��","��Ϫ��","������","������","������","��˳��","������","��«����","������","������","�̽���","������","������","Ӫ����","��������","�����׶���","��ͷ��","�����","������˹��","���ͺ�����","���ױ�����","ͨ����","�ں���","�����첼��","���ֹ�����","�˰���","��ԭ��","ʯ��ɽ��","������","������","������","�������������","��������������","��������","���ϲ���������","�����ɹ������������","���ϲ���������","������","��������������","������","������","��Ӫ��","������","������","������","������","�ĳ���","������","�ൺ��","������","̩����","������","Ϋ����","��̨��","��ׯ��","�Ͳ���","������","��ͬ��","������","������","�ٷ���","������","˷����","̫ԭ��","������","��Ȫ��","�˳���","������","������","������","������","ͭ����","μ����","������","������","�Ӱ���","������","���Ӳ���Ǽ��������","������","�ɶ���","������","������","���β���������","�㰲��","��Ԫ��","��ɽ��","��ɽ����������","������","üɽ��","������","�ڽ���","�ϳ���","��֦����","������","�Ű���","�˱���","������","�Թ���","��ɽ��","��������������","�������������","�º���徰����������","�������������","��ӹ���������������","������","������","�ٲ���","ŭ��������������","�ն���","������","��ɽ׳������������","��˫���ɴ���������","��Ϫ��","��ͨ��","������","������","������","����","��ˮ��","������","������","������","̨����","������","��ɽ��","̨����","������","��¡��","̨����","̨����","������","������"};
	private ImageView ig_left;
	/**
	 * ����ת����ƴ������
	 */
	private CharacterParser characterParser;
	private List<City> SourceDateList;
	
	/**
	 * ����ƴ��������ListView�����������
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		ig_left = (ImageView)findViewById(R.id.allcity_igLeft);
		ig_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    finish();	
			    
			}
		});
		initViews();
	}

	private void initViews() {
		//ʵ��������תƴ����
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//�����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//����ĸ�״γ��ֵ�λ��
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
				
			}
		});
		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
				Toast.makeText(getApplication(), ((City)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				MyCity.addCity((City)adapter.getItem(position));
				finish();
			}
		});
		
		SourceDateList = filledData(mcities);
		
		// ����a-z��������Դ����
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		
		
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		
		//�������������ֵ�ĸı�����������
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}


	/**
	 * ΪListView�������
	 * @param date
	 * @return
	 */
	private List<City> filledData(String [] date){
		List<City> mSortList = new ArrayList<City>();
		
		for(int i=0; i<date.length; i++){
			City sortModel = new City();
			sortModel.setName(date[i]);
			//����ת����ƴ��
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<City> filterDateList = new ArrayList<City>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(City sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
}

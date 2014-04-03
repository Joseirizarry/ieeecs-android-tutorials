package com.mmarquezpr.lifecycledemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MainActivity extends Activity {

	
	//Key Value Constants
	private static String onCreateKey = "onCreateKey";
	private static String onStartKey = "onStartKey";
	private static String onResumeKey = "onResumeKey";
	private static String onPauseKey = "onPauseKey";
	private static String onStopKey = "onStopKey";
	private static String onDestroyKey = "onDestroyKey";
	private static String onRestartKey = "onRestartKey";
	
	
	//Lifecycle Demo
	
	//Instance Fields
	private SharedPreferences pref; //Object that References Shared Preferences File in the Application
	private ListView lvLifeCylcleCounter;
	
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Change Tittle for Fun
        this.getActionBar().setTitle("Lifecycle Counter");
        
        //Get Object SharedPreferences Editors always run
        this.pref = this.getPreferences(Context.MODE_PRIVATE);

        //Obtain List View from ID
        this.lvLifeCylcleCounter = (ListView) findViewById(R.id.lv_content);
        
        //Add to Shared Pref Counter
        this.addToCounter(onCreateKey);
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	//Add to Shared Pref Counter
    	this.addToCounter(onStartKey);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	//Add to Shared Pref Counter
    	this.addToCounter(onResumeKey);
        this.updateListView();

    	
    	
    }
    
    
    @Override
    protected void onPause() {
    	super.onPause();
    	
    	this.addToCounter(onPauseKey);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	//Add to Shared Pref Counter
    	this.addToCounter(onStopKey);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	//Add to Shared Pref Counter
    	this.addToCounter(onDestroyKey);
    	
    }
    
    @Override
    protected void onRestart() {
    	super.onRestart();
    	
    	this.addToCounter(onRestartKey);
    }
    
    
    
    
    private boolean addToCounter(String key){
    	
    	SharedPreferences.Editor editor = pref.edit();
    	
    	//Get Integer from Shared Preferences
    		//Remember if no key exist is created with a value of 0.
    	int currentCounter = pref.getInt(key, 0);
    	currentCounter++; //Add One as it has been called on the counter
    	
    	//Add New Key Value
    	editor.putInt(key, currentCounter);
        	
    	//Close file
    	return editor.commit();
    	
    }
    
    private void updateListView(){
    	
    	lvLifeCylcleCounter.setAdapter(new CounterAdapter(this, R.layout.lv_counter_view, this.convertToList(pref)));
    	
    }
    
    
    //Action Bar Related Methods
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    
   @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   switch (item.getItemId()) {
		case R.id.ab_dialog:
			startDialog();
			break;
		case R.id.ab_finish:
            finish();
			//Toast.makeText(this, pref.getString("Key", "Error"), Toast.LENGTH_SHORT).show();
			break;
	
		}
	   
	   return super.onOptionsItemSelected(item);
	}

	private void startDialog() {
		
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("Generated Dialog");
		builder.create().show();
		
	}
    
    
    /* Sub Class */
	public class CounterAdapter extends ArrayAdapter<CounterContainer>{

		
		public CounterAdapter(Context context, int lvCounterView,
				List<CounterContainer> convertToList) {
			super(context, lvCounterView, convertToList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			CounterContainer container = this.getItem(position);
			View row = convertView;
			LayoutInflater inflater = ((Activity) this.getContext()).getLayoutInflater();
		        //Inflate Row
		     row = inflater.inflate(R.layout.lv_counter_view, parent, false);
			
			
			TextView counterName = (TextView) row.findViewById(R.id.lvrow_counter_key);
			TextView counterValue = (TextView) row.findViewById(R.id.lvrow_counter_value);
			
			counterName.setText(container.key);
			counterValue.setText(container.value+"");
			
			
			return row;
		}
		
	}
	
	
	/* Sub Class */
	public class CounterContainer{
		public String key;
		public int value;
		
		public CounterContainer(String key, int value){
			this.key = key;
			this.value = value;
		}
		
	}

	private List<CounterContainer> convertToList(SharedPreferences pref){
		
		Map<String, ?> map = pref.getAll();
		LinkedList<CounterContainer> result = new LinkedList<CounterContainer>();
		
		for(Entry<String, ?> e: map.entrySet() ){
			Integer s = (Integer) e.getValue();
			result.add(new CounterContainer(e.getKey(), s) );
		}
		return result;
	}
    
        
}

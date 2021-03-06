package mmarquezpr.uisample.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ListView listView = (ListView) findViewById(R.id.listViewDemo);
        String[] list = new String[]{
                "Layouts",
                "TextViews",
                "EditText",
                "Button",
                "Spinner",
                "ListView (Current)"
        };
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list){
            @Override
            public boolean isEnabled(int position) {
                if(position == 5 || position == 0 ){
                    return false;
                }
                return true;

            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 1:
                        startActivity(new Intent(getBaseContext(), TextViewActivity.class));
                        break;

                    case 2:
                        startActivity(new Intent(getBaseContext(), EditTextActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getBaseContext(), ButtonActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getBaseContext(), SpinnerAcitivty.class));
                        break;



                }


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

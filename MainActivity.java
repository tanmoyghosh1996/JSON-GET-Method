package com.example.root.jsonusingvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<SetGet> arrayList;
ListView lv;
ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=(ListView)findViewById(R.id.listview);
        pb=(ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        util utl=new util();
        if(utl.isConnected(MainActivity.this))
        {
            loaddatafromurl();
        }
        else{
            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
        }
    }
    
	private void loaddatafromurl()
    {
        String url_login="http://wptrafficanalyzer.in/p/demo1/first.php/countries/";      //URL provide by client
        
		// Request a string response from the provided URL & Formulate the request and handle the response.
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url_login, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jobj1=new JSONObject(response);                  // get JSONObject from JSON file
                    JSONArray jsonArray=jobj1.getJSONArray("countries");        // fetch JSONArray named countries
					arrayList=new ArrayList<SetGet>();
                    for(int i=0;i<jsonArray.length();i++)        // implement for loop for getting countries list data
                    {
                        SetGet setGet=new SetGet();
                        JSONObject jobj=jsonArray.getJSONObject(i);        // create a JSONObject for fetching single user data
                        setGet.setFlag(jobj.getString("flag"));             // fetch data and store it in arraylist
                        setGet.setCountryname(jobj.getString("countryname"));
                        setGet.setLanguage(jobj.getString("language"));
                        setGet.setCapital(jobj.getString("capital"));

                        JSONObject obj = jobj.getJSONObject("currency");       // create a object for getting currency data from jobj
                        setGet.setCurrencyname(obj.getString("currencyname"));

                        arrayList.add(setGet);
                    }

                    if (arrayList.size()>0){
                        lv.setAdapter(new customAdapter());
                        pb.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Volley Response Error",Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"VolleyError"+error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);           // Instantiate the RequestQueue.
        requestQueue.add(stringRequest);                   // Add the request to the RequestQueue.
    }
    
	public class customAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inf=getLayoutInflater();
            View v=inf.inflate(R.layout.list_row,parent,false);
            
			ImageView iv1=(ImageView)v.findViewById(R.id.iv1);
            TextView tv1=(TextView)v.findViewById(R.id.tv1);
            TextView tv2=(TextView)v.findViewById(R.id.tv2);
            TextView tv3=(TextView)v.findViewById(R.id.tv3);
            TextView tv4=(TextView)v.findViewById(R.id.tv4);

            Picasso.with(MainActivity.this).load(arrayList.get(position).getFlag()).into(iv1);
            tv1.setText(arrayList.get(position).getCountryname());     //set variables in TextView's
            tv2.setText(arrayList.get(position).getLanguage());
            tv3.setText(arrayList.get(position).getCapital());
            tv4.setText(arrayList.get(position).getCurrencyname());
            return v;
        }
    }
}
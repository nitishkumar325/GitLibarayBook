package e.nitishkumar.minor_project;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import e.nitishkumar.minor_project.Adapters.RepositoryAdapter;
import e.nitishkumar.minor_project.DataModel.POJOResult;

public class Reposterary_list extends AppCompatActivity {
RecyclerView rv;
ProgressDialog dialog;
RepositoryAdapter Adapter;
android.support.v7.widget.Toolbar toolbar;
CircleImageView circleImageView;
TextView textView;
ArrayList list=new ArrayList();
String string,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reposterary_list);
        toolbar=(Toolbar) findViewById(R.id.mytoolbar1);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        string=getIntent().getStringExtra("data");
        textView=(TextView)findViewById(R.id.user_profile_name);
        circleImageView=(CircleImageView)findViewById(R.id.user_profile_photo);
        rv=(RecyclerView)findViewById(R.id.reporecycler);
        Glide.with(this).load(string.split("_")[0]).into(circleImageView);
        textView.setText(string.split("_")[1]);
        url=string.split("_")[2];
        rv.setLayoutManager(new LinearLayoutManager(this));
        Adapter=new RepositoryAdapter(list,this);
        rv.setAdapter(Adapter);
        getDataFromServer(url);
    }
    public void getDataFromServer(String url)
    {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        System.out.println("urlurl"+url);
        StringRequest myrequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object1=array.getJSONObject(i);
                        System.out.println("myobject"+object1.toString());
                        POJOResult result=new POJOResult();
                        result.setName(object1.getString("name"));
                        result.setLanguage(object1.getString("language"));
                        result.setJsonObject(object1.getJSONObject("owner"));
                        result.setContributor_Url(object1.getString("contributors_url"));
                        result.setDescription(object1.getString("description"));
                        result.setWatchers(Long.parseLong(object1.getString("watchers")));
                        result.setForks_count(Long.parseLong(object1.getString("forks_count")));
                        System.out.println("position"+i+result.toString());
                        list.add(result);
                    }
                    Adapter.notifyDataSetChanged();
                    // mylist.clear();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
                Toast.makeText(Reposterary_list.this,"error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(myrequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}

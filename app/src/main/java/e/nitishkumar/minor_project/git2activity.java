package e.nitishkumar.minor_project;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import e.nitishkumar.minor_project.Adapters.HorizontalAdapter;
import e.nitishkumar.minor_project.DataModel.ContributeClass;

public class git2activity extends AppCompatActivity {
RecyclerView rv;
String string;
Toolbar toolbar;
TextView clickHere;
ProgressDialog dialog;
CircleImageView imageView;
TextView name,description;
String url,html_url;
HorizontalAdapter adapter;
ArrayList<ContributeClass>mylist=new ArrayList<ContributeClass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git2activity);
        toolbar=(Toolbar) findViewById(R.id.mytoolbar1);
        clickHere=(TextView)findViewById(R.id.clickhere);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        string=getIntent().getStringExtra("data");
        imageView=(CircleImageView) findViewById(R.id.profileimage);
        description=(TextView)findViewById(R.id.description);
        name=(TextView)findViewById(R.id.name);
        Glide.with(this).load(string.split("_")[0]).into(imageView);
        if(string.split(("_"))[2]!=null)
        description.setText(string.split("_")[2]);
        name.setText(string.split("_")[1]);
        url=string.split("_")[3];
        html_url=string.split("_")[4];
        rv=(RecyclerView)findViewById(R.id.horizontalscroll);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        if(url!=null)
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
        StringRequest myrequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }

                    JSONArray array=new JSONArray(response);
                    mylist.clear();
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object1=array.getJSONObject(i);
                        ContributeClass result=new ContributeClass();
                        result.setLoginName(object1.getString("login"));
                        result.setAvtarUrl(object1.getString("avatar_url"));
                        result.setRepos_Url(object1.getString("repos_url"));

                        mylist.add(result);
                    }
                    adapter=new HorizontalAdapter(mylist,git2activity.this);
                    rv.setAdapter(adapter);
                    // mylist.clear();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
                Toast.makeText(git2activity.this,"error"+error.getMessage(),Toast.LENGTH_SHORT).show();
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
    public void clickhere(View v)
    { Bundle b1=new Bundle();
    b1.putString("html_url",html_url);
    BlankFragment f=new BlankFragment();
    f.setArguments(b1);
        getSupportFragmentManager().beginTransaction().add(R.id.containner,f).commit();
        System.out.println("html_url"+html_url);
    }
}

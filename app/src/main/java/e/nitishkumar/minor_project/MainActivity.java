package e.nitishkumar.minor_project;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import e.nitishkumar.minor_project.Adapters.HorizontalAdapter;
import e.nitishkumar.minor_project.Adapters.RepositoryAdapter;
import e.nitishkumar.minor_project.DataModel.POJOResult;

public class MainActivity extends AppCompatActivity  {
    TextView textView;
   EditText editText;
   String topic="";
   RepositoryAdapter noticadapter;
   public static RecyclerView rv;
   ProgressDialog dialog;
    List<POJOResult> mylist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.search);
        rv=(RecyclerView)findViewById(R.id.myrecycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        noticadapter=new RepositoryAdapter(mylist,MainActivity.this);
        rv.setAdapter(noticadapter);

        Log.d("get data not invoked","invoked");
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                topic=editText.getText().toString();
                System.out.println("topic"+topic);
                if (actionId == EditorInfo.IME_ACTION_SEARCH&&topic!=null&&!"".equals(topic)) {
                    try {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    String url="https://api.github.com/search/repositories?q="+topic+"+language:HighLevelLanguage&sort=stars&order=desc";
                    getDataFromServer(url);
                    return true;
                }else{
                    Toast.makeText(MainActivity.this,"Search Field Could Not Be Empty",Toast.LENGTH_SHORT).show();
                    try {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                return false;
            }
        });
    }
    public void getDataFromServer(String url)
    {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Fetching Data...");
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
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("items");
                        mylist.clear();
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object1=array.getJSONObject(i);
                        System.out.println("firstobjct"+object1.toString());
                        POJOResult result=new POJOResult();
                        result.setName(object1.getString("name"));
                        result.setLanguage(object1.getString("language"));
                        result.setJsonObject(object1.getJSONObject("owner"));
                        result.setContributor_Url(object1.getString("contributors_url"));
                        result.setDescription(object1.getString("description"));
                        result.setWatchers(Long.parseLong(object1.getString("watchers")));
                        result.setForks_count(Long.parseLong(object1.getString("forks_count")));
                        System.out.println("position"+i+result.toString());
                        mylist.add(result);
                    }
                   noticadapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
                Toast.makeText(MainActivity.this,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue=Volley.newRequestQueue(this);
        queue.add(myrequest);
    }

    public void dilaog1(View view) {
        final Dialog dialog = new Dialog(this);
        String[] label=new String[]{"java","Phython","Android","Ruby","C","C++","DataStructure","xml","web","javascript"};
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.dialoglayout, null, false);
        ImageView imageView=(ImageView)view1.findViewById(R.id.dismiss);
        final RecyclerView rv=(RecyclerView)view1.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv.setAdapter(new HorizontalAdapter(this,label,"Dialog",dialog));
        LinearLayout linearLayout=view1.findViewById(R.id.horizontalscroll);
        ((Activity)this).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view1);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });

    }

}

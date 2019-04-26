package e.nitishkumar.minor_project.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import e.nitishkumar.minor_project.DataModel.ContributeClass;
import e.nitishkumar.minor_project.DataModel.POJOResult;
import e.nitishkumar.minor_project.MainActivity;
import e.nitishkumar.minor_project.R;
import e.nitishkumar.minor_project.Reposterary_list;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.myholder> {
    private  ArrayList<ContributeClass> list;
    private  ArrayList<POJOResult> mylist=new ArrayList<POJOResult>();
    Context c,c2;
    String[] type;
    String s="";
    Dialog d2;
    ProgressDialog dialog;
    public HorizontalAdapter(ArrayList<ContributeClass> model, Context c) {
        this.list=model;
        this.c=c;
        System.out.println("listreceice"+list.toString());
    }
    public HorizontalAdapter(Context c1, String[] list, String S, Dialog dialog) {
        type=list;
        s=S;
        c=c1;
        d2=dialog;
        System.out.println("listreceice"+list.toString());
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {
        View v;
        if(!s.equals("Dialog")) {
             v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_roundlayout, viewGroup, false);
        }else
        {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seachlabel, viewGroup, false);
        }
        return new myholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myholder myholder, final int i) {
        if (!s.equals("Dialog"))
        {
            Glide.with(c).load(list.get(i).getAvtarUrl()).into(myholder.imageView);
        if (list.get(i).getLoginName() != null)
            myholder.profilename.setText(list.get(i).getLoginName());
        myholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.startActivity(new Intent(c, Reposterary_list.class).putExtra("data", list.get(i).getAvtarUrl() + "_" + list.get(i).getLoginName() + "_" + list.get(i).getRepos_Url()));
            }
        });
    }else{
            myholder.label.setText(type[i]);
            myholder.label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String select=type[i];
                    d2.dismiss();
                    String url="https://api.github.com/search/repositories?q="+select+"+language:HighLevelLanguage&sort=stars&order=desc";
                    getDataFromServer(url);

                }
            });
        }

    }
    public void getDataFromServer(String url)
    {
        dialog = new ProgressDialog(c);
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
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("items");
                    mylist.clear();
                    for(int i=0;i<array.length();i++)
                    {
                        Log.d("hiiiii","hiiiiii2");
                        JSONObject object1=array.getJSONObject(i);
                        System.out.println("firstobjct"+object1.toString());
                        POJOResult result=new POJOResult();
                        result.setName(object1.getString("name"));
                        result.setLanguage(object1.getString("language"));
                        result.setJsonObject(object1.getJSONObject("owner"));
                        result.setContributor_Url(object1.getString("contributors_url"));
                        result.setDescription(object1.getString("description"));
                        //result.setContributor_Url(object.getString("contributors_url"));
//                        result.setContributor_Url(object1.getString("contributors_url"));
                        result.setWatchers(Long.parseLong(object1.getString("watchers")));
                        result.setForks_count(Long.parseLong(object1.getString("forks_count")));
                        System.out.println("position"+i+result.toString());
                        mylist.add(result);
                    }
                    MainActivity.rv.setAdapter(new RepositoryAdapter(mylist,c));
                } catch (JSONException e) {
                    e.printStackTrace();
                    https://avatars3.githubusercontent.com/u/698437?v=4
                    Log.d("hiiiii",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
                Toast.makeText(c,"error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(c);
        queue.add(myrequest);
    }
    @Override
    public int getItemCount() {
        if(!s.equals("Dialog"))
        return list.size();
        else{
            return type.length;
        }
    }

    class myholder extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView profilename,label;
        public myholder(@NonNull View itemView) {
            super(itemView);
            if (!s.equals("Dialog")) {
                imageView = (CircleImageView) itemView.findViewById(R.id.profileimage);
                profilename = (TextView) itemView.findViewById(R.id.profilename);
            }else{
                label=(TextView)itemView.findViewById(R.id.label);
            }

        }
    }
}

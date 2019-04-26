package e.nitishkumar.minor_project.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import e.nitishkumar.minor_project.DataModel.POJOResult;
import e.nitishkumar.minor_project.R;
import e.nitishkumar.minor_project.git2activity;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.viewholder> {
    List<POJOResult> list;
    Context context;

    public RepositoryAdapter(List list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.gitshowlayout, null);
        // progressBar=(ProgressBar)view.findViewById(R.id.mypro);
        //progressBar.setVisibility(View.VISIBLE);
        return new viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder,final  int i) {
        try {
                if(list.get(i).getJsonObject().getString("avatar_url")!=null)
            Glide.with(context).load(list.get(i).getJsonObject().getString("avatar_url")).into(viewholder.imageview);
            if(list.get(i).getJsonObject().getString("login")!=null)
            viewholder.login.setText(list.get(i).getJsonObject().getString("login"));
            if(list.get(i).getName()!=null)
            viewholder.name.setText(list.get(i).getName());
            if(list.get(i).getDescription()!=null)
            viewholder.description.setText(list.get(i).getDescription());
            if(list.get(i).getLanguage()!=null)
            viewholder.language.setText(list.get(i).getLanguage());
            if(list.get(i).getForks_count()!=null)
            viewholder.forks_count.setText(String.valueOf(list.get(i).getForks_count()));
            if(list.get(i).getWatchers()!=null)
            viewholder.watcher.setText(String.valueOf(list.get(i).getWatchers()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    context.startActivity(new Intent(context,git2activity.class).putExtra("data",list.get(i).getJsonObject().getString("avatar_url")+"_"+list.get(i).getJsonObject().getString("login")+"_"+list.get(i).getDescription()+"_"+list.get(i).getContributor_Url()+"_"+list.get(i).getJsonObject().getString("html_url")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView login,name,description,language;
        CircleImageView imageview;
        TextView forks_count,watcher;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            description = (TextView) itemView.findViewById(R.id.description);
            name = (TextView) itemView.findViewById(R.id.name);
            language = (TextView) itemView.findViewById(R.id.language);
            imageview=(CircleImageView) itemView.findViewById(R.id.avtar_url);
            forks_count = (TextView) itemView.findViewById(R.id.forks_count);
            watcher = (TextView) itemView.findViewById(R.id.watchers);

        }
    }
}


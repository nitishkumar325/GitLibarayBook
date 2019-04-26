package e.nitishkumar.minor_project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class BlankFragment extends Fragment {
    String url;
    WebView view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments() != null){
             url = getArguments().getString("html_url");
        }
        View v= inflater.inflate(R.layout.fragment_blank, container, false);
        view=(WebView)v.findViewById(R.id.mywebview);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }
}

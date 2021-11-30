package xyz.jnishat.entertainment247;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    // Declarations
    WebView webView;
    private String webUrl = "https://bluemoon147.blogspot.com/2021/08/royal-entertainment-all-in-1.html";
    ProgressBar progressBarWeb;
    RelativeLayout relativeLayout;
    Button btnNoInternetConnection;
    // Declarations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for full screen
        //Window window = getWindow();
        //window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // for full screen
        setContentView(R.layout.activity_main);


        // Initializations
        webView = (WebView) findViewById(R.id.myWebView);
        progressBarWeb = (ProgressBar) findViewById(R.id.progressBar);
        btnNoInternetConnection = (Button) findViewById(R.id.btnNoConnection);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        // Initializations


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Setting up webview
        checkConnection();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // progressbar setup inside webview
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBarWeb.setVisibility(View.VISIBLE);
                progressBarWeb.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBarWeb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        // progressbar setup inside webview

        // Setting up webview

        btnNoInternetConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).show();
        }
    }

    public void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected()) {
            webView.loadUrl(webUrl);
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        else if (mobileNetwork.isConnected()) {
            webView.loadUrl(webUrl);
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        else {
            webView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_home:
                checkConnection();
                break;

            case R.id.nav_back:
                onBackPressed();
                break;

            case R.id.nav_forward:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;

            case R.id.nav_refresh:
                webView.reload();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
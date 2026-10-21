package net.cwdesk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/** Full-screen WebView showing the bundled trainer page (assets/index.html). Works offline. */
public class MainActivity extends Activity {
    private WebView web;

    @Override
    protected void onCreate(Bundle state) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // the page has its own header
        super.onCreate(state);
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true); // settings and lesson history
        s.setMediaPlaybackRequiresUserGesture(false);
        web.setWebViewClient(new WebViewClient()); // keep navigation inside the app
        setContentView(web);
        // Keep the screen on during a copy session.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (state != null) web.restoreState(state);
        else web.loadUrl("file:///android_asset/index.html");
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        web.saveState(out);
    }

    @Override
    protected void onPause() {
        // Stop any playing Morse when the app goes to the background.
        web.evaluateJavascript("document.dispatchEvent(new KeyboardEvent('keydown',{key:'Escape'}))", null);
        web.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        web.onResume();
    }
}

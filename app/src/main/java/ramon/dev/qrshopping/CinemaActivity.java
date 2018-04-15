package ramon.dev.qrshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class CinemaActivity extends AppCompatActivity {
    Bundle extras;
    String json;
    String frameVideo = "";
    String title;
    String sinop;
    String sala = "";
    String form = "";
    String hora = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);

        TextView tvSala = (TextView) findViewById(R.id.tvSala);
        TextView tvForm = (TextView) findViewById(R.id.tvForm);
        TextView tvHora = (TextView) findViewById(R.id.tvHora);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        TextView tv = (TextView) findViewById(R.id.tv);

        extras = getIntent().getExtras();
        getValuesExtras(extras);

        tv.setText("Sinopse: "+sinop);

        tvSala.setText(sala);
        tvForm.setText(form);
        tvHora.setText(hora);

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView mWebview = (WebView) findViewById(R.id.wv);

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if(!frameVideo.trim().isEmpty()){
            mWebview.loadData(frameVideo, "text/html", "utf-8");
        }else{
            alerta("Nao foi possivel reproduzir o trailer.");
        }
    }

    private void getValuesExtras(Bundle extras) {
        json = extras.getString("json");
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject qrObj = obj.getJSONObject("qr");
            title = qrObj.getString("description");
            sinop = qrObj.getString("sinop");
            sala = qrObj.getString("s");
            form = qrObj.getString("f");
            hora = qrObj.getString("h");
            frameVideo = "<html><body><iframe width=\"100%\" height=\"90%\" src=\"http://www.youtube.com/embed/"+qrObj.getString("code")+"?autoplay=1\" allowfullscreen=\"true\"></iframe></body></html>";
            Log.e("My App", "alerta: "+frameVideo);

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
    }

    public void alerta (String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}

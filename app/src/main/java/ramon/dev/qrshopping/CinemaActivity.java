package ramon.dev.qrshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import org.json.JSONObject;

public class CinemaActivity extends AppCompatActivity{
    Bundle extras;
    String json;
    String code = "";
    String title;
    String sinop;
    String sala = "";
    String form = "";
    String hora = "";
    YouTubePlayerFragment playerFragment;
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

        playerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        playerFragment.initialize("key", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b){
                    youTubePlayer.cueVideo(code);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                alerta("Nao foi possivel carregar o trailer.");
            }
        });
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
            code = qrObj.getString("code");
            Log.e("My App", "alerta: "+code);

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
    }

    public void alerta (String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}

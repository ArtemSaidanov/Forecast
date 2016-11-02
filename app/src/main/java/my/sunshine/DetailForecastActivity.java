package my.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class DetailForecastActivity extends AppCompatActivity {
    private static final String FORECAST_SHARE_HASHTAG =" #SunshineApp" ;
    private String mForecastStr;
    public static ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_forecast_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forecast_detail,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);

        shareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(menuItem);
        if(shareActionProvider!=null){
            shareActionProvider.setShareIntent(createShareForecastIntent());
        }else {
            Log.d("Share Menu", "error");
        }
        return true;
    }
    public static Intent createShareForecastIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, DetailForecastActivityFragment.forecastStr+FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }

}

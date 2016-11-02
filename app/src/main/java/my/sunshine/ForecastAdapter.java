package my.sunshine;

/**
 * Created by Godfrid on 22.10.2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {


    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FUTURE_DAY = 1;

    @Override
    public int getItemViewType(int position) {
        return (position==0) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    //Remember that these views are reused as needed.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutID = -1;
        if(viewType == VIEW_TYPE_TODAY){
            layoutID = R.layout.list_item_today_forecast;
        }else layoutID = R.layout.list_item;
        return LayoutInflater.from(context).inflate(layoutID, parent, false);
    }

    //This is where we fill-in the views with the contents of the cursor.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        int weatherId = cursor.getInt(MainActivityFragment.COL_WEATHER_ID);
       // ImageView iconView =(ImageView)view.findViewById(R.id.list_item_icon);
      //  iconView.setImageResource(R.drawable.fullsun);

        long dateInMillis = cursor.getLong(MainActivityFragment.COL_WEATHER_DATE);
        TextView dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
        dateView.setText(Utility.getFriendluDayString(context,dateInMillis));

        String description = cursor.getString(MainActivityFragment.COL_WEATHER_DESC);
        TextView descView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        descView.setText(description);

        boolean isMetric = Utility.isMetric(context);

        double high = cursor.getDouble(MainActivityFragment.COL_WEATHER_MAX_TEMP);
        TextView highView =(TextView)view.findViewById(R.id.list_item_high_textview);
        highView.setText(Utility.formatTemperature(high, isMetric));

        double low = cursor.getDouble(MainActivityFragment.COL_WEATHER_MIN_TEMP);
        TextView lowView = (TextView) view.findViewById(R.id.list_item_low_textview);
        lowView.setText(Utility.formatTemperature(low,isMetric));


    }
}
package my.sunshine;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import my.sunshine.data.WeatherContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailForecastActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String[] FORECAST_COLUMNS = {
            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
    };
    static final int COL_WEATHER_ID = 0;
    static final int COL_WEATHER_DATE = 1;
    static final int COL_WEATHER_DESC = 2;
    static final int COL_WEATHER_MAX_TEMP = 3;
    static final int COL_WEATHER_MIN_TEMP = 4;

    private static final int DETAIL_LOADER = 0;

    public static String forecastStr;

    public DetailForecastActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_forecast_activity, container, false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Intent intent = getActivity().getIntent();
        if(intent == null){
            return null;
        }
        return new CursorLoader(
                getActivity(),
                intent.getData(),
                FORECAST_COLUMNS,
                null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(!data.moveToFirst()){return;}

        String dateString = Utility.formatDate(data.getLong(COL_WEATHER_DATE));

        String weatherDescription = data.getString(COL_WEATHER_DESC);

        boolean isMetric = Utility.isMetric(getActivity());

        String high = Utility.formatTemperature(data.getDouble(COL_WEATHER_MAX_TEMP), isMetric);

        String low = Utility.formatTemperature(data.getDouble(COL_WEATHER_MIN_TEMP),isMetric);

        forecastStr = String.format("%s - %s - %s/%s", dateString, weatherDescription, high, low);

        TextView detailTextView = (TextView)getView().findViewById(R.id.fragment_detail_text_vew);
                   detailTextView.setText(forecastStr);

        if (DetailForecastActivity.shareActionProvider != null) {
            DetailForecastActivity.shareActionProvider.setShareIntent(DetailForecastActivity.createShareForecastIntent());
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }
}































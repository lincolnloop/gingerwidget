package com.gingerhq.android;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class WidgeRemoteService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new WidgetRemoteFactory(this.getApplicationContext(), intent);
	}

}

class WidgetRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

	private static final String TAG = WidgetRemoteFactory.class.getSimpleName();
	
	List<Unread> data;
	Context context;
	
	public WidgetRemoteFactory(Context applicationContext, Intent intent) {
		Log.d(TAG, "WidgetRemoteFactory constructor");
		this.context = applicationContext;
		this.data = new DBManager(applicationContext).load();
	}

	@Override
	public int getCount() {
		Log.d(TAG, "WidgetRemoteFactory.getCount: " + this.data.size());
		return this.data.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public RemoteViews getLoadingView() {
		// We don't have a loading view
		return null;
	}

	@Override
	public RemoteViews getViewAt(int position) {

		Log.d(TAG, "getViewAt: "+ position);
		
		RemoteViews rv = new RemoteViews(this.context.getPackageName(), R.layout.row);
		rv.setTextViewText(R.id.rowContent, this.data.get(position).toString());
		
		return rv;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "WidgetRemoteFactory.onCreate");
		// Maybe we need to do something here?
	}

	@Override
	public void onDataSetChanged() {
		Log.d(TAG, "WidgetRemoteFactory.onDataSetChanged");
		// Refresh the cursor (?)
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "WidgetRemoteFactory.onDestroy");
		// pass
	}
	
}
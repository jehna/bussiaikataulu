package com.jesseluoto.bussiaikataulu;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppWidgetProvider {
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager,int[] appWidgetIds) {
        for(int i=0; i<appWidgetIds.length; i++){
            final int currentWidgetId = appWidgetIds[i];

            final RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.activity_main);

            AsyncTask<Void, Void, AikatauluHakija> task = new AsyncTask<Void, Void, AikatauluHakija>() {
                @Override
                protected AikatauluHakija doInBackground(Void... params) {
                    return new AikatauluHakija(2411240);
                }

                @Override
                protected void onPostExecute(AikatauluHakija aikatauluHakija) {
                    ArrayList<AikatauluAika> ajat = aikatauluHakija.getAikataulut();
                    views.setTextViewText(R.id.busName, ajat.get(0).getCode());
                    views.setTextViewText(R.id.timeToStop, ajat.get(0).getRelativeDepartTime());
                    appWidgetManager.updateAppWidget(currentWidgetId, views);
                }
            };
            task.execute();


            Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
        }
    }
}
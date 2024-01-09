package com.betelgeuse.corp.vidgetjava.widgets;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.betelgeuse.corp.vidgetjava.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Implementation of App Widget functionality.
 */
public class ViewTest extends AppWidgetProvider {
    static int a = 1;



    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.i("TEST", "TEST AAAAAAA");
    }

    //    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
//
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.view_test);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }
@Override
public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
//        ComponentName componentName = new ComponentName(context, ViewTest.class);
//        int[] AllWidgetIDs = appWidgetManager.getAppWidgetIds(componentName);
//        for (int appWidgetId : AllWidgetIDs) {
////            updateAppWidget(context, appWidgetManager, appWidgetId);
//            int Random = new Random().nextInt(100);
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_test);
////            remoteViews.setTextViewText(R.id.appwidget_text, Integer.toString(Random));
//            try {
//                remoteViews.setTextViewText(R.id.appwidget_text, getData());
//            } catch (IOException e) {
////                throw new RuntimeException(e);
//            } catch (ParserConfigurationException e) {
////                throw new RuntimeException(e);
//            } catch (SAXException e) {
////                throw new RuntimeException(e);
//            }
//
//            Intent intent = new Intent(context, ViewTest.class);
//            intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetIds);
//
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                    0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//            remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
//            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
//        }
    super.onUpdate(context, appWidgetManager, appWidgetIds);

    class LongOperation extends AsyncTask <Object, Void, String> {
        int[] Ids;
        Context context;
        AppWidgetManager appWidgetManager;

        @Override
        protected void onPostExecute(String s) {
            for (int widgetId: Ids){
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_test);
                remoteViews.setTextViewText(R.id.appwidget_text, s);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }

        @Override
        protected String doInBackground(Object... objects) {
            Ids = (int[]) objects[0];
            context = (Context) objects[1];
            appWidgetManager = (AppWidgetManager) objects[2];
            String data = "AsyncTaskError";
            try {
                data = getData();
            } catch (IOException e) {
                Log.i("TEST", "IOException");;
            } catch (ParserConfigurationException e) {
                Log.i("TEST", "ParserConfigurationException");
            } catch (SAXException e) {
                Log.i("TEST", "SAXException");
            }
            return data;
        }
    }
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Log.i("TEST", "TEST AAAAAAA");
            new LongOperation().execute(appWidgetIds, context, appWidgetManager);
        }
    };
    Timer timer = new Timer();
    timer.schedule(timerTask,0,5000);



        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ViewTest.class);
        PendingIntent pendingIntent = PendingIntent.getService
                (context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setInexactRepeating(
                AlarmManager.RTC,System.currentTimeMillis(), 5000, pendingIntent);
//        appWidgetManager.updateAppWidget(context, appWidgetManager, appWidgetIds);
}
    String getData() throws IOException, ParserConfigurationException, SAXException {
        StringBuilder stringBuilder = new  StringBuilder(100);
        URL url = new URL("https://www.cbr-xml-daily.ru/daily.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(url.openStream());
        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("Valute");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;
            String code = element.getElementsByTagName("CharCode")
                    .item(0).getFirstChild().getNodeValue();
            Log.i("TEST", code.toString());
            if (code.equals("AZN")
                    || code.equals("JPY")
                    || code.equals("AUD")
                    || code.equals("GBP")
                    || code.equals("HKD")
                    || code.equals("DKK")
                    || code.equals("CNY")){
                String value = element.getElementsByTagName("Value")
                        .item(0).getFirstChild().getNodeValue();
                stringBuilder.append(code + " : " + value + "\n");
            }
//            <Valute ID="R01820">
//            <NumCode>392</NumCode>
//            <CharCode>JPY</CharCode>
//            <Nominal>100</Nominal>
//            <Name>Японских иен</Name>
//            <Value>64,7868</Value>
//            <VunitRate>0,647868</VunitRate>
//            </Valute>
        }
//        stringBuilder.append("\n" + ++a);
        stringBuilder.append("\n" + "Курс к рублю");
        return stringBuilder.toString();
    }

    @Override
    public void onEnabled(Context context) {

        Log.i("TEST", "TEST 1");
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        Log.i("TEST", "TEST 2");
        // Enter relevant functionality for when the last widget is disabled
    }
}
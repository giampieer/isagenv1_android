package principal.android.empresa.eduardo;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import layout.Relacion.BlankFragment;
import layout.Relacion.Personalizacion_relacion;
import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.dao.ProyectoDao;

/**
 * Implementation of App Widget functionality.
 */
public class miwidget_wprovider extends AppWidgetProvider {
    ArrayList<ProyectoBean> listado;
    ListView LSTRELACION;
    SwipeRefreshLayout swipeContainer;
    ProyectoDao objdao=null;
    int estado;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for(int i=0; i<appWidgetIds.length; i++){
            int currentWidgetId = appWidgetIds[i];
            String url = "http://gestion24.jl.serv.net.mx/mariscal24/#no-back-button";

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));

            PendingIntent pending = PendingIntent.getActivity(context, 0,intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.miwidget_wprovider);

            views.setOnClickPendingIntent(R.id.BTNENLACE, pending);
            appWidgetManager.updateAppWidget(currentWidgetId,views);
            Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onEnabled(Context context) {

    }







    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


package com.ozlem.ozlemsample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ozlem.ozlemsample.backend.myApi.model.MyBean;
import com.ozlem.ozlemsample.backend.myApi.MyApi;
import com.ozlem.ozlemsample.backend.myApi.model.MyBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EndpointAsyncTask extends AsyncTask<Void, Void, List<MyBean>> {

    private static MyApi myApiService = null;
    private Context context;
    private String name;
    private ProgressDialog pd_ring;

    EndpointAsyncTask(Context context, String name) {
        this.context = context;
        this.name = name;
    }

    @Override
    protected void onPreExecute() {
        pd_ring  = new ProgressDialog (context, R.style.AppTheme);
        pd_ring.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd_ring.show();
    }

    @Override
    protected List<MyBean> doInBackground(Void... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl("https://ozlemsample.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);// turn off if local server is used
                        }
                    });

            myApiService = builder.build();

        }

        try {

            MyBean mybean=new MyBean();
            mybean.setData(myApiService.sayHi(name).execute().getData());

            List<MyBean> l=new ArrayList<MyBean>();
            l.add(mybean);

            Log.d("1",mybean.getData() );
            return l;

        } catch (Exception e) {
            Log.d("2", "empty");

            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<MyBean> result) {

        pd_ring.dismiss();

        for (MyBean q : result) {
            Log.d("3", q.getData());

            Intent intent=new Intent(context, Welcome.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("name",q.getData());

            context.getApplicationContext().startActivity(intent);
        }
    }
}
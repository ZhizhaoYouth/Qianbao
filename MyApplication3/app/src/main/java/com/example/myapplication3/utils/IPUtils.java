package com.example.myapplication3.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IPUtils {

    public static void getPublicIpAddress(IPCallback callback) {
        new FetchPublicIPTask(callback).execute();
    }

    private static class FetchPublicIPTask extends AsyncTask<Void, Void, String> {

        private IPCallback callback;

        FetchPublicIPTask(IPCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.ipify.org");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    return in.readLine();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("IPUtils", "Error fetching public IP", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (callback != null) {
                callback.onIPReceived(result);
            }
        }
    }

    public interface IPCallback {
        void onIPReceived(String ip);
    }
}


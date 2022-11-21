package es.unex.giiis.asee.proyecto;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

public class NetworkImageLoaderRunnable implements Runnable{

    private String url;
    private final OnImageLoaderListener mOnImageLoaderListener;

    public NetworkImageLoaderRunnable(String url, OnImageLoaderListener mOnImageLoaderListener) {
        this.url = url;
        this.mOnImageLoaderListener = mOnImageLoaderListener;
    }

    @Override
    public void run() {
        try {
            // Se obtiene la imagen de internet en segundo plano
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");

            // Llamada al Listener con los datos obtenidos
            AppExecutors.getInstance().mainThread().execute(() -> mOnImageLoaderListener.onImageLoader(d));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
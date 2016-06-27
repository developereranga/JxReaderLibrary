package net.droidblaster.libjxr.extra;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ErangaS on 2016-06-27.
 */
public class ImageLoader {

    private final Map<String, SoftReference<Drawable>> mCache = new HashMap<String, SoftReference<Drawable>>();
    private final LinkedList<Drawable> mChacheController = new LinkedList<Drawable>();
    private final Map<ImageView, String> mImageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    Context context;
    private ExecutorService mThreadPool;
    private int MAX_CACHE_SIZE = 80;
    private int THREAD_POOL_SIZE = 3;


    public ImageLoader(Context context) {
        mThreadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.context = context;
    }

    public void customizer(int MAX_CACHE_SIZE, int THREAD_POOL_SIZE) {
        this.MAX_CACHE_SIZE = MAX_CACHE_SIZE;
        this.THREAD_POOL_SIZE = THREAD_POOL_SIZE;
    }

    public void ClearCache() {
        ExecutorService oldThreadPool = mThreadPool;
        mThreadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        oldThreadPool.shutdownNow();

        mChacheController.clear();
        mCache.clear();
        mImageViews.clear();
    }

    public void LoadImage(final String url, final ImageView imageView, int placeholder) {
        Drawable defImage = context.getResources().getDrawable(placeholder);
        mImageViews.put(imageView, url);
        Drawable drawable = getDrawableFromCache(url);
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setImageDrawable(defImage);
            addToQueue(url, imageView, defImage);
        }
    }


    private Drawable getDrawableFromCache(String url) {
        if (mCache.containsKey(url)) {
            return mCache.get(url).get();
        }

        return null;
    }

    private synchronized void cacheIt(String url, Drawable drawable) {
        int chacheControllerSize = mChacheController.size();
        if (chacheControllerSize > MAX_CACHE_SIZE)
            mChacheController.subList(0, MAX_CACHE_SIZE / 2).clear();
        mChacheController.addLast(drawable);
        mCache.put(url, new SoftReference<Drawable>(drawable));

    }

    private void addToQueue(final String url, final ImageView imageView, final Drawable placeholder) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String tag = mImageViews.get(imageView);
                if (tag != null && tag.equals(url)) {
                    if (imageView.isShown())
                        if (msg.obj != null) {
                            imageView.setImageDrawable((Drawable) msg.obj);
                        } else {
                            imageView.setImageDrawable(placeholder);

                        }
                }
            }
        };

        mThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                final Drawable bmp = downloadDrawable(url);
                if (imageView.isShown()) {
                    Message message = Message.obtain();
                    message.obj = bmp;
                    handler.sendMessage(message);
                }
            }
        });
    }


    private Drawable downloadDrawable(String url) {
        try {
            InputStream is = getInputStream(url);

            Drawable drawable = Drawable.createFromStream(is, url);
            cacheIt(url, drawable);
            return drawable;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private InputStream getInputStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection;
        connection = url.openConnection();
        connection.setUseCaches(true);
        connection.connect();
        InputStream response = connection.getInputStream();

        return response;
    }
}
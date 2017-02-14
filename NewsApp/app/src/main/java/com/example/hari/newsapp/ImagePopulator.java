package com.example.hari.newsapp;

/**
 * Created by Hari on 2/6/2017.
 */


        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;

        import java.io.IOException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

/**
 * Created by Allen on 2/6/17.
 */

public class ImagePopulator extends AsyncTask<String, Void, Bitmap> {
    Bitmap image;
    Image intImage;

    public ImagePopulator(Image image) {
        this.intImage=image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        intImage.getMainImage(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url=new URL(strings[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            image=BitmapFactory.decodeStream(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    static public interface Image{

        public void getMainImage(Bitmap image);
    }
}


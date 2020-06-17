package com.example.kurs;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class films extends AppCompatActivity {
    public ListView listView;
    public String link;
    public String cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);
        TextView cityy = findViewById(R.id.films);
        cityy.setText("Restaurants in the city "+cityName);
        String href = getIntent().getStringExtra("href");
        Log.d("href", "onCreate: href:  "+href);
        String[] words = href.split("href=");
        String hreff = "";
        for(String word : words){
            hreff = word;
            System.out.println("1: "+word);
        }

        String[] linkmas = hreff.split(">");
        link= linkmas[0];
        int lenlink = link.length()-1;
        link = link.substring(1,lenlink);
        System.out.println("link: "+link);
        listView = findViewById(R.id.list_films);
       MyTask mt = new MyTask();
        mt.execute();
    }
    class MyTask extends AsyncTask<Void, Void, Void> {

        String title;//Тут храним значение заголовка сайта
        List<String> film_img = new ArrayList<>();
        List<String> film_name = new ArrayList<>();
        List<String> film_cat = new ArrayList<>();
        List<String> film_description = new ArrayList<>();
        List<String> href = new ArrayList<>();
        ArrayList<String> ArrRating = new ArrayList<String>();
        Elements list_img;
        Elements list_info;

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("log", "doInBackground: "+link.split("'")[0]);
            Document doc = null;
            try {
                String url = "http://www.afisha.ru"+link.split("'")[0];
                Log.d("log", "doInBackground: "+url);
                doc =  Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (doc!=null) {
                Log.d("logh", "doInBackground: OKI");
                list_img = doc.getElementsByClass("tile__media");
                list_info = doc.getElementsByClass("page_main__content");
            } else Log.d("err", "ERROR: ");


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            String img = "";
            super.onPostExecute(result);
                    /*for (Element element : list_img) {

                img = element.toString().split("src=")[0];
                int img_len = img.length();
                img = img.substring(0,img_len-1);
                System.out.println("img: "+ img);
                        System.out.println("img: " + element.toString());
                        film_img.add(img);
                        // href.add(element.toString());
                    }*/
                    String name = "";
                    String cat = "";
                    for (Element element : list_info.select(".card__badge")) {

                        film_cat.add(element.text());
                        System.out.println("cat: " + element.text());
                    }
                    for (Element element : list_info.select(".card__title")) {
                        if(list_info.select("h2").get(1).text().equals("Кинотеатры")){} else{
                        film_name.add(element.text());
                        System.out.println("name: " + element.text());}
                    }
                    for (Element element : list_info.select(".card__verdict")) {
                        film_description.add(element.text());
                        System.out.println("Desc: " + element.text());
                    }
                    String link1 = "";
                    // String last_link =  url.split("/cinema")[0];

                    for (Element element : list_info.select(".rating-static")) {
                        ArrRating.add(element.text().split(" ")[0]);

                        System.out.println("rating: " + element.text().split(" ")[0]);
                    }

            ArrayList<Film> products = new ArrayList<Film>();

           for(int i=0;i<film_name.size();i++) {
              // if (ArrRating.get(i).equals("добавить")) ArrRating.set(i,"");
                products.add(new Film(film_name.get(i),"",film_cat.get(i),film_description.get(i),ArrRating.get(i)));
            }
            AdapterFilms adapter =  new AdapterFilms(getBaseContext(), products);
            listView.setAdapter(adapter);
        }

    }
}

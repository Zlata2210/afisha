package com.example.kurs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Show extends AppCompatActivity {
    public ListView listView;
    public String link;
    private RecyclerView mTaskRecyclerView;
    private Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
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
        mTaskRecyclerView = (RecyclerView) findViewById(R.id.list);
        MyTask mt = new MyTask();
        mt.execute();
    }
    class MyTask extends AsyncTask<Void, Void, Void> {

        String title;//Тут храним значение заголовка сайта
        List<String> film_img = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> place = new ArrayList<>();
        List<String> janr = new ArrayList<>();
        List<String> href = new ArrayList<>();
        ArrayList<String> ArrRating = new ArrayList<String>();
        Elements list_img;
        Elements list_info;

        @Override
        protected Void doInBackground(Void... params) {
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
                list_info = doc.getElementsByClass("carousel__item");
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
            String cat = "";
            int i=-1;
            for (Element element : list_info.select("h3")) {
            i+=1;
                name.add(element.text());
                System.out.println("name: " + element.text()+" i: "+i);
            }
            //genre___2nKOC
            int ii=-1;
            for (Element element : list_info.select(".genre___2nKOC")) {
ii+=1;
             //   String el = element.toString().split("</h3>")[0].split("</div>")[0].substring(8);
                janr.add(element.text());
                System.out.println("janr: " + element.text()+" i: "+ii);
            }
            int iii=-1;
            for (Element element : list_info.select(".cardPlaceAndDate___2WKPS")) {
                    iii+=1;
                place.add(element.text());
                System.out.println("place: " + element.text()+" i: "+iii);
            }


            while(name.size() != place.size()) place.add("");
            ArrayList<Concerts> products = new ArrayList<Concerts>();
            for(int o=0;o<name.size();o++) {

                products.add(new Concerts(name.get(o),"",janr.get(o),place.get(o)));
            }
            mTaskRecyclerView
                    .setLayoutManager(new LinearLayoutManager(getBaseContext()));
            Adapter adapter =  new Adapter(getBaseContext(), products);
            mTaskRecyclerView.setAdapter(adapter);
            /*
            String[] el = list_info.toString().split("</h3><div>");
            // [0].split("</div>")[0].substring(8);
            System.out.println(el[el.length]);*/

        }

    }
}

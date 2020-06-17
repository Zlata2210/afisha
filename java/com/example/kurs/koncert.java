package com.example.kurs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class koncert extends AppCompatActivity {
    public String link;
    private RecyclerView mTaskRecyclerView;
    private Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koncert);
        String href = getIntent().getStringExtra("href");
        Log.d("href", "onCreate: href:  "+href);
        String[] words = href.split("href=");
        String hreff = "";
        for(String word : words){
            hreff = word;
            System.out.println("1: "+word);
        }

        String[] linkmas = hreff.split("concerts/");
        link= linkmas[0];
        int lenlink = link.length()-1;
        link = link.substring(1,lenlink);
        link = link+"/schedule_concert";
        System.out.println("link: "+link);

        mTaskRecyclerView = (RecyclerView) findViewById(R.id.list);
        MyTask mt = new MyTask();
        mt.execute();
    }
    class MyTask extends AsyncTask<Void, Void, Void> {

        String title;//Тут храним значение заголовка сайта
        List<String> name = new ArrayList<>();
        List<String> place = new ArrayList<>();
        List<String> img = new ArrayList<>();
        List<String> janr = new ArrayList<>();
        Elements list;
        Elements imgg;
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
                list = doc.getElementsByClass("boardItem___3bfHo wide___mGuM8 ");
                imgg = //doc.getElementsByClass("imageWrapper___2cAS9");
                doc.getElementsByAttribute("src");
            } else Log.d("err", "ERROR: ");


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            int i=-1;
            for (Element element : list.select(".genre___2nKOC")) {
                janr.add(element.text());
                i+=1;
                System.out.println("janr: "+element.text()+" i:"+i);
            }

            int j=-1;
            for (Element element : list.select(".cardTitleLink___FF9CS")) {
                name.add(element.text());
                j+=1;
                System.out.println("name: "+element.text()+" i: "+j);
            }
            int jj=-1;
            for (Element element : list.select(".cardPlaceAndDate___2WKPS")) {
                place.add(element.text());
                jj+=1;
                System.out.println("place: "+element.text()+" i: "+jj);
            }
            ArrayList<Concerts> products = new ArrayList<Concerts>();
            for (Element element : imgg) {
                img.add(element.toString());
                i+=1;
               // System.out.println("janr: "+element.toString()+" i:"+i);
            }
            if(name.size() > janr.size()) janr.add("");
           for(int o=0;o<name.size();o++) {

                products.add(new Concerts(name.get(o),"",janr.get(o),place.get(o)));
            }
            mTaskRecyclerView
                    .setLayoutManager(new LinearLayoutManager(getBaseContext()));
            Adapter adapter =  new Adapter(getBaseContext(), products);
            mTaskRecyclerView.setAdapter(adapter);
        }
    }
}

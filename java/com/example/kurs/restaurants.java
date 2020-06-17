package com.example.kurs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
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

public class restaurants extends AppCompatActivity {
    public ListView listView;
    public String link;
    public String cityName;
    private RecyclerView mTaskRecyclerView;
    private Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        cityName = getIntent().getStringExtra("city");
        //TextView cityy = findViewById(R.id.rest);
       // cityy.setText("Restaurants in the city "+cityName);
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

        mTaskRecyclerView = (RecyclerView) findViewById(R.id.list_rest);
        MyTask mt = new MyTask();
       mt.execute();
    }
    class MyTask extends AsyncTask<Void, Void, Void> {

        String title;//Тут храним значение заголовка сайта
        List<String> rest_img = new ArrayList<>();
        List<String> rest_name = new ArrayList<>();
        List<String> rest_tag = new ArrayList<>();
        List<String> rest_adress = new ArrayList<>();
        List<String> href = new ArrayList<>();
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
                list_img = doc.getElementsByClass("places_top");
                list_info = doc.getElementsByClass("places_info");
            } else Log.d("err", "ERROR: ");


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            String img = "";
            super.onPostExecute(result);
            for (Element element : list_img.select("img")) {

                img = element.toString().substring(10).split(" ")[0];
                int img_len = img.length();
                img = img.substring(0,img_len-1);
                System.out.println("img: "+ Uri.parse(img));
                rest_img.add(img);
               // href.add(element.toString());
            }
            String name = "";
            String tag = "";
            for (Element element:list_info.select("h2")){
                name = element.text();
                if (element.text().split(" ").length ==1) tag = "";
                else tag = element.text().split(" ")[2];
                rest_name.add(name);
                rest_tag.add(tag);
                System.out.println("name: "+name +" tag: "+tag);
            }
            String adress = "";
            for (Element element:list_info.select(".places_address")){
                adress = element.text();
                rest_adress.add(adress);
                System.out.println("adress: "+adress);
            }
            ArrayList<Concerts> products = new ArrayList<Concerts>();
            if (rest_name.size()>rest_img.size()){

                for(int i=0;i<rest_name.size();i++) {
                    rest_img.add("");
                      }
            }
            for(int i=0;i<rest_name.size();i++) {
                products.add(new Concerts(rest_name.get(i),rest_img.get(i),"",rest_adress.get(i)));
            }


            mTaskRecyclerView
                    .setLayoutManager(new LinearLayoutManager(getBaseContext()));
            Adapter adapter =  new Adapter(getBaseContext(), products);
            mTaskRecyclerView.setAdapter(adapter);
        }
    }
}

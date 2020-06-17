package com.example.kurs;

import androidx.appcompat.app.AppCompatActivity;

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
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class city extends AppCompatActivity {
public ListView listView;
public String link;
public String cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        cityName = getIntent().getStringExtra("city");
        TextView cityy = findViewById(R.id.nameCity);
        cityy.setText("City: "+cityName);
        String href = getIntent().getStringExtra("href");
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
        listView = findViewById(R.id.List);
       MyTask mt = new MyTask();
        mt.execute();

    }


    class MyTask extends AsyncTask<Void, Void, Void> {

        String title;//Тут храним значение заголовка сайта
        List<String> menu = new ArrayList<>();
        List<String> href = new ArrayList<>();
        Elements list;
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
               list = doc.getElementsByClass("menu-item-link");
                Log.d("logh", "doInBackground: OKI"+list.toString());

            } else Log.d("err", "ERROR: ");


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            for (Element element : list.select("a")) {
                if ((element.text().equals("РЕСТОРАНЫ")&&(cityName.equals("Москва")||cityName.equals("Санкт-Петербург")))||(element.text().equals("ФИЛЬМОТЕКА")) || (element.text().equals("ДЕТИ")) || (element.text().equals("ПОДБОРКИ"))|| (element.text().equals("КИНО")) || (element.text().equals("LIVE"))|| (element.text().equals("Daily")) || (element.text().equals("поиск"))) {

                } else {
                    menu.add(element.text());
                    href.add(element.toString());
                }
            }
            for (String element : menu) {
                System.out.println(element);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                    android.R.layout.simple_list_item_1, menu);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Log.d("log", "itemClick: position = " + position + ", id = "
                            + id+"menu:"+((TextView) view).getText() + "menuPos: "+menu.get(position));
                   if ( menu.get(position).equals("РЕСТОРАНЫ")) {

                        Intent i = new Intent(getBaseContext(), com.example.kurs.restaurants.class);
                        i.putExtra("href", href.get(position));
                        i.putExtra("city", cityName);
                        startActivity(i);
                   } else if (menu.get(position).equals("КИНО")){
                       Intent i = new Intent(getBaseContext(), com.example.kurs.films.class);
                       i.putExtra("href", href.get(position));
                       i.putExtra("city", cityName);
                       startActivity(i);
                   } else if (menu.get(position).equals("КОНЦЕРТЫ")){
                       Intent i = new Intent(getBaseContext(), com.example.kurs.koncert.class);
                       i.putExtra("href", href.get(position));
                       i.putExtra("city", cityName);
                       startActivity(i);
                   }
                   else if (menu.get(position).equals("ТЕАТР")){
                       Intent i = new Intent(getBaseContext(), com.example.kurs.Theatre.class);
                       i.putExtra("href", href.get(position));
                       i.putExtra("city", cityName);
                       startActivity(i);
                   }
                   else if (menu.get(position).equals("ВЫСТАВКИ")){
                       Intent i = new Intent(getBaseContext(), com.example.kurs.Show.class);
                       i.putExtra("href", href.get(position));
                       i.putExtra("city", cityName);
                       startActivity(i);
                   }
                }
            });
        }
    }
}

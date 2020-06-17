package com.example.kurs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    public ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       listView = findViewById(R.id.listView);
        MyTask mt = new MyTask();
        mt.execute();


    }


    class MyTask extends AsyncTask<Void, Void, Void> {

        String title;//Тут храним значение заголовка сайта
        List<String> city = new ArrayList<>();
        List<String> href = new ArrayList<>();
        Elements list;
        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;//Здесь хранится будет разобранный html документ
            try {
                //Считываем заглавную страницу http://harrix.org

                doc =  Jsoup.connect("http://www.afisha.ru").get();
            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }

            //Если всё считалось, что вытаскиваем из считанного html документа заголовок
            if (doc!=null) {
                title = doc.getElementsByClass("filter-button filter-button_type_select header__city-switcher-title-text").toString();
                list = doc.getElementsByClass("city-switcher__item-link");

            }else
                title = "Ошибка";


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("log", "onPostExecute: ");
            super.onPostExecute(result);
            for (Element element : list){
                city.add(element.text());
                Log.d("city", "onPostExecute: "+element.text());
                href.add(element.toString());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                    android.R.layout.simple_list_item_1, city);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Log.d("log", "itemClick: position = " + position + ", id = "
                            + id+"city: "+((TextView) view).getText());
                    Intent i = new Intent(getBaseContext(), com.example.kurs.city.class);
                    i.putExtra("city",((TextView) view).getText());
                    i.putExtra("href",href.get(position));
                    startActivity(i);
                }
            });
        }
    }
}

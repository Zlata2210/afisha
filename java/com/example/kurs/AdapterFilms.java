package com.example.kurs;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterFilms  extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Film> objects;

    AdapterFilms(Context context, ArrayList<Film> restrans) {
        ctx = context;
        objects = restrans;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_films, parent, false);
        }

        Film p = getProduct(position);
        Log.d("11", "getView: ");
        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.nameRest)).setText(p.name);
        ((TextView) view.findViewById(R.id.tagRest)).setText("category: "+p.cat);
        ((TextView) view.findViewById(R.id.adressRest)).setText(p.description);
        // ((ImageView) view.findViewById(R.id.imgRest)).setImageURI(Uri.parse(p.image));
        new DownloadImageTask((ImageView) view.findViewById(R.id.imgRest))
                .execute(p.image);

      //  ((TextView) view.findViewById(R.id.rating)).setText("rating: "+p.rating);


        return view;
    }
    Film getProduct(int position) {
        return ((Film) getItem(position));
    }



}

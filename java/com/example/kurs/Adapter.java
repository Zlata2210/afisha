package com.example.kurs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class  Adapter extends RecyclerView
        .Adapter<TaskHolder> {
    public Context mContext;
    List<Concerts> objects;

    public Adapter(Context context, List<Concerts> restrans) {
        mContext = context;
        objects = restrans;
    }

    @Override
    public int getItemCount()  {
        return objects.size();
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        String LOG = "onCreate";
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_films, parent, false);

        return new TaskHolder(mContext, view);
    }
    @Override
    public void onBindViewHolder(final TaskHolder holder, final int position) {
       Concerts task = objects.get(position);
        String LOG = "Bind";
        Log.d(LOG, "onBindViewHolder: "+position);

        holder.bindTask(task);
    }
}

  class TaskHolder extends RecyclerView
        .ViewHolder implements View.OnClickListener{

    private TextView mname;
    private TextView mDateView;
    private ImageView mImage;
    private TextView mTag;
    private Context mContext;
   private Concerts restran;
    public TaskHolder(Context context, View view) {
        super(view);
        view.setOnClickListener(this);
        mContext = context;
        mname = (TextView) view.findViewById(R.id.nameRest);
        mTag =  view.findViewById(R.id.tagRest);
       mDateView = (TextView) view.findViewById(R.id.adressRest);
       mImage = (ImageView) view.findViewById(R.id.imgRest);

    }
    public void bindTask(Concerts p) {
        restran = p;
       mname.setText(p.Restname);
       mDateView.setText(p.adress);
       mTag.setText(p.tagName);
      new DownloadImageTask(mImage).execute(p.image);

    }


    @Override
    public void onClick(View v) {
    }


}


/*
public class Adapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Restran> objects;

    Adapter(Context context, ArrayList<Restran> restrans) {
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
            view = lInflater.inflate(R.layout.layout_items, parent, false);
        }

        Restran p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.nameRest)).setText(p.Restname);
       // ((TextView) view.findViewById(R.id.tagRest)).setText(p.tagName);
        ((TextView) view.findViewById(R.id.adressRest)).setText(p.adress);
       // ((ImageView) view.findViewById(R.id.imgRest)).setImageURI(Uri.parse(p.image));
        new DownloadImageTask((ImageView) view.findViewById(R.id.imgRest))
                .execute(p.image);






        return view;
    }
    Restran getProduct(int position) {
        return ((Restran) getItem(position));
    }


}*/

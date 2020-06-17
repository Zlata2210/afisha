package com.example.kurs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class  AdapterCon extends RecyclerView
        .Adapter<TaskHolderCon> {
    public Context mContext;
    List<Concerts> objects;

    public AdapterCon(Context context, List<Concerts> restrans) {
        mContext = context;
        objects = restrans;
    }

    @Override
    public int getItemCount()  {
        return objects.size();
    }

    @Override
    public TaskHolderCon onCreateViewHolder(ViewGroup parent, int viewType) {
        String LOG = "onCreate";
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_films, parent, false);

        return new TaskHolderCon(mContext, view);
    }
    @Override
    public void onBindViewHolder(final TaskHolderCon holder, final int position) {
        Concerts task = objects.get(position);
        String LOG = "Bind";
        Log.d(LOG, "onBindViewHolder: "+position);

        holder.bindTask(task);
    }
}

class TaskHolderCon extends RecyclerView
        .ViewHolder implements OnClickListener{

    private TextView mname;
    private TextView mDateView;
    private TextView mTag;
    private ImageView mImage;
    private Context mContext;
    private Concerts concerts;
    public TaskHolderCon(Context context, View view) {
        super(view);
        view.setOnClickListener(this);
        mContext = context;
        mname = (TextView) view.findViewById(R.id.nameRest);
        mTag = ((TextView) view.findViewById(R.id.tagRest));
        mDateView = (TextView) view.findViewById(R.id.adressRest);
        mImage = (ImageView) view.findViewById(R.id.imgRest);

    }
    public void bindTask(Concerts p) {
        concerts = p;
        mname.setText(p.Restname);
        mDateView.setText(p.adress);
        mTag.setText((p.tagName));
        new DownloadImageTask(mImage).execute(p.image);

    }


    @Override
    public void onClick(View v) {
    }


}



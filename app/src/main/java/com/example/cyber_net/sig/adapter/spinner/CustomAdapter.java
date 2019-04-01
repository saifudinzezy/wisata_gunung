package com.example.cyber_net.sig.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.model.response.item.WisataItem;

import java.util.List;

import static com.example.cyber_net.sig.helper.Contans.BASE_URL_IMAGE_WISATA;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;

    List<WisataItem> data;

    public CustomAdapter(Context context, List<WisataItem> data) {
        this.context = context;
        this.data = data;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        Glide.with(context)
                .load(BASE_URL_IMAGE_WISATA + data.get(i).getImage())
                .into(icon);
        names.setText(data.get(i).getJudul());
        return view;
    }
}

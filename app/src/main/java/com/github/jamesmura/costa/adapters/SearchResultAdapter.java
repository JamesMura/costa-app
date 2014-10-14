package com.github.jamesmura.costa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jamesmura.costa.R;
import com.github.jamesmura.costa.models.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResultAdapter extends ArrayAdapter<Result> {
    private final int resource;

    public SearchResultAdapter(Context context, int resource, List<Result> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);
        TextView textViewName = (TextView) rowView.findViewById(R.id.name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewPic);
        Result result = getItem(position);
        textViewName.setText(result.getArtistName());
        Picasso.with(getContext()).load(result.getArtworkUrl100()).into(imageView);
        return rowView;
    }
}

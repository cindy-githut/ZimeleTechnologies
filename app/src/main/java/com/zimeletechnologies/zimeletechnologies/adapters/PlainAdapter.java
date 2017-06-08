package com.zimeletechnologies.zimeletechnologies.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zimeletechnologies.zimeletechnologies.R;
import com.zimeletechnologies.zimeletechnologies.models.PlainItem;

import java.util.ArrayList;

/**
 * Created by cindymbonani on 2017/06/07.
 */


public class PlainAdapter extends BaseAdapter{

    private ArrayList<PlainItem> listFlights;
    LayoutInflater inflater;


    public PlainAdapter(ArrayList<PlainItem> listFlights, Activity activity) {
        this.listFlights = listFlights;
        this.inflater = LayoutInflater.from(activity);

    }

    @Override
    public int getCount() {
        return listFlights.size();
    }

    @Override
    public PlainItem getItem(int position) {
        return listFlights.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        
        final ViewHolder holder;

        if (view == null) {

            holder = new ViewHolder();

            view = this.inflater.inflate(R.layout.flight_layout,
                    viewGroup, false);

            holder.flightNo = (TextView) view
                    .findViewById(R.id.flightNo);
            holder.flightOrigin = (TextView) view
                    .findViewById(R.id.flightOrigin);
            holder.flightArrival = (TextView) view
                    .findViewById(R.id.flightArrival);
            holder.flightDate = (TextView) view
                    .findViewById(R.id.flightDate);
            holder.flightDeparture = (TextView) view
                    .findViewById(R.id.flightDeparture);
            holder.flightDestination = (TextView) view
                    .findViewById(R.id.flightDestination);

            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();
        }


        // Current flight
        holder.flightNo.setText(listFlights.get(position).getFlightNo());
        holder.flightOrigin.setText(listFlights.get(position).getOrigin());
        holder.flightArrival.setText(listFlights.get(position).getArrival());
        holder.flightDate.setText(listFlights.get(position).getDate());
        holder.flightDeparture.setText(listFlights.get(position).getDeparture());
        holder.flightDestination.setText(listFlights.get(position).getDestination());

        return view;
    }

    private class ViewHolder {

        private TextView flightNo;
        private TextView flightOrigin;
        private TextView flightDestination;
        private TextView flightDeparture;
        private TextView flightArrival;
        private TextView flightDate;
        
    }
    
}

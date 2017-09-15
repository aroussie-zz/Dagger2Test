package com.example.alexandreroussiere.bmwlocation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alexandreroussiere.bmwlocation.R;
import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;

import java.util.List;

/**
 * Created by alexandreroussiere on 6/28/17.
 * Adapter for the RecyclerView inside LocationList View
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    //This interface will be implemented directly by the view
    public interface onItemClickedListener{
        void onItemClicked(LocationInfo info);
    }

    private List<LocationInfo> mDataList;
    private onItemClickedListener mListener;
    private Context mContext;


    public RecyclerViewAdapter(List<LocationInfo> myDataList, onItemClickedListener listener, Context context) {
        mDataList = myDataList;
        mListener = listener;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView mLocationTextView;
         TextView mAddressTextView;
         RelativeLayout mLinearLayout;
         ViewHolder(View v ) {
            super(v);
            mLocationTextView = (TextView) v.findViewById(R.id.location_recyclerView);
            mAddressTextView = (TextView) v.findViewById(R.id.address_recyclerView);
            mLinearLayout = (RelativeLayout) v.findViewById(R.id.location_relativeLayout);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final LocationInfo locationInfo = mDataList.get(position);
        holder.mAddressTextView.setText(String.format(mContext.getString(R.string.address_recyclerView),
                locationInfo.getAddress()));
        holder.mLocationTextView.setText(mDataList.get(position).getName());

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(locationInfo);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}

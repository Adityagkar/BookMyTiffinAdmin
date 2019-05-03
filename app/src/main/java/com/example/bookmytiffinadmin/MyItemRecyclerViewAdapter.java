package com.example.bookmytiffinadmin;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookmytiffinadmin.ItemFragment.OnListFragmentInteractionListener;
import com.example.bookmytiffinadmin.dummy.UserData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<UserData> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(ArrayList<UserData> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.bookingDate.setText(mValues.get(position).getDateOfBooking());
        holder.name.setText(mValues.get(position).getNameOfCustomer());
        holder.phone.setText(mValues.get(position).getPhoneNumber());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {

                    String clickedOn = mValues.get(position).getPhoneNumber();
                    String var[]=clickedOn.split("\\|");
                    String orderId[] = var[2].trim().split("\\=");
                    String cusId[] = var[0].trim().split("\\=");
                    String totalTiffins[] = var[3].trim().split("\\=");
                    Log.d("TESTING","corder ID"+orderId[1]+" customer id"+cusId[1]+" ");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("OrderTable");
                    if(Long.valueOf(totalTiffins[1])>0){
                   myRef.child(cusId[1]).child(orderId[1]).child("TiffinsLeft").setValue(Long.valueOf(totalTiffins[1])-1);}

                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView bookingDate;
        public final TextView name;
        public final TextView phone;
        public UserData mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            bookingDate = (TextView) view.findViewById(R.id.bookingdate);
            name = (TextView) view.findViewById(R.id.nameofcustomer);
            phone = (TextView) view.findViewById(R.id.phonenumber);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }





    }


}

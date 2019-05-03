package com.example.bookmytiffinadmin;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookmytiffinadmin.dummy.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    ArrayList<UserData> userData;
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("OrderTable");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = "temp";

                // HashMap<Object,Object> map = (HashMap<Object, Object>) dataSnapshot.child("8076788240").child("-LdIQCF_8GtwxXUQGBB8").getValue();
                // String rowValue = (String) map.get("Name");

                userData = new ArrayList<UserData>();
                UserData userData1 ;

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    String id = snapshot.getKey();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        String Order = snapshot1.getKey();
                        HashMap<Object,Object> map = (HashMap<Object, Object>) dataSnapshot.child(id).child(Order).getValue();
                        String nameTaken= (String) map.get("Name");
                        String dob = (String) map.get("StartDate");
                        String doe = (String) map.get("EndDate");
                        String phone = (String) map.get("Mobile");
                        String totalTiffins = String.valueOf(map.get("TiffinsLeft"));
                        userData1 = new UserData();
                        userData1.setDateOfBooking("From: "+dob + "\nTo: "+doe);
                        userData1.setNameOfCustomer(nameTaken);
                        userData1.setPhoneNumber("id="+id+"| phone="+phone+" | orderId="+Order+" | tiffins left="+totalTiffins );
                        userData.add(userData1);
                        if(Long.valueOf(totalTiffins)==0){
                            userData.remove(userData1);
                        }
                        Log.d("TESTINGDB", "Value of totalCost is: " + nameTaken);
                    }


                }
                // Set the adapter
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }
                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(userData, mListener));
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TESTINGDB", "Failed to read value.", error.toException());
            }
        });



        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_item_list, container, false);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(UserData item);
    }
}

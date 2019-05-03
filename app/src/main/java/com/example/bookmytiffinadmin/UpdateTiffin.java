package com.example.bookmytiffinadmin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateTiffin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateTiffin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateTiffin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatabaseReference myRef;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Long changeValue;

    public UpdateTiffin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateTiffin.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateTiffin newInstance(String param1, String param2) {
        UpdateTiffin fragment = new UpdateTiffin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_tiffin, container, false);
        final Spinner spinner = view.findViewById(R.id.spinner2);
        String list[]={"Ghar ka Khana","Khana Khazana", "Ghar ki Rasoi", "Yellow Chilli","Mummy's food","Papa G Kitchen"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,list);
        spinner.setAdapter(adapter);
        final TextView textView = view.findViewById(R.id.textView4);
        Button show = view.findViewById(R.id.show);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference("TiffinServices");

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String temp = (String) spinner.getSelectedItem();
                        HashMap<Object,Object> map = (HashMap<Object, Object>) dataSnapshot.child("Row-"+temp).getValue();
                        String rowValue = (String) map.get("Kitchen Name");
                        Long breakfastV = (Long) map.get("BPlan");
                        Long lunchV = (Long) map.get("LPlan");
                        Long dinnerV = (Long) map.get("DPlan");
                        String finalValue = rowValue+"\nLunch = Rs."+lunchV+"\nBreakfast = Rs."+breakfastV+"\nDinner = Rs."+dinnerV;
                        textView.setText(finalValue);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("TESTINGDB", "Failed to read value.", error.toException());
                    }
                });
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

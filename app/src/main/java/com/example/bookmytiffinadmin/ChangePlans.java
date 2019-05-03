package com.example.bookmytiffinadmin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChangePlans.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangePlans#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePlans extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String newLunch = null,newBF=null, newDinner=null,tiffinName="nothing";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChangePlans() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePlans.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePlans newInstance(String param1, String param2) {
        ChangePlans fragment = new ChangePlans();
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
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        final View view = inflater.inflate(R.layout.changeplans, container, false);


        final EditText breakfastCost = view.findViewById(R.id.editText);
        final EditText lunchCost = view.findViewById(R.id.editText2);
        final EditText dinnerCost = view.findViewById(R.id.editText3);

        final CheckBox breakfast = view.findViewById(R.id.checkBox);
        final CheckBox lunch  = view.findViewById(R.id.checkBox2);
        final CheckBox dinner = view.findViewById(R.id.checkBox3);
        Button change = view.findViewById(R.id.change);
        final Spinner spinner = view.findViewById(R.id.spinner);
        String list[]={"Ghar ka Khana","Khana Khazana", "Ghar ki Rasoi", "Yellow Chilli","Mummy's food","Papa G Kitchen"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,list);
        spinner.setAdapter(adapter);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean somethingChecked = false;

                if(breakfast.isChecked()){
                    newBF = breakfastCost.getText().toString();
                    somethingChecked = true;
                }
                if(lunch.isChecked()){
                    newLunch = lunchCost.getText().toString();
                    somethingChecked = true;
                }
                if(dinner.isChecked()){
                    newDinner = dinnerCost.getText().toString();
                    somethingChecked = true;

                }
                tiffinName = spinner.getSelectedItem().toString();


                if(tiffinName.equals("nothing")|| somethingChecked.equals(false)){
                    Toast.makeText(getActivity(),"Nothing Selected or Some field is empty",Toast.LENGTH_LONG).show();
                }else{
                    //update in DB

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference("TiffinServices");
                    if (lunch.isChecked())
                        myRef.child("Row-"+tiffinName).child("LPlan").setValue(Integer.valueOf(newLunch));
                    if(breakfast.isChecked())
                        myRef.child("Row-"+tiffinName).child("BPlan").setValue(Integer.valueOf(newBF));
                    if(dinner.isChecked())
                        myRef.child("Row-"+tiffinName).child("DPlan").setValue(Integer.valueOf(newDinner));

                    Toast.makeText(getContext(),"The values have been changed !",Toast.LENGTH_LONG).show();
                }

             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     ViewGroup group = (ViewGroup)view.findViewById(R.id.sample);
                     for (int i = 0, count = group.getChildCount(); i < count; ++i) {
                         View view = group.getChildAt(i);
                         if (view instanceof EditText) {
                             ((EditText)view).setText("");
                         }
                         if (view instanceof CheckBox) {
                             ((CheckBox)view).setChecked(false);
                         }
                     }
                 }
             },5000);



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

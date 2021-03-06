package com.codgin.paulo.mesadebar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.codgin.paulo.mesadebar.R;
import com.codgin.paulo.mesadebar.Service.DialogService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FinalizarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinalizarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalizarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FinalizarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinalizarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinalizarFragment newInstance(String param1, String param2) {
        FinalizarFragment fragment = new FinalizarFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_finalizar, container, false);

        final Bundle bundle = this.getArguments();
        final String idUser = bundle.getString("idUser");
        final String nomeMesa = bundle.getString("nomeMesa");

        TextView txtTituloMesa = (TextView)v.findViewById(R.id.txtNomeMesaFinalizar);
        final TextView txtTotalMesa = (TextView)v.findViewById(R.id.txtTotalMesaFinalizar);
        final TextView txtTotalDividido =  (TextView)v.findViewById(R.id.txtTotalDivididoIgualmente);
        Switch adicionarGorjeta = (Switch)v.findViewById(R.id.switchAddGorjeta);
        final DialogService dialogService = new DialogService();

       adicionarGorjeta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b==true){
                   dialogService.dialogSetGorjeta(getContext(), idUser,nomeMesa,txtTotalMesa);
               }
           }
       });

        return v;
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

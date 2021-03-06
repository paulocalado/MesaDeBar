package com.codgin.paulo.mesadebar.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.codgin.paulo.mesadebar.Model.ModelGetMesa;
import com.codgin.paulo.mesadebar.R;
import com.codgin.paulo.mesadebar.Service.DialogService;
import com.codgin.paulo.mesadebar.Service.FirebaseService;
import com.codgin.paulo.mesadebar.Service.PessoaFirebaseService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaPessoaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaPessoaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPessoaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    FirebaseService mesaFirebaseService = new FirebaseService();
    PessoaFirebaseService pessoaFirebaseService = new PessoaFirebaseService();
    public ListaPessoaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaPessoaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaPessoaFragment newInstance(String param1, String param2) {
        ListaPessoaFragment fragment = new ListaPessoaFragment();
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
        View v = inflater.inflate(R.layout.fragment_lista_pessoa, container, false);

        final Bundle bundle = this.getArguments();
        final String idUser = bundle.getString("idUser");
        final String nomeMesa = bundle.getString("nomeMesa");



        //initializeComponents
        final DialogService dialogService = new DialogService();
        final TextView txtTotalMesaFragment = (TextView)v.findViewById(R.id.txtTotalMesaFragment);
        final Switch switchAddGorjeta = (Switch)v.findViewById(R.id.switchAddGorjeta);
        FloatingActionButton btnAdicionarPessoa = (FloatingActionButton )v.findViewById(R.id.btnAdicionarPessoa);
        RecyclerView rvListaPessoa = (RecyclerView)v.findViewById(R.id.rvListaPessoa);

        ModelGetMesa modelGetMesaVerificaTip = new ModelGetMesa(idUser, nomeMesa, switchAddGorjeta);
        pessoaFirebaseService.getPessoaFirebase(idUser,nomeMesa,rvListaPessoa, getContext());
        mesaFirebaseService.verificaMesaPossuiTip(modelGetMesaVerificaTip);
        if(!switchAddGorjeta.isChecked()){
            mesaFirebaseService.getTotalMesa(false, idUser,nomeMesa, txtTotalMesaFragment);
        }

       switchAddGorjeta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){

                        mesaFirebaseService.getTotalMesa(b,idUser,nomeMesa,txtTotalMesaFragment);
                    }

            }});

        switchAddGorjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchAddGorjeta.isChecked()){
                    dialogService.dialogSetGorjeta(getContext(),idUser,nomeMesa,txtTotalMesaFragment);
                }else{
                    Toast.makeText(getContext(), "FALSO", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnAdicionarPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogService dialogService = new DialogService();
                dialogService.dialogAddPessoa(getContext(),idUser,nomeMesa);
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

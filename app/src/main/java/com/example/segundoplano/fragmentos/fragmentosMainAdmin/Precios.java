package com.example.segundoplano.fragmentos.fragmentosMainAdmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.segundoplano.R;
import com.example.segundoplano.herramientas.EsconderTeclado;
import com.example.segundoplano.herramientas.popUpGenerico;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Precios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Precios extends Fragment {
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    EditText edtx_gorra,edtx_taza,edtx_playerah,edtx_playeram;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Precios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Precios.
     */
    // TODO: Rename and change types and number of parameters
    public static Precios newInstance(String param1, String param2) {
        Precios fragment = new Precios();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void InicializarVariables(){
        edtx_gorra= getView().findViewById(R.id.edtx_gorra);
        edtx_taza= getView().findViewById(R.id.edtx_taza);
        edtx_playerah= getView().findViewById(R.id.edtx_playerah);
        edtx_playeram= getView().findViewById(R.id.edtx_playeram);


        auth= FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Precios");
    }
    public void AgregarListeners(){
        edtx_gorra.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(edtx_gorra.getText())){
                    databaseReference.child("gorra").setValue(Long.valueOf(edtx_gorra.getText().toString()));
                }else {
                    new popUpGenerico(getContext(),"Si desea llenar el campo, no debe estar vacio","Error");
                }
                new EsconderTeclado(getActivity());
                return false;
            }
        });
        edtx_taza.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(edtx_taza.getText())){
                    databaseReference.child("taza").setValue(Long.valueOf(edtx_taza.getText().toString()));
                    new EsconderTeclado(getActivity());
                }
                else{
                    new EsconderTeclado(getActivity());
                    new popUpGenerico(getContext(),"Si desea llenar el campo, no debe estar vacio","Error");
                }
                return false;
            }
        });
        edtx_playerah.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(edtx_playerah.getText())){
                    Long numero = Long.valueOf(edtx_playerah.getText().toString());
                    databaseReference.child("playeraH").setValue(Long.valueOf(edtx_playerah.getText().toString()));
                    new EsconderTeclado(getActivity());
                }else{
                    new EsconderTeclado(getActivity());
                    new popUpGenerico(getContext(),"Si desea llenar el campo, no debe estar vacio","Error");
                }
                return false;
            }
        });
        edtx_playeram.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(edtx_playeram.getText())){
                    databaseReference.child("playeraM").setValue(Long.valueOf(edtx_playeram.getText().toString()));
                }else {
                    new popUpGenerico(getContext(),"Si desea llenar el campo, no debe estar vacio","Error");
                }
                new EsconderTeclado(getActivity());
                return false;
            }
        });
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
        View v = inflater.inflate(R.layout.fragment_precios, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        InicializarVariables();
        AgregarListeners();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mParam2.equals("English")){
            TextView etiqueta1=view.findViewById(R.id.etiqueta1);
            TextView etiqueta2=view.findViewById(R.id.etiqueta2);
            TextView etiqueta3=view.findViewById(R.id.etiqueta3);
            TextView etiqueta4=view.findViewById(R.id.etiqueta4);

            etiqueta1.setText("Cap");
            etiqueta2.setText("Mug");
            etiqueta3.setText("Man's T-shirt");
            etiqueta4.setText("Woman's T-shirt");
        }
    }
}
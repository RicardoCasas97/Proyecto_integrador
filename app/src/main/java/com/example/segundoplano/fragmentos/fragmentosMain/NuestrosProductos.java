package com.example.segundoplano.fragmentos.fragmentosMain;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.segundoplano.EditorImagen;
import com.example.segundoplano.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NuestrosProductos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuestrosProductos extends Fragment {

    String preciogorra, precioplayerah, preciotaza, precioplayeram;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    TextView tv_precioGorra,precioPlayeraH,tv_precioTaza,precioPlayeraM;
    LinearLayout contenedorGorra,contenedorPlayeraH,contenedorPlayeraM,contenedorTaza;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NuestrosProductos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuestrosProductos.
     */
    // TODO: Rename and change types and number of parameters
    public static NuestrosProductos newInstance(String param1, String param2) {
        NuestrosProductos fragment = new NuestrosProductos();
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
        return inflater.inflate(R.layout.fragment_nuestros_productos, container, false);
    }


    public void InicializarVariables(){
        contenedorGorra= getView().findViewById(R.id.contenedorGorra);
        contenedorPlayeraH= getView().findViewById(R.id.contenedorPlayeraH);
        contenedorPlayeraM= getView().findViewById(R.id.contenedorPlayeraM);
        contenedorTaza= getView().findViewById(R.id.contenedorTaza);
        tv_precioGorra= getView().findViewById(R.id.tv_precioGorra);
        precioPlayeraH= getView().findViewById(R.id.precioPlayeraH);
        tv_precioTaza= getView().findViewById(R.id.tv_precioTaza);
        precioPlayeraM= getView().findViewById(R.id.precioPlayeraM);

        auth= FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Precios");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                preciogorra = String.valueOf(snapshot.child("gorra").getValue(Long.class));
                precioplayerah = String.valueOf(snapshot.child("playeraH").getValue(Long.class));
                preciotaza = String.valueOf(snapshot.child("taza").getValue(Long.class));
                precioplayeram = String.valueOf(snapshot.child("playeraM").getValue(Long.class));
                tv_precioGorra.setText(preciogorra);
                precioPlayeraH.setText(precioplayerah);
                tv_precioTaza.setText(preciotaza);
                precioPlayeraM.setText(precioplayeram);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        InicializarVariables();
        agregarListeners();
    }

    public void agregarListeners(){
        final Intent intent = new Intent(getActivity().getApplication(), EditorImagen.class);
        final Bundle b= new Bundle();
        contenedorGorra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            b.putString("Tipo","Gorra");
            intent.putExtras(b);
            startActivity(intent);
            }
        });
        contenedorPlayeraH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString("Tipo","PlayeraHombre");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        contenedorPlayeraM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString("Tipo","PlayeraMujer");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        contenedorTaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.putString("Tipo","Taza");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mParam2.equals("English")){
            TextView textView4= view.findViewById(R.id.textView4);
            TextView textView5= view.findViewById(R.id.textView5);
            TextView textView7= view.findViewById(R.id.textView7);
            TextView textView6= view.findViewById(R.id.textView6);
            textView4.setText("Cap");
            textView5.setText("Man's T-shirt");
            textView6.setText("Woman's T-shirt");
            textView7.setText("Mug");
        }
    }
}
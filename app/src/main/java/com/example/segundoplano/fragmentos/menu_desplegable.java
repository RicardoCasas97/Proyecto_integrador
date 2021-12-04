package com.example.segundoplano.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.segundoplano.Calendario.CalendarMainActivity;
import com.example.segundoplano.R;
import com.example.segundoplano.databinding.FragmentMenuDesplegableBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link menu_desplegable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class menu_desplegable extends Fragment {
    Button botonPerfil,botonCerrar;
    FragmentMenuDesplegableBinding binding;
    ConstraintLayout background;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public menu_desplegable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment menu_desplegable.
     */
    // TODO: Rename and change types and number of parameters
    public static menu_desplegable newInstance(String param1, String param2) {
        menu_desplegable fragment = new menu_desplegable();
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


    public void cerrarFragmento(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentMenuDesplegableBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


    public void User(){

        binding.botonUnirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.areaDeTrabajo,fragmento_VideoLlamada.newInstance("",""),"Pantalla").commit();
            }
        });
        botonPerfil= getView().findViewById(R.id.botonPerfil);
        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarFragmento();
            }
        });
        botonCerrar= getView().findViewById(R.id.botonCerrar);
        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                if (mParam2.equals("Español")){
                    AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                    builder.setTitle("¿Desea cerrar sesion?").setMessage("Se cerrara la sesion actual y regresara a la pantalla de inicio")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().finish();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                    builder.setTitle("Do you want to sign out?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });

        binding.botonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(getContext(), CalendarMainActivity.class));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        background= getActivity().findViewById(R.id.frameLayout2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            background.setBackgroundColor(getActivity().getWindow().getNavigationBarColor());
        }
        User();
    }


}
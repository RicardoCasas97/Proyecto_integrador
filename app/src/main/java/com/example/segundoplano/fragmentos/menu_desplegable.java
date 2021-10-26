package com.example.segundoplano.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.segundoplano.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link menu_desplegable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class menu_desplegable extends Fragment {
    InteraccionMenu interaccionMenu;
    Button quienesSomos,nuestrosProductos,botonPerfil,botonAviso,botonCerrar;
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
        return inflater.inflate(R.layout.fragment_menu_desplegable, container, false);
    }

    public void Admin(){
        quienesSomos = getView().findViewById(R.id.botonQuienesSomos);
        quienesSomos.setText("Precios");
        quienesSomos.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        nuestrosProductos= getView().findViewById(R.id.botonNuestrosProductos);
        nuestrosProductos.setText("Mensajes");
        nuestrosProductos.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        botonAviso = getView().findViewById(R.id.botonAvisoDePrivacidad);
        botonAviso.setText("Avisos");
        botonAviso.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        botonAviso.setVisibility(View.GONE);

        botonPerfil= getView().findViewById(R.id.botonPerfil);
        botonPerfil.setText("Envios Pendientes");
        botonPerfil.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        botonPerfil.setVisibility(View.GONE);

        botonCerrar= getView().findViewById(R.id.botonCerrar);
        botonCerrar.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

    }
    public void User(){
        quienesSomos = getView().findViewById(R.id.botonQuienesSomos);
        quienesSomos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaccionMenu.BotonQuienesSomos();
                cerrarFragmento();

            }
        });
        nuestrosProductos= getView().findViewById(R.id.botonNuestrosProductos);
        nuestrosProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaccionMenu.BotonNuestrosProductos();
                cerrarFragmento();
            }
        });
        botonAviso = getView().findViewById(R.id.botonAvisoDePrivacidad);
        botonAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaccionMenu.BotonAvisoDePrivacidad();
                cerrarFragmento();
            }
        });
        botonPerfil= getView().findViewById(R.id.botonPerfil);
        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interaccionMenu.BotonPerfil();
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
                                    interaccionMenu.BotonCerrar();
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
                                    interaccionMenu.BotonCerrar();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
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
        if (mParam1.equals("Admin")){
            Admin();
        }
        User();
        if (mParam2.equals("English")){
            quienesSomos.setText("About us");
            nuestrosProductos.setText("Our products");
            botonAviso.setText("Privacy policy");
            botonCerrar.setText("Sign out");
            botonPerfil.setText("profile");
        }
        if (mParam2.equals("English") && mParam1.equals("Admin")){
            quienesSomos.setText("Prices");
            nuestrosProductos.setText("Messages");
            botonCerrar.setText("Sign out");
        }
    }

    public interface InteraccionMenu{
        public void BotonQuienesSomos();
        public void BotonNuestrosProductos();
        public  void BotonAvisoDePrivacidad();
        public  void BotonPerfil();
        public  void BotonCerrar();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        interaccionMenu = (InteraccionMenu) context;
    }
}
package com.example.segundoplano.fragmentos;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.segundoplano.R;
import com.example.segundoplano.herramientas.popUpGenerico;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmento_registrarse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmento_registrarse extends Fragment {

    EditText correo,contra,confContra;
    Button registrarse;
    String Semail;
    String Scontra;
    FirebaseAuth firebaseAuth;
    ImageButton imageButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmento_registrarse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmento_registrarse.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmento_registrarse newInstance(String param1, String param2) {
        fragmento_registrarse fragment = new fragmento_registrarse();
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
        View v =inflater.inflate(R.layout.fragment_fragmento_registrarse, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inicializarVariables();
        firebaseAuth = FirebaseAuth.getInstance();
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }

    public void inicializarVariables(){
        correo= getView().findViewById(R.id.correo_et_registrar);
        contra = getView().findViewById(R.id.contra_et_registrar);
        registrarse= getView().findViewById(R.id.boton_registrarse_register);
        confContra = getView().findViewById(R.id.contra_et_registrar2);
        imageButton = getView().findViewById(R.id.backButtonfragment);
    }

    public  void registrarUsuario(){
        if (contra.getText().toString().equals(confContra.getText().toString())){
            firebaseAuth.createUserWithEmailAndPassword(Semail,Scontra).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser currentUser= firebaseAuth.getCurrentUser();
                        currentUser.sendEmailVerification();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("Registrado")
                                .setMessage("Usuario Registrado Correctamente")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    getFragmentManager().popBackStack();
                                    }
                                });
                        builder.show();
                    }else{
                        new popUpGenerico(getContext(),task.getException().toString(), "Error");
                    }
                }
            });
        }else{
            new popUpGenerico(getContext(),"Las contraseñas no coinciden", "Error");
        }

    }

    private void validarUsuario(){
        Semail = correo.getText().toString().trim();
        Scontra = contra.getText().toString().trim();
        if(TextUtils.isEmpty(Semail)||TextUtils.isEmpty(Scontra)){
            new popUpGenerico(getContext(),"Llene todos los campos requeridos", "Error");
        }else {
            registrarUsuario();
        }
    }
}
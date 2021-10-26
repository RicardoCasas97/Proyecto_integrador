package com.example.segundoplano.fragmentos;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spanned;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.segundoplano.Adapters.AdapterMensajes;
import com.example.segundoplano.Adapters.Mensaje;
import com.example.segundoplano.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mensajes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mensajes extends Fragment {

    private AdapterMensajes adapter;
    EditText edtx_mensaje;
    LinearLayout MensajesFrame;
    RecyclerView recyclerMensajes;
    String correo;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Mensajes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mensajes.
     */
    // TODO: Rename and change types and number of parameters
    public static Mensajes newInstance(String param1, String param2) {
        Mensajes fragment = new Mensajes();
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
        auth= FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        if(mParam1.equals("1")){
            v =inflater.inflate(R.layout.fragment_mensajes2, container, false);
        }else{
            v =inflater.inflate(R.layout.fragment_mensajes, container, false);
        }
        return v;
    }

    public void InicializarVariables(){
        adapter= new AdapterMensajes(getContext());
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        edtx_mensaje= getView().findViewById(R.id.edtx_mensaje);
        MensajesFrame = getView().findViewById(R.id.MensajesFrame);
        recyclerMensajes= getView().findViewById(R.id.recyclerMensajes);
        recyclerMensajes.setLayoutManager(l);
        recyclerMensajes.setAdapter(adapter);
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InicializarVariables();
        if (firebaseUser!=null){
            correo=(firebaseUser.getEmail());
        }
        edtx_mensaje.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String mensaje = edtx_mensaje.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                String currentDateandTime = simpleDateFormat.format(new Date());
                databaseReference.push().setValue(new Mensaje(mensaje,correo,"",currentDateandTime));
                view.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtx_mensaje.getWindowToken(),0);
                edtx_mensaje.setText("");
                return false;
            }
        });
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                autoScroll();
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Mensaje m = snapshot.getValue(Mensaje.class);
                adapter.addMensaje(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (mParam2.equals("English")){
            TextView etiqueta1=view.findViewById(R.id.etiqueta1);
            etiqueta1.setText("Messages");
            edtx_mensaje.setHint("write a message...");
        }
    }

    public void autoScroll(){
        recyclerMensajes.scrollToPosition(adapter.getItemCount()-1);
    }
}
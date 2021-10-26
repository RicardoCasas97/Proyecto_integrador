package com.example.segundoplano.fragmentos.fragmentosMain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.segundoplano.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvisoDePrivacidad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvisoDePrivacidad extends Fragment {
    TextView textView2,textView8;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AvisoDePrivacidad() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvisoDePrivacidad.
     */
    // TODO: Rename and change types and number of parameters
    public static AvisoDePrivacidad newInstance(String param1, String param2) {
        AvisoDePrivacidad fragment = new AvisoDePrivacidad();
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
        return inflater.inflate(R.layout.fragment_aviso_de_privacidad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView2= view.findViewById(R.id.textView2);
        textView8 = view.findViewById(R.id.textView8);
        if (mParam2.equals("English")){
            textView2.setText("Privacy policy");
            textView8.setText("We commit ourselves to the client with data protection and privacy The protection of your personal data and your privacy is the main concern of VindiAPP. Principles VindiAPP expresses its commitment to privacy and data protection by adopting the following principles. VindiAPP uses personal data in a legal, fair and transparent way. VindiAPP does not collect more personal data than necessary and only does so for a legitimate purpose. VindiAPP does not retain more data than necessary, or for a period longer than necessary. VindiAPP protects personal data with appropriate security measures.");
        }

    }
}
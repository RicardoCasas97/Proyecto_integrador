package com.example.segundoplano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.segundoplano.ActivityAdmin.ActivityAdminMain;
import com.example.segundoplano.fragmentos.fragmento_registrarse;
import com.example.segundoplano.herramientas.popUpGenerico;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Toolbar toolbar;
    private  FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener stateListeners;
    Spinner spinner;
    EditText user,password;
    boolean admin;
    RadioGroup grupo;
    String idioma;
    RadioButton rb_Admin,rb_User;
    Button ingresar, registrarse;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark)); //status bar or the time bar at the top
        }
        inicializarVariables();
        firebaseAuth = FirebaseAuth.getInstance();

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contra = password.getText().toString().trim();
                if(ingresar.getText().toString().equals("")||password.getText().toString().equals("")){
                    new popUpGenerico(Login.this,"Ingrese todos los campos","Error");
                }else{
                    if (grupo.getCheckedRadioButtonId()==-1){
                        new popUpGenerico(Login.this,"Por favor seleccione el tipo de acceso","Error");
                    }else
                    {
                        loggearUsuario(contra);
                    }
                }
            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmento_registrarse fragmento_registrarse = new fragmento_registrarse();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations( R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.slide_in,   // popEnter
                        R.anim.slide_out );

                transaction.replace(R.id.areaDeTrabajo,fragmento_registrarse).addToBackStack(null);
                transaction.commit();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idioma= spinner.getSelectedItem().toString();
                bundle.putString("idioma",idioma);
                if (spinner.getSelectedItem().toString().equals("English")){
                    user.setHint("User");
                    password.setHint("Password");
                    ingresar.setText("Login");
                    registrarse.setText("Sign in");
                }else{
                    user.setHint("Usuario");
                    password.setHint("Contraseña");
                    ingresar.setText("Ingresar");
                    registrarse.setText("Registrarse");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getSupportActionBar().setTitle("NomPend");
    }

    public void inicializarVariables(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user= findViewById(R.id.usuario_et_login);
        password= findViewById(R.id.contra_et_login);
        ingresar= findViewById(R.id.boton_ingresar_login);
        registrarse= findViewById(R.id.boton_registrarse_login);
        grupo = findViewById(R.id.radioGroup);
        rb_Admin= findViewById(R.id.rb_Admin);
        rb_User= findViewById(R.id.rb_User);

        bundle= new Bundle();

        spinner= findViewById(R.id.spinner);
        String[] array= {"Español","English"};
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner_normal,array));
    }

    public void loggearUsuario(String contra){
        firebaseAuth.signInWithEmailAndPassword(user.getText().toString(),contra).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final FirebaseUser  currentUser= firebaseAuth.getCurrentUser();
                    if (currentUser.isEmailVerified()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this).setTitle("Registrado")
                                .setMessage("Usuario ingresado Correctamente")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        user.setText("");
                                        password.setText("");
                                        if (rb_Admin.isChecked()){
                                            Intent intent = new Intent(Login.this, ActivityAdminMain.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }else{
                                            Intent intent = new Intent(Login.this,MainActivity.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }

                                    }
                                });
                        builder.show();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this).setTitle("No verificado")
                                .setMessage("Usuario ingresado no ha verificado su correo electronico").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.show();
                    }
                }else{
                    new popUpGenerico(Login.this,task.getException().getMessage(), "Error");
                }
            }
        });
    }

}
package com.example.segundoplano;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.segundoplano.fragmentos.Mensajes;
import com.example.segundoplano.fragmentos.fragmentosMain.AvisoDePrivacidad;
import com.example.segundoplano.fragmentos.fragmentosMain.LogoPrincipal;
import com.example.segundoplano.fragmentos.fragmentosMain.NuestrosProductos;
import com.example.segundoplano.fragmentos.fragmentosMain.Perfil;
import com.example.segundoplano.fragmentos.fragmentosMain.QuienesSomos;
import com.example.segundoplano.fragmentos.menu_desplegable;
import com.example.segundoplano.herramientas.MovableFloatingActionButton;
import com.example.segundoplano.herramientas.popUpGenerico;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements menu_desplegable.InteraccionMenu, MovableFloatingActionButton.Mensajeria {
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String idioma;
    boolean principalb = false;
    boolean mensajeria= false;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2)
        ;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark)); //status bar or the time bar at the top
        }
        inicializarVariables();
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayoutPrincipal,LogoPrincipal.newInstance("",""));
        transaction.commit();
        auth= FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();
        if (firebaseUser!=null){
            Toast.makeText(MainActivity.this,firebaseUser.getEmail(),Toast.LENGTH_LONG).show();
        }
    }



    public void inicializarVariables(){
        bundle=getIntent().getExtras();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
           idioma= bundle.getString("idioma");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_principal,menu);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ((id==R.id.abrirMenu)){
            Fragment findFragment = getSupportFragmentManager().findFragmentByTag("menu");
            if (findFragment==null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_derecha,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.slide_in_derecha,   // popEnter
                        R.anim.slide_out_derecha );
                transaction.replace(R.id.areaDeTrabajo,menu_desplegable.newInstance("User",idioma),"menu").addToBackStack(null);
                transaction.commit();
            }else{
                getSupportFragmentManager().popBackStack();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void BotonQuienesSomos() {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayoutPrincipal,QuienesSomos.newInstance("",idioma));
        //transaction.commit();
        principalb=true;
    }

    @Override
    public void BotonNuestrosProductos() {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayoutPrincipal,NuestrosProductos.newInstance("",idioma));
        //transaction.commit();
        principalb=true;
    }

    @Override
    public void BotonAvisoDePrivacidad() {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayoutPrincipal,AvisoDePrivacidad.newInstance("",idioma));
        //transaction.commit();
        principalb=true;
    }

    @Override
    public void BotonPerfil() {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayoutPrincipal,Perfil.newInstance("",idioma));
        //transaction.commit();
        principalb=true;
    }

    @Override
    public void BotonCerrar() {

        finish();
    }

    @Override
    public void onBackPressed() {
        if (principalb){
            LogoPrincipal principal = new LogoPrincipal();
            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentLayoutPrincipal,principal);
            transaction.commit();
            getSupportFragmentManager().popBackStack();
            principalb= false;
        }else {
            getSupportFragmentManager().popBackStack();
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            if (idioma.equals("Español")){
                builder.setMessage("¿Desea cerrar sesión?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }else {
                builder.setMessage("Do you want to sign out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }

        }

    }

    @Override
    public void Activar() {
        if (!mensajeria){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.fade_out );
            transaction.replace(R.id.areaDeTrabajo,Mensajes.newInstance("",idioma)).addToBackStack(null);
            transaction.commit();
            principalb=true;
            mensajeria=true;
        }else{
            getSupportFragmentManager().popBackStack();
            mensajeria=false;
        }

    }

}
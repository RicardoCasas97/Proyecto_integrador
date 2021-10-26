package com.example.segundoplano.ActivityAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.segundoplano.MainActivity;
import com.example.segundoplano.R;
import com.example.segundoplano.fragmentos.fragmentosMain.LogoPrincipal;
import com.example.segundoplano.fragmentos.fragmentosMain.QuienesSomos;
import com.example.segundoplano.fragmentos.fragmentosMainAdmin.Mensajes_frag;
import com.example.segundoplano.fragmentos.fragmentosMainAdmin.Precios;
import com.example.segundoplano.fragmentos.menu_desplegable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityAdminMain extends AppCompatActivity  implements menu_desplegable.InteraccionMenu{
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    Bundle b;
    String idioma;

    boolean principalb = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        InicializarVariables();

        auth= FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();
        if (firebaseUser!=null){
            Toast.makeText(ActivityAdminMain.this,firebaseUser.getEmail(),Toast.LENGTH_LONG).show();
        }
    }

    public void InicializarVariables(){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.moradoAdmin)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.moradoAdmin)); //status bar or the time bar at the top
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b= getIntent().getExtras();
        idioma= b.getString("idioma");
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ((id==R.id.abrirMenu)){
            Fragment findFragment = getSupportFragmentManager().findFragmentByTag("menu");
            if (findFragment==null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_derecha,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.slide_in_derecha,   // popEnter
                        R.anim.slide_out_derecha );
                transaction.replace(R.id.areaDeTrabajo,menu_desplegable.newInstance("Admin",idioma),"menu").addToBackStack(null);
                transaction.commit();

            }else{
                getSupportFragmentManager().popBackStack();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void BotonQuienesSomos() {
        //QuienesSomos somos = new QuienesSomos();
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayoutPrincipal, Precios.newInstance("",idioma));
        transaction.commit();
        principalb=true;
    }

    @Override
    public void BotonNuestrosProductos() {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayoutPrincipal, Mensajes_frag.newInstance("1",idioma));
        transaction.commit();
        principalb=true;
    }

    @Override
    public void BotonAvisoDePrivacidad() {

    }

    @Override
    public void BotonPerfil() {

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
            if (idioma.equals("Español")){
                AlertDialog.Builder builder= new AlertDialog.Builder(ActivityAdminMain.this);
                builder.setMessage("¿Desea cerrar sesión?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityAdminMain.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }else {
                AlertDialog.Builder builder= new AlertDialog.Builder(ActivityAdminMain.this);
                builder.setMessage("Do you want to sign out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityAdminMain.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }

        }

    }
}
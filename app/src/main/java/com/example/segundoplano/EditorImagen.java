package com.example.segundoplano;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.segundoplano.fragmentos.menu_desplegable;
import com.example.segundoplano.herramientas.popUpGenerico;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import javax.xml.transform.Result;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;

@RequiresApi(api = Build.VERSION_CODES.P)
public class EditorImagen extends AppCompatActivity {
    //Use custom font using latest support library
    Typeface mTextRobotoTf = Typeface.create(null,700,false);
    Toolbar toolbar;
    //loading font from assest
    //Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");
    ImageButton bt_buscar,bt_pincel,bt_texto,bt_borrador;
    PhotoEditorView imagenView;
    FrameLayout frame_texto;
    PhotoEditor editor;
    EditText texto_editar;
    String textoEdit;
    Bitmap fotoGuardada;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    StorageReference reference;
    private static int RESULT_LOAD_IMG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_imagen);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark)); //status bar or the time bar at the top
        }
        auth= FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        declararVariables();
        agregarListeners();
    }

    public void declararVariables(){
        bt_buscar= findViewById(R.id.bt_buscar);
        imagenView = findViewById(R.id.Imagen_view);
        bt_pincel= findViewById(R.id.bt_pincel);
        bt_texto= findViewById(R.id.bt_texto);
        bt_borrador= findViewById(R.id.bt_borrador);
        frame_texto = findViewById(R.id.frame_texto);
        texto_editar= findViewById(R.id.texto_editar);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       try {
           getMenuInflater().inflate(R.menu.menu_editor,menu);
       }catch (Exception e){
           e.getStackTrace();
       }
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ((id==R.id.guardarImagen)){
            editor.saveAsBitmap(new OnSaveBitmap() {
                @Override
                public void onBitmapReady(Bitmap bitmap) {
                    fotoGuardada = bitmap;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                    byte[] data =baos.toByteArray();
                    reference = FirebaseStorage.getInstance().getReference("Pedidos/"+ firebaseUser.getEmail()+"/"+UUID.randomUUID());
                    reference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(EditorImagen.this);
                            builder.setMessage("Guardado Correctamente");
                            builder.setTitle("Aviso");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            builder.show();
                        }
                    });
                }

                @Override
                public void onFailure(Exception e) {
                    e.getStackTrace();
                    new popUpGenerico(EditorImagen.this,"No pudo ser Guardado correctamente","Error");
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    public void agregarListeners(){

        bt_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery(getCurrentFocus());
            }
        });
        bt_pincel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setBrushSize(10);
                editor.setOpacity(100);
                editor.setBrushColor(000000);
            }
        });
        bt_borrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.brushEraser();
            }
        });
        bt_texto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editor
                frame_texto.setVisibility(View.VISIBLE);
                texto_editar.requestFocus();
                texto_editar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        textoEdit= texto_editar.getText().toString();
                        texto_editar.setText("");
                        editor.addText(mTextRobotoTf, textoEdit, Color.parseColor("#000000"));
                        frame_texto.setVisibility(View.GONE);
                        return false;
                    }
                });
            }
        });
    }
    public void loadImagefromGallery(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            // When an Image is picked
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imagenView.getSource().setImageBitmap(selectedImage);
                    editor = new PhotoEditor.Builder(this,imagenView)
                            .setPinchTextScalable(true)
                            .setDefaultTextTypeface(mTextRobotoTf)
                            //.setDefaultEmojiTypeface(mEmojiTypeFace)
                            .build();
                    findViewById(R.id.sel_image).setVisibility(View.GONE);
                    findViewById(R.id.sel_edit).setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(EditorImagen.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(EditorImagen.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
    }
}
package com.example.segundoplano.herramientas;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class EsconderTeclado {
    Activity a;
    public EsconderTeclado(Activity a) {
        this.a = a;

        if (a.getCurrentFocus()!=null){
            InputMethodManager inputMethodManager = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(),0);
        }
    }
}

package com.example.cyber_net.sig.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SharedID {
    //key
    public static final String SP_LEY = "POS";

    //key value
    public static final String SP_ID = "id_pos";
    public static final String SP_ID_GALERI = "id_pos";

    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    //buat sharefpref dari class lain
    public SharedID(Context context){
        //dengan key sampahku
        sp = context.getSharedPreferences(SP_LEY, Context.MODE_PRIVATE);
        this.context = context;
        spEditor = sp.edit();
    }

    public void saveIDPos(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
        //Toast.makeText(context, "data disimpan "+value, Toast.LENGTH_SHORT).show();
    }

    public void saveIDGaleri(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
        //Toast.makeText(context, "data disimpan "+value, Toast.LENGTH_SHORT).show();
    }

    public String getIDPos(){
        return sp.getString(SP_ID, "");
    }

    public String getIDGaleri(){
        return sp.getString(SP_ID_GALERI, "");
    }
}
package com.example.cyber_net.sig.activity.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.model.response.ResponseInsert;
import com.example.cyber_net.sig.model.response.ResponsePos;
import com.example.cyber_net.sig.model.response.ResponsePos;
import com.example.cyber_net.sig.model.response.item.ManfaatItem;
import com.example.cyber_net.sig.model.response.item.PosPendakianItem;
import com.example.cyber_net.sig.network.ApiService;
import com.example.cyber_net.sig.network.RetroClient;
import com.example.cyber_net.sig.shared.SharedID;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.cyber_net.sig.activity.add.AddWisata.removeString;
import static com.example.cyber_net.sig.helper.Contans.KEY_DATA;
import static com.example.cyber_net.sig.helper.Contans.RESULT_UPDATE;
import static com.example.cyber_net.sig.helper.ConvertDate.getDateTime;

public class AddPos extends AppCompatActivity {

    @BindView(R.id.txt_pos)
    EditText txtPos;
    @BindView(R.id.txt_latitude)
    EditText txtLatitude;
    @BindView(R.id.txt_longitude)
    EditText txtLongitude;
    PosPendakianItem data;
    SharedID sharedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pos);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedID = new SharedID(this);

        data = getIntent().getParcelableExtra(KEY_DATA);
        if (data != null) {
            txtPos.setText(data.getNama());
            txtLatitude.setText(data.getLatitude());
            txtLongitude.setText(data.getLongitude());getSupportActionBar().setTitle("Ubah Data");
        }else {
            getSupportActionBar().setTitle("Tambah Data");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            if (data == null) {
                simpan();
            } else {
                //jika place sama dengan nama imagenya
                updateWithField(data.getIdPos());
                Intent intent = new Intent();
                setResult(RESULT_UPDATE, intent);
                //hentiak activiy ini dengan finish
                finish();
            }
            //Toast.makeText(this, ""+edtJudul.getText().toString(), Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void simpan() {
        //eksekusi
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.insertPos(sharedID.getIDPos(), txtPos.getText().toString(), txtLatitude.getText().toString(),
                txtLongitude.getText().toString());
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(AddPos.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddPos.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(AddPos.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWithField(String id) {
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updatePos(id, txtPos.getText().toString(), txtLatitude.getText().toString(),
                txtLongitude.getText().toString());

        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(AddPos.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddPos.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(AddPos.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

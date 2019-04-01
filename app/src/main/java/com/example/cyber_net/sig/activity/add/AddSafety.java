package com.example.cyber_net.sig.activity.add;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.model.response.ResponseInsert;
import com.example.cyber_net.sig.model.response.item.SafetyItem;
import com.example.cyber_net.sig.network.ApiService;
import com.example.cyber_net.sig.network.RetroClient;

import net.dankito.richtexteditor.android.RichTextEditor;
import net.dankito.richtexteditor.android.toolbar.GroupedCommandsEditorToolbar;
import net.dankito.utils.android.permissions.IPermissionsService;
import net.dankito.utils.android.permissions.PermissionsService;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.cyber_net.sig.activity.add.AddWisata.removeString;
import static com.example.cyber_net.sig.helper.Contans.BASE_URL_IMAGE;
import static com.example.cyber_net.sig.helper.Contans.KEY_DATA;
import static com.example.cyber_net.sig.helper.ConvertDate.customTanggal;
import static com.example.cyber_net.sig.helper.ConvertDate.getDateTime;
import static com.example.cyber_net.sig.helper.ConvertImage.getResizedBitmap;
import static com.example.cyber_net.sig.helper.ConvertImage.setToImageView;

public class AddSafety extends AppCompatActivity {
    @BindView(R.id.edt_judul)
    EditText edtJudul;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_insert)
    TextView txtInsert;
    @BindView(R.id.txt_place)
    TextView txtPlace;

    //var img volley
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    String part_image;

    private RichTextEditor editor;
    private GroupedCommandsEditorToolbar bottomGroupedCommandsToolbar;
    private IPermissionsService permissionsService = new PermissionsService(this);

    SafetyItem data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artikel);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editor = findViewById(R.id.editor);

        // this is needed if you like to insert images so that the user gets asked for permission to access external storage if needed
        // see also onRequestPermissionsResult() below
        editor.setPermissionsService(permissionsService);

        bottomGroupedCommandsToolbar = findViewById(R.id.editorToolbar);
        bottomGroupedCommandsToolbar.setEditor(editor);

        editor.setEditorFontSize(20);
        editor.setPadding((4 * (int) getResources().getDisplayMetrics().density));
        editor.setHtml(" ");

        //edtJudul.getText().clear();
        Log.d("tanggal", getDateTime());
        //Toast.makeText(this, ""+tglHariIni(), Toast.LENGTH_SHORT).show();
        //get nilai from adapter
        data = getIntent().getParcelableExtra(KEY_DATA);
        if (data != null) {
            //Toast.makeText(this, data.getIdAdmin(), Toast.LENGTH_SHORT).show();
            txtInsert.setText(data.getImage());
            edtJudul.setText(data.getJudul());
            editor.setHtml(data.getDeskripsi());getSupportActionBar().setTitle("Ubah Data");
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
                if (data.getImage() == txtInsert.getText().toString()) {
                    updateWithField(data.getIdSafety());
                    Toast.makeText(this, data.getIdSafety(), Toast.LENGTH_SHORT).show();
                } else {
                    updateWithImage(data.getIdSafety());
                    Toast.makeText(this, data.getIdSafety(), Toast.LENGTH_SHORT).show();
                }
            }
            //Toast.makeText(this, ""+edtJudul.getText().toString(), Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (bottomGroupedCommandsToolbar.handlesBackButtonPress() == false) {
            super.onBackPressed();
        }
    }

    //select image
    @OnClick(R.id.txt_insert)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512), img, decoded);
                //code ku
                Uri dataImage = data.getData();
                String[] imageProjection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataImage, imageProjection, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageProjection[0]);
                    part_image = cursor.getString(indexImage);

                    if (part_image != null) {
                        // /storage/emulated/0/download/29386176_987202911432105_5041564070140626907_n.jpg
                        File image = new File(part_image);
                        img.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                        Log.i("Ini image", String.valueOf(image));
                        txtInsert.setText(image.getName());

                        Log.i("Ini nama image ; ", image.getName());
                    }
                } //end if

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //insert
    private void simpan() {
        File imageFile = new File(part_image);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        //parm 1 samakan dengan parameter di backend
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        //eksekusi
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.insertSafety(1, partImage, edtJudul.getText().toString(),
                editor.getCachedHtml(), getDateTime());
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(AddSafety.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddSafety.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(AddSafety.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWithImage(String id) {
        File imageFile = new File(part_image);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        //parm 1 samakan dengan parameter di backend
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        //eksekusi
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateSafety1(Integer.parseInt(id), partImage, edtJudul.getText().toString(),
                removeString(editor.getCachedHtml()));
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(AddSafety.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddSafety.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(AddSafety.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWithField(String id) {
        ApiService service = RetroClient.getApiService();
        Call<ResponseInsert> call = service.updateSafety2(Integer.parseInt(id), edtJudul.getText().toString(), removeString(editor.getCachedHtml()));

        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(AddSafety.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddSafety.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(AddSafety.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

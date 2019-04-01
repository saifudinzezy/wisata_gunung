package com.example.cyber_net.sig.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.model.response.item.ManfaatItem;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.cyber_net.sig.helper.Contans.BASE_URL_IMAGE;
import static com.example.cyber_net.sig.helper.Contans.KEY_DATA;
import static com.example.cyber_net.sig.helper.ConvertDate.customTanggal;

public class DetailManfaat extends AppCompatActivity {

    @BindView(R.id.img_cover)
    ImageView imgCover;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.txt_tanggal)
    TextView txtTanggal;
    @BindView(R.id.txt_jam)
    TextView txtJam;
    @BindView(R.id.txt_penulis)
    TextView txtPenulis;
    @BindView(R.id.txt_isi)
    WebView txtIsi;
    //
    ManfaatItem data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_manfaat);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = getIntent().getParcelableExtra(KEY_DATA);
        if (data != null) {
            txtTanggal.setText(customTanggal(data.getTanggal(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy"));
            txtJam.setText(customTanggal(data.getTanggal(), "yyyy-MM-dd HH:mm:ss", "HH:mm"));
            txtPenulis.setText("Di tulis oleh : " + data.getNama());

            Glide.with(this)
                    .load(BASE_URL_IMAGE + data.getImage())
                    .into(imgCover);
            txtIsi.getSettings().setJavaScriptEnabled(true);
            txtIsi.loadData(data.getDeskripsi(), "text/html; charset=utf-8", "UTF-8");
        }

        //hide tittle in bar
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    String title = "Title";
                    if (data != null) {
                        title = data.getJudul();
                    }
                    collapsingToolbarLayout.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Toast.makeText(this, "Share It", Toast.LENGTH_SHORT).show();
            try {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "";

                if (data != null) {
                    shareBody = data.getJudul();
                    //hilangkan semua tag html
                    //https://stackoverflow.com/questions/240546/remove-html-tags-from-a-string
                    String isi = android.text.Html.fromHtml(data.getDeskripsi()).toString();
                    //potong string biar tidak panjang

                    if (isi.length() > 50) {
                        //https://stackoverflow.com/questions/8369708/limiting-the-number-of-characters-in-a-string-and-chopping-off-the-rest/8369746
                        isi = isi.substring(0, 100);
                    }

                    shareBody += " \n" + isi + "...";
                }

                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.SUBJEK));
                //isi text yang di share
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.SHARE_VIA)));
            } catch (Exception e) {

            }
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
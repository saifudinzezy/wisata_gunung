package com.example.cyber_net.sig.activity.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.adapter.GalleryAdapter;
import com.example.cyber_net.sig.helper.Function;
import com.example.cyber_net.sig.helper.SlideshowDialogFragment;
import com.example.cyber_net.sig.model.cuaca.ResponseWeather;
import com.example.cyber_net.sig.model.response.ResponsePos;
import com.example.cyber_net.sig.model.response.ResponseView;
import com.example.cyber_net.sig.model.response.item.DataItem;
import com.example.cyber_net.sig.model.response.item.PosPendakianItem;
import com.example.cyber_net.sig.model.response.item.WisataItem;
import com.example.cyber_net.sig.network.ApiService;
import com.example.cyber_net.sig.network.InitWeather;
import com.example.cyber_net.sig.network.RetroClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.cyber_net.sig.BuildConfig.API;
import static com.example.cyber_net.sig.helper.Contans.BASE_URL_IMAGE_WISATA;
import static com.example.cyber_net.sig.helper.Contans.KEY_DATA;
import static com.example.cyber_net.sig.helper.Function.cuaca;

public class DetailWisata extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.no_image)
    TextView noImage;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.pd)
    ProgressBar pd;
    @BindView(R.id.no_pos)
    TextView noPos;
    @BindView(R.id.pd_pos)
    ProgressBar pdPos;
    @BindView(R.id.wrapper_map)
    LinearLayout wrapperMap;
    @BindView(R.id.txt_cuaca)
    TextView txtCuaca;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.txt_ket_cuaca)
    TextView txtKetCuaca;
    @BindView(R.id.txt_derajat)
    TextView txtDerajat;
    @BindView(R.id.txt_lokasi)
    TextView txtLokasi;
    @BindView(R.id.pd_cuaca)
    ProgressBar pdCuaca;
    @BindView(R.id.img_cover)
    ImageView imgCover;
    /*@BindView(R.id.txt_deskripsi)
    HtmlTextView txtDeskripsi;*/
    @BindView(R.id.txt_deskripsi)
    WebView txtDeskripsi;
    @BindView(R.id.txt_alamat)
    TextView txtAlamat;
    @BindView(R.id.txt_pos)
    TextView txtPos;
    @BindView(R.id.txt_tanah)
    TextView txtTanah;
    @BindView(R.id.txt_gunung)
    TextView txtGunung;
    @BindView(R.id.txt_tinggi)
    TextView txtTinggi;
    @BindView(R.id.txt_fasilitas)
    WebView txtFasilitas;
    private GoogleMap mMap;
    private ArrayList<DataItem> images;
    WisataItem data;

    //weather icon
    Typeface weatherFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get nilai from adapter
        data = getIntent().getParcelableExtra(KEY_DATA);
        if (data != null) {
            getData(data.getIdWisata());
            //cuaca
            //metric = untuk satuan celcius
            getWeather(data.getLatitude(), data.getLongitude(), "metric", API);
            Glide.with(this)
                    .load(BASE_URL_IMAGE_WISATA + data.getImage())
                    .into(imgCover);
            //txtDeskripsi.setHtml(data.getDeskripsi());
            txtAlamat.setText(data.getLokasi());
            txtTanah.setText(data.getTipe_tanah());
            txtGunung.setText(data.getTipe_gunung());
            txtTinggi.setText(data.getKetinggian());
            txtDeskripsi.getSettings().setJavaScriptEnabled(true);
            txtDeskripsi.loadData(data.getDeskripsi(), "text/html; charset=utf-8", "UTF-8");
            txtFasilitas.getSettings().setJavaScriptEnabled(true);
            txtFasilitas.loadData(data.getFasilitas(), "text/html; charset=utf-8", "UTF-8");
        }

        //list
        images = new ArrayList<>();

        //back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //fonts
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");
        txtCuaca.setTypeface(weatherFont);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (data != null) {
                        //maps
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + data.getLatitude() + "," + data.getLongitude() + "");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                } catch (Exception e) {
                    Snackbar.make(view, "Tidak ada aplikasi google maps, silahkan download dulu", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        //recyclerview galery
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //kirim data jika ada item yagn di click
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-6.18938381651682, 106.84674588079042);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        if (data != null) {
            getPosPendakian(data.getIdWisata());
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void getData(String id) {
        recyclerView.setVisibility(View.VISIBLE);
        noImage.setVisibility(View.GONE);
        pd.setVisibility(View.VISIBLE);

        //cek jika data tidak kosong
        //maka di hapus
        if (images != null) {
            images.clear();
        }

        ApiService apiService = RetroClient.getApiService();
        Call<ResponseView> call = apiService.getView(id);
        call.enqueue(new Callback<ResponseView>() {
            @Override
            public void onResponse(Call<ResponseView> call, Response<ResponseView> response) {
                List<DataItem> hasilPesan = response.body().getData();
                Log.e("Tag", "Hasil image :" + new Gson().toJson(hasilPesan));
                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    //tambahkan image
                    images.addAll(hasilPesan);
                    GalleryAdapter adapterPesan = new GalleryAdapter(DetailWisata.this, images);
                    recyclerView.setAdapter(adapterPesan);
                    pd.setVisibility(View.GONE);
                } else {
                    Log.e("Tag", "Gagal req data ");
                    recyclerView.setVisibility(View.GONE);
                    noImage.setVisibility(View.VISIBLE);
                    pd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseView> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                noImage.setVisibility(View.VISIBLE);
                pd.setVisibility(View.GONE);
            }
        });
    }

    private void getPosPendakian(String id) {
        wrapperMap.setVisibility(View.VISIBLE);
        noPos.setVisibility(View.GONE);
        pdPos.setVisibility(View.VISIBLE);

        ApiService apiService = RetroClient.getApiService();
        Call<ResponsePos> call = apiService.getPosPendakian(id);

        call.enqueue(new Callback<ResponsePos>() {
            @Override
            public void onResponse(Call<ResponsePos> call, Response<ResponsePos> response) {
                List<PosPendakianItem> hasilPesan = response.body().getPosPendakian();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    initMarker(hasilPesan);
                    pdPos.setVisibility(View.GONE);
                    txtPos.setText(hasilPesan.get(0).getPos());
                    wrapperMap.setVisibility(View.VISIBLE);
                } else {
                    Log.e("Tag", "Gagal req data ");
                    wrapperMap.setVisibility(View.GONE);
                    txtPos.setText("0");
                    noPos.setVisibility(View.VISIBLE);
                    pdPos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePos> call, Throwable t) {
                wrapperMap.setVisibility(View.GONE);
                noPos.setVisibility(View.VISIBLE);
                txtPos.setText("0");
                pdPos.setVisibility(View.GONE);
            }
        });
    }

    private void initMarker(List<PosPendakianItem> mListMarker) {
        //iterasi semua data dan tampilkan markernya
        for (int i = 0; i < mListMarker.size(); i++) {
            //set latlng nya
            LatLng location = new LatLng(Double.parseDouble(mListMarker.get(i).getLatitude()),
                    Double.parseDouble(mListMarker.get(i).getLongitude()));
            //tambahkan markernya
            mMap.addMarker(new MarkerOptions().position(location).title(mListMarker.get(i).getNama()));
            //set latlng index 0
            LatLng latLng = new LatLng(Double.parseDouble(mListMarker.get(0).getLatitude()),
                    Double.parseDouble(mListMarker.get(0).getLongitude()));
            //lalu arahkan zooming ke marker index ke 0
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 17.0f));
        }
    }

    private void getWeather(String latitude, String longitude, String units, String api) {
        pdCuaca.setVisibility(View.VISIBLE);
        ApiService apiService = InitWeather.getApiService();
        Call<ResponseWeather> call = apiService.getWeatherKoordinat(latitude, longitude, units, api);

        call.enqueue(new Callback<ResponseWeather>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<ResponseWeather> call, Response<ResponseWeather> response) {
                //Log.e("Tag", "Hasil List :" + new Gson().toJson(response));
                try {
                    if (response.body().getCod() == 200) {
                        if (data != null) {
                            Drawable img = getResources().getDrawable(R.drawable.ic_location_on_black_24dp);
                            //sesuaikan image sesuai isian bisa di start/awal, top/atas,end/akhir, bottom/bawah
                            txtLokasi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                            txtLokasi.setText(data.getJudul());
                        }

                        //keterangan
                        txtKetCuaca.setText(cuaca(response.body().getWeather().get(0).getId()));
                        //icon
                        txtCuaca.setText(Html.fromHtml(Function.setWeatherIcon(response.body().getWeather().get(0).getId(),
                                response.body().getSys().getSunrise() * 1000,
                                response.body().getSys().getSunset() * 1000)));
                        //derajat
                        int cuaca = (int) response.body().getMain().getTemp();
                        //txtDerajat.setText(String.format("%.2f", response.body().getMain().getTemp())+ " ℃");
                        txtDerajat.setText(String.valueOf(cuaca) + " ℃");
                        pdCuaca.setVisibility(View.GONE);
                    } else {
                        Log.e("Tag", "Gagal req data ");
                        pdCuaca.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Toast.makeText(DetailWisata.this, "Cuaca tidak ditemukan", Toast.LENGTH_SHORT).show();
                    pdCuaca.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseWeather> call, Throwable t) {
                pdCuaca.setVisibility(View.GONE);
                Snackbar.make(recyclerView, "Tidak Ada Koneksi internet", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ulangi", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data != null) {
                                    //cuaca
                                    getWeather(data.getLatitude(), data.getLongitude(), "metric", API);
                                }
                            }
                        }).show();
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
                    String isi = Html.fromHtml(data.getDeskripsi()).toString();
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
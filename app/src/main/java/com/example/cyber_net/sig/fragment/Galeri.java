package com.example.cyber_net.sig.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.activity.add.AddGaleri;
import com.example.cyber_net.sig.activity.add.AddManfaat;
import com.example.cyber_net.sig.adapter.GaleriAdapter;
import com.example.cyber_net.sig.adapter.PosAdapter;
import com.example.cyber_net.sig.adapter.spinner.CustomAdapter;
import com.example.cyber_net.sig.model.response.ResponseView;
import com.example.cyber_net.sig.model.response.ResponseWisata;
import com.example.cyber_net.sig.model.response.item.DataItem;
import com.example.cyber_net.sig.model.response.item.PosPendakianItem;
import com.example.cyber_net.sig.model.response.item.WisataItem;
import com.example.cyber_net.sig.network.ApiService;
import com.example.cyber_net.sig.network.RetroClient;
import com.example.cyber_net.sig.shared.SharedID;
import com.google.gson.Gson;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.cyber_net.sig.helper.Contans.BASE_URL_IMAGE_GALERI;
import static com.example.cyber_net.sig.shared.SharedID.SP_ID;
import static com.example.cyber_net.sig.shared.SharedID.SP_ID_GALERI;

public class Galeri extends Fragment {

    @BindView(R.id.sp)
    Spinner sp;
    @BindView(R.id.txt)
    TextView txt;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tambah)
    FloatingActionButton tambah;
    Unbinder unbinder;
    SharedID sharedID;
    CustomAdapter adapter;
    List<WisataItem> hasilPesan;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ImageView gambar;
    private Context context;

    public Galeri() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        progress.setVisibility(View.GONE);
        //shared
        sharedID = new SharedID(getActivity());
        //sharedID.saveIDPos(SP_ID, null);
        getWIsata();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), hasilPesan.get(position).getJudul(), Toast.LENGTH_SHORT).show();
                try {
                    getGaleri(hasilPesan.get(position).getIdWisata());
                    sharedID.saveIDPos(SP_ID_GALERI, hasilPesan.get(position).getIdWisata());
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // hide fab
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && tambah.getVisibility() == View.VISIBLE) {
                    tambah.hide();
                } else if (dy < 0 && tambah.getVisibility() != View.VISIBLE) {
                    tambah.show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tambah)
    public void onViewClicked() {
        getActivity().startActivity(new Intent(getActivity(), AddGaleri.class));
    }

    private void getWIsata() {
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseWisata> call = apiService.getWisata();
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Tunggu Sebentar...");
        pd.show();

        call.enqueue(new Callback<ResponseWisata>() {
            @Override
            public void onResponse(Call<ResponseWisata> call, Response<ResponseWisata> response) {
                hasilPesan = response.body().getWisata();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    try {
                        pd.dismiss();
                        //load jika ada data baru
                        adapter = new CustomAdapter(getActivity(), hasilPesan);
                        sp.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        pd.dismiss();
                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseWisata> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

    private void getGaleri(String id) {
        progress.setVisibility(View.VISIBLE);
        rvList.setVisibility(View.VISIBLE);

        ApiService apiService = RetroClient.getApiService();
        Call<ResponseView> call = apiService.getView(id);

        call.enqueue(new Callback<ResponseView>() {
            @Override
            public void onResponse(Call<ResponseView> call, Response<ResponseView> response) {
                List<DataItem> hasilPesan = response.body().getData();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));

                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    Log.e("Tag", "Berhasil req data ");
                    try {
                        ArrayList<DataItem> list = new ArrayList<>();
                        list.addAll(hasilPesan);

                        GaleriAdapter adapterPesan1 = new GaleriAdapter(getActivity(), list, new GaleriAdapter.GaleriAdapterListener() {
                            @Override
                            public void onImageSelected(DataItem image) {
                                dialog = new AlertDialog.Builder(getContext());
                                //buat layout
                                inflater = getLayoutInflater();
                                dialogView = inflater.inflate(R.layout.detail_image, null);
                                dialog.setView(dialogView);
                                dialog.setCancelable(true);
                                //dialog.setIcon(R.mipmap.profil);
                                dialog.setTitle(image.getNama());

                                //inisialisasi
                                gambar = dialogView.findViewById(R.id.thumbnail);

                                Glide.with(getContext())
                                        .load(BASE_URL_IMAGE_GALERI + image.getImage())
                                        .into(gambar);

                                dialog.setPositiveButton("X", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //keluar
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                            }
                        });
                        //  swipeMain.setRefreshing(false);
                        rvList.setAdapter(adapterPesan1);
                        progress.setVisibility(View.GONE);
                        txt.setText(hasilPesan.get(0).getNama());
                    } catch (Exception e) {
                        progress.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    Toast.makeText(getActivity(), "Tidak Ada Gambar Pendakian", Toast.LENGTH_SHORT).show();
                    rvList.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseView> call, Throwable t) {
                Toast.makeText(getActivity(), "Tidak Ada Gambar Pendakian", Toast.LENGTH_SHORT).show();
                rvList.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //reload data
        if (sharedID.getIDPos() != null) getGaleri(sharedID.getIDPos());
    }
}

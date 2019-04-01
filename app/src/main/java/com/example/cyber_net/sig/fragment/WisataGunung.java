package com.example.cyber_net.sig.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.activity.add.AddWisata;
import com.example.cyber_net.sig.adapter.WisataAdapter;
import com.example.cyber_net.sig.model.response.ResponseWisata;
import com.example.cyber_net.sig.model.response.item.WisataItem;
import com.example.cyber_net.sig.network.ApiService;
import com.example.cyber_net.sig.network.RetroClient;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class WisataGunung extends Fragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    @BindView(R.id.txt_notif)
    TextView txtNotif;
    @BindView(R.id.pd)
    ProgressBar pd;
    @BindView(R.id.tambah)
    FloatingActionButton tambah;

    public WisataGunung() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //layoutManager.setStackFromEnd(true);
        rvList.setLayoutManager(new VegaLayoutManager());

        getData();
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

    private void getData() {
        pd.setVisibility(View.VISIBLE);
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseWisata> call = apiService.getWisata();
        call.enqueue(new Callback<ResponseWisata>() {
            @Override
            public void onResponse(Call<ResponseWisata> call, Response<ResponseWisata> response) {
                List<WisataItem> hasilPesan = response.body().getWisata();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    try {
                        //
                        ArrayList<WisataItem> list = new ArrayList<>();
                        list.addAll(hasilPesan);
                        WisataAdapter adapterPesan = new WisataAdapter(getContext(), list);
                        //  swipeMain.setRefreshing(false);
                        rvList.setAdapter(adapterPesan);
                        pd.setVisibility(View.GONE);
                    } catch (Exception e) {

                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    pd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseWisata> call, Throwable t) {
                pd.setVisibility(View.GONE);
                Snackbar.make(rvList, "Tidak Ada Koneksi internet", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ulangi", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        }).show();
            }
        });
    }

    @OnClick(R.id.tambah)
    public void onViewClicked() {
        getActivity().startActivity(new Intent(getActivity(), AddWisata.class));
    }
}
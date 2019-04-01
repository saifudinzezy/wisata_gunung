package com.example.cyber_net.sig.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.activity.add.AddManfaat;
import com.example.cyber_net.sig.adapter.ManfaatAdapter;
import com.example.cyber_net.sig.model.response.ResponseManfaat;
import com.example.cyber_net.sig.model.response.item.ManfaatItem;
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

public class ManfaatPegunungan extends Fragment {

    @BindView(R.id.txt_notif)
    TextView txtNotif;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.pd)
    ProgressBar pd;
    Unbinder unbinder;
    @BindView(R.id.tambah)
    FloatingActionButton tambah;

    public ManfaatPegunungan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        //layoutManager.setStackFromEnd(true);
        rvList.setLayoutManager(new VegaLayoutManager());

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

        getData();
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
        Call<ResponseManfaat> call = apiService.getManfaat();
        call.enqueue(new Callback<ResponseManfaat>() {
            @Override
            public void onResponse(Call<ResponseManfaat> call, Response<ResponseManfaat> response) {
                List<ManfaatItem> hasilPesan = response.body().getManfaat();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));
                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    try {
                        //
                        ArrayList<ManfaatItem> list = new ArrayList<>();
                        list.addAll(hasilPesan);
                        ManfaatAdapter adapterPesan1 = new ManfaatAdapter(getContext(), list);
                        //  swipeMain.setRefreshing(false);
                        rvList.setAdapter(adapterPesan1);
                        pd.setVisibility(View.GONE);

                    } catch (Exception e) {

                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    pd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseManfaat> call, Throwable t) {
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

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick(R.id.tambah)
    public void onViewClicked() {
        getActivity().startActivity(new Intent(getActivity(), AddManfaat.class));
    }
}
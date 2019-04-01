package com.example.cyber_net.sig.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.activity.add.AddPos;
import com.example.cyber_net.sig.activity.add.AddSafety;
import com.example.cyber_net.sig.adapter.PosAdapter;
import com.example.cyber_net.sig.adapter.spinner.CustomAdapter;
import com.example.cyber_net.sig.model.response.ResponsePos;
import com.example.cyber_net.sig.model.response.ResponseWisata;
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

import static com.example.cyber_net.sig.helper.Contans.REQUEST_UPDATE;
import static com.example.cyber_net.sig.helper.Contans.RESULT_UPDATE;
import static com.example.cyber_net.sig.shared.SharedID.SP_ID;

public class PosPendakian extends Fragment {

    @BindView(R.id.sp)
    Spinner sp;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    //ArrayAdapter<String> adapter;
    CustomAdapter adapter;
    List<WisataItem> hasilPesan;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.txt)
    TextView txt;
    SharedID sharedID;
    @BindView(R.id.tambah)
    FloatingActionButton tambah;

    public PosPendakian() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        unbinder = ButterKnife.bind(this, view);
        rvList.setLayoutManager(new VegaLayoutManager());
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
                    getPosPendakian(hasilPesan.get(position).getIdWisata());
                    sharedID.saveIDPos(SP_ID, hasilPesan.get(position).getIdWisata());
                }catch (Exception e){

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

        //getPosPendakian("1");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    private void getPosPendakian(String id) {
        progress.setVisibility(View.VISIBLE);
        rvList.setVisibility(View.VISIBLE);

        ApiService apiService = RetroClient.getApiService();
        Call<ResponsePos> call = apiService.getPosPendakian(id);

        call.enqueue(new Callback<ResponsePos>() {
            @Override
            public void onResponse(Call<ResponsePos> call, Response<ResponsePos> response) {
                List<PosPendakianItem> hasilPesan = response.body().getPosPendakian();
                Log.e("Tag", "Hasil List :" + new Gson().toJson(hasilPesan));

                if (response.body().getResponse().equalsIgnoreCase("true")) {
                    Log.e("Tag", "Berhasil req data ");
                    try {
                        ArrayList<PosPendakianItem> list = new ArrayList<>();
                        list.addAll(hasilPesan);

                        PosAdapter adapterPesan1 = new PosAdapter(getActivity(), list);
                        //  swipeMain.setRefreshing(false);
                        rvList.setAdapter(adapterPesan1);
                        progress.setVisibility(View.GONE);
                        txt.setText(hasilPesan.get(0).getNama());
                    }catch (Exception e){
                        progress.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("Tag", "Gagal req data ");
                    Toast.makeText(getActivity(), "Tidak Ada Pos Pendakian", Toast.LENGTH_SHORT).show();
                    rvList.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePos> call, Throwable t) {
                Toast.makeText(getActivity(), "Tidak Ada Pos Pendakian", Toast.LENGTH_SHORT).show();
                rvList.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.tambah)
    public void onViewClicked() {
        getActivity().startActivity(new Intent(getActivity(), AddPos.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE) {
            //memastikan dia type yang mana
            // jika REQUEST_UPDATE berisi RESULT_UPDATE maka dia kategori update
            //eksekusi
            Toast.makeText(getActivity(), "oke", Toast.LENGTH_SHORT).show();
            if (resultCode == RESULT_UPDATE) {
                Toast.makeText(getActivity(), "berhasil", Toast.LENGTH_SHORT).show();
                getWIsata();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //reload data
        if (sharedID.getIDPos() != null) getPosPendakian(sharedID.getIDPos());
    }
}

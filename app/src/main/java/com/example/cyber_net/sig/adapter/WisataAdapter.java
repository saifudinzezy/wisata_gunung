package com.example.cyber_net.sig.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.activity.add.AddWisata;
import com.example.cyber_net.sig.activity.detail.DetailWisata;
import com.example.cyber_net.sig.model.response.item.WisataItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.cyber_net.sig.helper.Contans.BASE_URL_IMAGE_WISATA;
import static com.example.cyber_net.sig.helper.Contans.KEY_DATA;
import static com.example.cyber_net.sig.helper.Contans.TABEL_ID_WISATA;
import static com.example.cyber_net.sig.helper.Contans.TABEL_WISATA;

public class WisataAdapter extends RecyclerSwipeAdapter<WisataAdapter.SimpleViewHolder> {
    private Context mContext;
    private ArrayList<WisataItem> list;
    private OnClickEdit listener;


    public WisataAdapter(Context context, ArrayList<WisataItem> objects, OnClickEdit listener) {
        this.mContext = context;
        this.list = objects;
        this.listener = listener;
    }

    public WisataAdapter(Context context, ArrayList<WisataItem> objects) {
        this.mContext = context;
        this.list = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wisata, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        //ini link untuk gambar
        String linkGambarMovie = list.get(position).getImage();
        Glide.with(mContext)
                .load(BASE_URL_IMAGE_WISATA + linkGambarMovie)
                .into(viewHolder.imageView);

        viewHolder.txtJudul.setText(list.get(position).getJudul());
        viewHolder.txtLokasi.setText(list.get(position).getLokasi());
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WisataItem data = list.get(position);
                Intent intent = new Intent(mContext, DetailWisata.class);
                intent.putExtra(KEY_DATA, data);
                mContext.startActivity(intent);
            }
        });

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //dari kanan ke kriri
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "Clicked on Edit  ", Toast.LENGTH_SHORT).show();
                //listener.onEdit(list.get(position));
                WisataItem data = list.get(position);
                Intent intent = new Intent(mContext, AddWisata.class);
                intent.putExtra(KEY_DATA, data);
                mContext.startActivity(intent);
            }
        });

        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Clicked on hapus  ", Toast.LENGTH_SHORT).show();

                //buat object alarm
                final ManfaatAdapter adapter = new ManfaatAdapter(mContext);
                AlertDialog.Builder aleBuilder = new AlertDialog.Builder(mContext);
                //settting judul dan pesan
                aleBuilder.setTitle("Hapus Data");
                aleBuilder
                        .setMessage("Apakah anda yakin ingin menghapus data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //panggil metod yang di butuhkan
                                //ini yang bakal kita deklarasikan sendiri jika ada yang panggil ini
                                adapter.hapusData(list.get(position).getIdWisata(), TABEL_WISATA, TABEL_ID_WISATA);
                                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                                mItemManger.closeAllItems();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //cancel
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = aleBuilder.create();
                alertDialog.show();
            }
        });

        //untuk membuka swipe hanya satu
        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Delete)
        ImageButton Delete;
        @BindView(R.id.Edit)
        ImageButton Edit;
        @BindView(R.id.bottom_wraper)
        LinearLayout bottomWraper;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.txt_judul)
        TextView txtJudul;
        @BindView(R.id.txt_lokasi)
        TextView txtLokasi;
        @BindView(R.id.txt_jarak)
        TextView txtJarak;
        @BindView(R.id.txt_waktu)
        TextView txtWaktu;
        @BindView(R.id.cv)
        CardView cv;
        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickEdit {
        void onEdit(WisataItem data);
    }

    public void editData(final String id, final String catatan, final Context context) {

    }
}
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.activity.add.AddManfaat;
import com.example.cyber_net.sig.activity.detail.DetailManfaat;
import com.example.cyber_net.sig.model.response.ResponseDelete;
import com.example.cyber_net.sig.model.response.item.ManfaatItem;
import com.example.cyber_net.sig.network.ApiService;
import com.example.cyber_net.sig.network.RetroClient;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.cyber_net.sig.helper.Contans.BASE_URL_IMAGE_MANFAAT;
import static com.example.cyber_net.sig.helper.Contans.KEY_DATA;
import static com.example.cyber_net.sig.helper.Contans.TABEL_ID_MANFAAT;
import static com.example.cyber_net.sig.helper.Contans.TABEL_MANFAAT;
import static com.example.cyber_net.sig.helper.ConvertDate.customTanggal;

public class ManfaatAdapter extends RecyclerSwipeAdapter<ManfaatAdapter.SimpleViewHolder> {
    private Context mContext;
    private ArrayList<ManfaatItem> list;
    private OnClickEdit listener;


    public ManfaatAdapter(Context context, ArrayList<ManfaatItem> objects, OnClickEdit listener) {
        this.mContext = context;
        this.list = objects;
        this.listener = listener;
    }

    public ManfaatAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ManfaatAdapter(Context context, ArrayList<ManfaatItem> objects) {
        this.mContext = context;
        this.list = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_artikel, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        //ini link untuk gambar
        String linkGambarMovie = list.get(position).getImage();
        Glide.with(mContext)
                .load(BASE_URL_IMAGE_MANFAAT + linkGambarMovie)
                .into(viewHolder.imgCover);
        viewHolder.txtJudul.setText(list.get(position).getJudul());

        /*//potong string biar tidak panjang
        if (list.get(position).getDeskripsi().length() > 50) {
            //https://stackoverflow.com/questions/8369708/limiting-the-number-of-characters-in-a-string-and-chopping-off-the-rest/8369746
            viewHolder.txtArtikel.setHtml(list.get(position).getDeskripsi().substring(0, 80)+"...");
        }*/

        viewHolder.txtArtikel.setHtml(list.get(position).getDeskripsi());
        viewHolder.txtTanggal.setText(customTanggal(list.get(position).getTanggal(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy"));
        viewHolder.txtJam.setText(customTanggal(list.get(position).getTanggal(), "yyyy-MM-dd HH:mm:ss", "HH:mm"));

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManfaatItem data = list.get(position);
                Intent intent = new Intent(mContext, DetailManfaat.class);
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
                ManfaatItem data = list.get(position);
                Intent intent = new Intent(mContext, AddManfaat.class);
                intent.putExtra(KEY_DATA, data);
                mContext.startActivity(intent);
            }
        });

        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Clicked on hapus  ", Toast.LENGTH_SHORT).show();

                //buat object alarm
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
                                hapusData(list.get(position).getIdManfaat(), TABEL_MANFAAT, TABEL_ID_MANFAAT);
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
        @BindView(R.id.img_cover)
        ImageView imgCover;
        @BindView(R.id.txt_judul)
        TextView txtJudul;
        @BindView(R.id.txt_tanggal)
        TextView txtTanggal;
        @BindView(R.id.txt_jam)
        TextView txtJam;
        @BindView(R.id.cv)
        CardView cv;
        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;
        @BindView(R.id.txt_artikel)
        HtmlTextView txtArtikel;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickEdit {
        void onEdit(ManfaatItem data);
    }

    public void hapusData(final String id, final String tabel, final String cari) {
        ApiService apiService = RetroClient.getApiService();
        Call<ResponseDelete> call = apiService.delete(tabel, cari, id);
        call.enqueue(new Callback<ResponseDelete>() {
            @Override
            public void onResponse(Call<ResponseDelete> call, Response<ResponseDelete> response) {
                try {
                    if (response.body().getKode() == 1) {
                        Toast.makeText(mContext, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDelete> call, Throwable t) {

            }
        });
    }
}
package com.salman.projectabsensiguru.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.salman.projectabsensiguru.MapsLocationActivity;
import com.salman.projectabsensiguru.R;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

public class ItemAbsensi extends AbstractItem<ItemAbsensi, ItemAbsensi.ViewHolder> {
    private String username;
    private String password;
    private String jam_login;
    private String jam_logout;
    private String tanggal;
    private double loc_latitude;
    private double loc_longitude;

    public ItemAbsensi(String username, String password, String jam_login, String jam_logout, String tanggal, double loc_latitude, double loc_longitude) {
        this.username = username;
        this.password = password;
        this.jam_login = jam_login;
        this.jam_logout = jam_logout;
        this.tanggal = tanggal;
        this.loc_latitude = loc_latitude;
        this.loc_longitude = loc_longitude;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getJam_login() {
        return jam_login;
    }

    public String getJam_logout() {
        return jam_logout;
    }

    public String getTanggal() {

        return tanggal;
    }

    public double getLoc_latitude() {
        return loc_latitude;
    }

    public double getLoc_longitude() {
        return loc_longitude;
    }

    @NonNull
    @Override
    public ItemAbsensi.ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.rv_dataabsen;
    }

    @Override
    public int getLayoutRes() {

        return R.layout.item_absen;
    }

    public class ViewHolder extends FastAdapter.ViewHolder<ItemAbsensi> {
        private TextView jam_login, jam_logout, tanggal, latitude, longitude;

        public ViewHolder(View itemView) {
            super(itemView);
            jam_login = itemView.findViewById(R.id.txt_jam_login);
            jam_logout = itemView.findViewById(R.id.txt_jam_logout);
            tanggal = itemView.findViewById(R.id.txt_tanggal);
            latitude = itemView.findViewById(R.id.txt_loclatitude);
            longitude = itemView.findViewById(R.id.txt_loclongitude);
        }

        @Override
        public void bindView(final ItemAbsensi item, List<Object> payloads) {
            jam_login.setText(item.jam_login);
            jam_logout.setText(item.jam_logout);
            tanggal.setText(item.tanggal);
            latitude.setText(String.valueOf(item.loc_latitude));
            longitude.setText(String.valueOf(item.loc_longitude));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    Intent mapintent = new Intent(context, MapsLocationActivity.class);
                    mapintent.putExtra("latitude", item.loc_latitude);
                    mapintent.putExtra("longitude", item.loc_longitude);
                    context.startActivity(mapintent);
                }
            });
        }

        @Override
        public void unbindView(ItemAbsensi item) {
            jam_login.setText(null);
            jam_logout.setText(null);
            tanggal.setText(null);
            latitude.setText(null);
            longitude.setText(null);
        }
    }
}

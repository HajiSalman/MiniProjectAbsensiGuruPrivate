package com.salman.projectabsensiguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.salman.projectabsensiguru.adapter.ItemAbsensi;
import com.salman.projectabsensiguru.adapter.ItemGuru;
import com.salman.projectabsensiguru.api.ApiClient;
import com.salman.projectabsensiguru.api.ApiInterface;
import com.salman.projectabsensiguru.helper.Session;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuruActivity extends AppCompatActivity {
    private ImageView profil;
    private TextView id_guru, nama, alamat, jenis_kelamin, no_telp, username, password;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);

        session = new Session(getApplicationContext());

        final RecyclerView absenView = findViewById(R.id.rv_dataabsen);
        final ItemAdapter itemAdapter = new ItemAdapter<>();
        final FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        final List absen = new ArrayList<>();

        profil = findViewById(R.id.foto_profil);
        id_guru = findViewById(R.id.id_guru);
        nama = findViewById(R.id.nama_guru);
        alamat = findViewById(R.id.alamat_guru);
        jenis_kelamin = findViewById(R.id.jenis_kelamin_guru);
        no_telp = findViewById(R.id.telp_guru);
        username = findViewById(R.id.username_guru);
        password = findViewById(R.id.password_guru);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ItemAbsensi>> call1 = apiInterface.getAbsenByUsername(session.getUsername());

        call1.enqueue(new Callback<List<ItemAbsensi>>() {
            @Override
            public void onResponse(Call<List<ItemAbsensi>> call, Response<List<ItemAbsensi>> response) {
                if (response.isSuccessful()) {
                    List<ItemAbsensi> absenItems = response.body();

                    for (ItemAbsensi item : absenItems) {
                        absen.add(new ItemAbsensi(item.getUsername(), item.getPassword(), item.getJam_login(),
                                item.getJam_logout(), item.getTanggal(), item.getLoc_latitude(), item.getLoc_longitude()));
                    }

                    absen.add(new ItemAbsensi(session.getUsername(), session.getPassword(), session.getLoginTime(),
                            session.getLogoutTime(), session.getDate(), session.getLocLatitude(), session.getLocLongitude()));

                    itemAdapter.add(absen);
                    absenView.setAdapter(fastAdapter);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    absenView.setLayoutManager(layoutManager);
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal menampilkan data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ItemAbsensi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickLogout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GuruActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Yakin Anda ingin logout?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                session.setLogoutTime(currentTime);

                String username = session.getUsername();
                String password = session.getPassword();
                String jam_login = session.getLoginTime();
                String jam_logout = session.getLogoutTime();
                String tanggal = session.getDate();
                double loc_latitude = session.getLocLatitude();
                double loc_longitude = session.getLocLongitude();

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponseBody> call = apiInterface.absenGuru(new ItemAbsensi(username, password, jam_login, jam_logout, tanggal, loc_latitude,loc_longitude));

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            session.logout();
                            Toast.makeText(getApplicationContext(), "Berhasil Logout!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Gagal Logout!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

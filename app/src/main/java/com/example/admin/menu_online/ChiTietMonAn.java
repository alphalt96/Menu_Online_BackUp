package com.example.admin.menu_online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.menu_online.Database.MenuOnlineDatabase;
import com.example.admin.menu_online.models.MonAn;

public class ChiTietMonAn extends AppCompatActivity {

    private MenuOnlineDatabase menuOnlineDatabase;

    private TextView txtRenTen, txtRenSoLuong, txtRenViTri;
    private ImageView imgMonAn;
    private Button  btnMenu;
    private Toolbar toolbar;
    private TextView txtLuotXem, txtLuotThich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);

        setControl();
        setEvent();
    }

    private void setEvent() {

    }

    private void setControl() {
        data();

        //setup toolbar
        setupToolbar();

        txtRenTen = (TextView) findViewById(R.id.txtRenTen);
        imgMonAn = (ImageView) findViewById(R.id.imgMonAn);
        txtRenSoLuong = (TextView) findViewById(R.id.txtRenSoLuong);
        txtRenViTri = (TextView) findViewById(R.id.txtRenViTri);
        txtLuotXem = (TextView) findViewById(R.id.txtLuotXem);
        txtLuotThich = (TextView) findViewById(R.id.txtLuotThich);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        MonAn monAn = (MonAn) bundle.getSerializable("detail");

        txtRenTen.setText(monAn.getTenMonAn());
        imgMonAn.setBackgroundResource(monAn.getImage());
        txtRenSoLuong.setText(String.valueOf(monAn.getSoLuong()));
        txtRenViTri.setText(monAn.getViTri());
        txtLuotXem.setText(String.valueOf(monAn.getLuotView()));
        txtLuotThich.setText(String.valueOf(monAn.getLuotThich()));
    }

    private void data() {
        menuOnlineDatabase = new MenuOnlineDatabase(this);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.toolBarReturnHome));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Món ăn");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon_png);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_menu, menu);
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.cart).setIcon(R.drawable.add_cart_icon_png);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) finish();
        else if(item.getItemId() == R.id.cart){
            SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
            if(sharedPreferences.getInt("USERID", 0) != 0) {
                MonAn monAn = (MonAn) getIntent().getBundleExtra("bundle").getSerializable("detail");
                if(menuOnlineDatabase.checkDatHang(monAn.getMaMonAn())) {
                    int maMonAn = monAn.getMaMonAn();
                    String tenMonAn = monAn.getTenMonAn();
                    int img = monAn.getImage();
                    String viTri = monAn.getViTri();
                    String loaiMonAn = monAn.getLoaiMonAn();
                    float giaTien = monAn.getGiaTien();
                    menuOnlineDatabase.insertDatHang(maMonAn, tenMonAn, img, 1, viTri, loaiMonAn, giaTien);
                    Toast.makeText(ChiTietMonAn.this, "Them mon an vao gio hang thanh cong", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(ChiTietMonAn.this, "Mon nay da co san trong gio hang", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(ChiTietMonAn.this, "Ban can dang nhap de thuc hien chuc nang nay", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

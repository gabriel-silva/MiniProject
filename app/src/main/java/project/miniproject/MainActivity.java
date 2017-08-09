package project.miniproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import project.miniproject.Base.ShowMessage;
import project.miniproject.Util.Marker;
import project.miniproject.Util.MapUtil;
import project.miniproject.Util.SharedPref;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private FrameLayout frameLayout;
    private Button button;
    private Marker marker;
    private ArrayList<CheckBox> checkBox;
    boolean comprasAtive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        frameLayout = (FrameLayout) findViewById(R.id.checkbox_dialog);
        marker = new Marker();

        //começando o frameLayout invisível
        if (!SharedPref.isVisible(this)) {
            frameLayout.setVisibility(View.INVISIBLE);
        }

        //verificação de estado das checkboxs
        if (!SharedPref.isEmptyState(this)) {
            SharedPref.checkedPoint(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapUtil.setMap(googleMap);
        centerMap(-8.08584967414886, -34.894552230834961, 13);
        marker.logicMarker(this);

    }

    public void filter(View view) {


        //guarda estado de exibição do FrameLayout (visível/invisível)
        if (!SharedPref.isVisible(this)) {
            frameLayout.setVisibility(View.VISIBLE);
            SharedPref.stateVisibleFrameLayout(this);
        } else {
            frameLayout.setVisibility(View.INVISIBLE);
            SharedPref.stateInvisibleFrameLayout(this);
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layout);
        checkBox = new ArrayList<CheckBox>();

        //guardando quantidade de checkboxs
        SharedPreferences.Editor sizeChecked = SharedPref.getPref(this).edit();

        int count = 0;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View v = gridLayout.getChildAt(count);

            if (v instanceof CheckBox) {
                checkBox.add((CheckBox) gridLayout.getChildAt(count));
                count++;
            }
        }
        sizeChecked.putInt("countCheckBox", count);
        sizeChecked.commit();


        //marcar todos os checkboxs de acordo com a persistência
        int[] checked = SharedPref.getChecked(this);
        for (int i = 0; i < checked.length; i++) {
            if (checked[i] == i + 1) {
                checkBox.get(i).setChecked(true);
            }
        }

        button = (Button) findViewById(R.id.filter_point);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //aguardando estado do checkboxs
                SharedPref.setStateCheckBox(getApplication(), checkBox);

                //se todos estiverem desmarcados, será chamada a função que preenche todos os checkboxs
                fillCheckbox();

                resetMap();
            }
        });

        //guardando o estado como checked
        SharedPref.state(this);

    }

    private void resetMap() {

        if (MapUtil.getMap() != null) {
            MapUtil.getMap().clear();
            onMapReady(MapUtil.getMap());
            frameLayout.setVisibility(View.INVISIBLE);
            SharedPref.stateInvisibleFrameLayout(this);
        }
        //ajustando o mapa, quando o checkbox de compras não tiver marcado
        if (!checkBox.get(4).isChecked() && comprasAtive) {
            centerMap(-8.0587303517894853, -34.872104823589325, 15);
        }
        //variavel de controle de ajuste de zoom
        comprasAtive = true;
    }

    public void fillCheckbox(){
        boolean allNotChecked = true;
        for (CheckBox c : checkBox) {
            allNotChecked = allNotChecked && !c.isChecked();
        }
        if (allNotChecked) {
            ShowMessage.message(getApplication());
            SharedPref.checkedPoint(getApplication());
            comprasAtive = false;
        }
    }

    private void centerMap(double latitude, double longitude, int zoom) {
        LatLng center = new LatLng(latitude, longitude);
        MapUtil.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(center, zoom));
    }
}

package com.c11.colectivosfinal.logica;
import static android.content.Context.LOCATION_SERVICE;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class OsmApi {
    private MapView mapView;
    private Context context;

    public OsmApi(MapView mapView, Context context) {
        this.mapView = mapView;
        this.context = context;
    }

    /* Despliega el mapa y permite ver la ubicacion del usuario al iniciar el mapa, sino aparece en las coord (0 , 0) */
    public void setUpMap (Context context){

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        IMapController mapController = mapView.getController();
        mapController.setZoom(19.0);
        mapView.setMultiTouchControls(true);

        Location lastKnownLocation = getLastKnownLocation(context);

        if(lastKnownLocation != null){
            GeoPoint startPoint = new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            mapController.setCenter(startPoint);

            MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), mapView);
            myLocationOverlay.enableMyLocation();
            mapView.getOverlays().add(myLocationOverlay);
        }else{
            Toast.makeText(context, "No se pudo encontrar la ubicacion", Toast.LENGTH_SHORT).show();
        }
    }

    private Location getLastKnownLocation(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        try {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation == null) {
                lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            return lastKnownLocation;
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }


}

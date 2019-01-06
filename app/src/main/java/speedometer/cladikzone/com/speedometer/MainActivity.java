package speedometer.cladikzone.com.speedometer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManage = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        locationManage.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                        this.onLocationChanged(null);
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                    }
                } else {
                    this.onLocationChanged(null);
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView view  = (TextView) this.findViewById(R.id.dashView);
        TextView actual = (TextView) this.findViewById(R.id.actualView);

        if(location == null) {
            view.setText("0.0 Km/h");
        } else {
            //location.setAccuracy(1);
            float speedInMs = location.getSpeed();

            double currentSpeed = ( speedInMs * 3.6 );
            String speed = currentSpeed + "Km/h".toString();
            view.setText(speed);
            actual.setText(speedInMs + "m/s");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

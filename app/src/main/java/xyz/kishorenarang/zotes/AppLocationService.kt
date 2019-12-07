package xyz.kishorenarang.zotes

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import java.io.IOException
import java.util.*


class AppLocationService(context: Context) : Service(),
    LocationListener {
    protected var locationManager: LocationManager?
    var location: Location? = null
    @SuppressLint("MissingPermission")
    fun getLocation(provider: String?): Location? {
        if (locationManager!!.isProviderEnabled(provider)) {
            locationManager!!.requestLocationUpdates(
                provider,
                MIN_TIME_FOR_UPDATE,
                MIN_DISTANCE_FOR_UPDATE.toFloat(),
                this
            )
            if (locationManager != null) {
                location = locationManager!!.getLastKnownLocation(provider)
                return location
            }
        }
        return null
    }

    override fun onLocationChanged(location: Location) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(
        provider: String,
        status: Int,
        extras: Bundle
    ) {
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    companion object {
        private const val MIN_DISTANCE_FOR_UPDATE: Long = 10
        private const val MIN_TIME_FOR_UPDATE = 1000 * 60 * 2.toLong()
    }

    init {
        locationManager = context
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}




object LocationAddress {
    private const val TAG = "LocationAddress"
    fun getAddressFromLocation(
        latitude: Double, longitude: Double,
        context: Context?, handler: Handler?
    ) {
        val thread: Thread = object : Thread() {
            override fun run() {
                val geocoder = Geocoder(context, Locale.getDefault())
                var result: String? = null
                try {
                    val addressList =
                        geocoder.getFromLocation(
                            latitude, longitude, 1
                        )
                    if (addressList != null && addressList.size > 0) {
                        val address = addressList[0]
                        val sb = StringBuilder()
                        for (i in 0 until address.maxAddressLineIndex) {
                            sb.append(address.getAddressLine(i)).append("\n")
                        }
                        sb.append(address.locality).append("\n")
                        sb.append(address.postalCode).append("\n")
                        sb.append(address.countryName)
                        result = sb.toString()
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Unable connect to Geocoder", e)
                } finally {
                    val message = Message.obtain()
                    message.target = handler
                    if (result != null) {
                        message.what = 1
                        val bundle = Bundle()
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n\nAddress:\n" + result
                        bundle.putString("address", result)
                        message.data = bundle
                    } else {
                        message.what = 1
                        val bundle = Bundle()
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n Unable to get address for this lat-long."
                        bundle.putString("address", result)
                        message.data = bundle
                    }
                    message.sendToTarget()
                }
            }
        }
        thread.start()
    }
}
package com.sina.presentation.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textview.MaterialTextView
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.presentation.R
import com.sina.presentation.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback {


    private val AC_REQ_CODE = 134
    private val TAG = "AdditAddressFragment"
    private var mMap: GoogleMap? = null
    private var geocoder: Geocoder? = null
    private var mMarker: Marker? = null
    private var mLocationName: String? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var currentAddress = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)
        geocoder = Geocoder(requireContext())
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

    }

    override fun setupViews() = with(binding) {
        btnConfirmAddress.setOnClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToEditAddressFragment(currentAddress))
        }
    }

    override fun observers() {
        TODO("Not yet implemented")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL

        checkPermission()
        moveMarkerToMiddleScreen()
        // Set info window adapter
        infoWindowMarker()
    }

    private fun infoWindowMarker() {
        mMap!!.setInfoWindowAdapter(object : InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null // Use default info window background
            }

            override fun getInfoContents(marker: Marker): View? {
                val view: View = layoutInflater.inflate(R.layout.info_window_layout, null)
                val tvLocationName = view.findViewById<MaterialTextView>(R.id.tv_location_name)
                tvLocationName.text = mLocationName
                return view
            }
        })
    }

    private fun moveMarkerToMiddleScreen() {
        mMap!!.setOnCameraIdleListener {
            val target =
                mMap!!.cameraPosition.target
            Log.e(TAG, "setOnCameraIdleListener: $target")
            try {
                val addresses =
                    geocoder!!.getFromLocation(target.latitude, target.longitude, 1)
                if (addresses!!.size > 0) {
                    val address = addresses[0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    mMap!!.clear()
                    mMarker =
                        mMap!!.addMarker(MarkerOptions().position(latLng).title(address.locality))
                    mLocationName = address.getAddressLine(0)
                    val stringAddress = buildString {
                        append(address.getAddressLine(0))
                    }
                    currentAddress = stringAddress
                    binding.tvCurrentAddress.text = stringAddress
                    Log.e(TAG, "onMapReady: " + address.getAddressLine(0).toString())
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) enableUserLocation() else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), AC_REQ_CODE
            ) else ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), AC_REQ_CODE
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun zoomToUserLocation() {
        val lastLocation = fusedLocationProviderClient!!.lastLocation
        lastLocation.addOnSuccessListener { location: Location ->
            val latLng =
                LatLng(location.latitude, location.latitude)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))
            mMap!!.addMarker(MarkerOptions().position(latLng))
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableUserLocation() {
        mMap!!.isMyLocationEnabled = true
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location ->
            if (location != null) {
                val userLocation =
                    LatLng(
                        location.latitude,
                        location.longitude
                    )
                mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AC_REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation()
                zoomToUserLocation()
            } else {
                //show dialog permisson not granted
            }
        }
    }
}
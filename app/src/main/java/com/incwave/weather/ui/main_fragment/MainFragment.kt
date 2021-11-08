package com.incwave.weather.ui.main_fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.incwave.weather.core.base.BaseFragment
import com.incwave.weather.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import androidx.navigation.findNavController
import com.incwave.weather.R
import com.incwave.weather.ui.main_fragment.MainViewModel.Event.*


@AndroidEntryPoint
class MainFragment() : BaseFragment<MainViewModel>(), LocationListener {

    private lateinit var binding: FragmentMainBinding
    override val viewModel by viewModels<MainViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>
    private var locationManager: LocationManager? = null

    override fun onResume() {
        super.onResume()
        checkGpsIsOn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it.isNotEmpty() &&
                    it[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                    it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                ) {
                    viewModel.gpsPermissionIsGrunted(true)
                    binding.tvPermission.visibility = View.GONE
                } else {
                    binding.tvPermission.visibility = View.VISIBLE
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGetWeather.setOnClickListener {
            viewModel.btnGetWeatherClicked()
        }
        binding.btnShowDetails.setOnClickListener {
            view.findNavController().navigate(R.id.details_fragment)
        }
        viewModel.eventsFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            when(it){
                CheckGPSIsOn -> {checkGpsIsOn()}
                GetLocation -> {getLocation()}
                OpenGPSSettings -> {openGpsSettings()}
                CheckPermission -> {checkPermission()}
                RequestPermission -> {requestPermission()}
                is ShowNoInternetConnection -> { if (it.isInternet){
                    binding.tvNoInternetConnection.visibility = View.GONE
                } else {
                    binding.tvNoInternetConnection.visibility = View.VISIBLE
                }
                }
                is SetTimeAndWeatherOnUi -> {
                    binding.btnShowDetails.visibility = View.VISIBLE
                    binding.tvTime.text = it.time
                    binding.tvWeather.text = it.weather
                }
            }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun checkGpsIsOn(){
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        viewModel.gpsIsEnabled(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }

    private fun openGpsSettings(){
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    private fun checkPermission(){
        viewModel.gpsPermissionIsGrunted(
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        )
    }

    private fun requestPermission() {
        activityResultLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        val criteria = Criteria()
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val bestProvider = java.lang.String.valueOf(locationManager!!.getBestProvider(criteria, true)).toString()
        val lastKnownLoc = locationManager!!.getLastKnownLocation(bestProvider)
        if (lastKnownLoc != null){
            viewModel.latitudeLongitudeIsReceived("${lastKnownLoc.latitude},${lastKnownLoc.longitude}")
        } else {
            locationManager!!.requestLocationUpdates(bestProvider, 1000, 0f,this)
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager?.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        viewModel.latitudeLongitudeIsReceived("${location.latitude},${location.longitude}")
    }
}
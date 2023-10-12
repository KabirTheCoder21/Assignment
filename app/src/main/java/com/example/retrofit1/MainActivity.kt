package com.example.retrofit1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),OnMapReadyCallback{
    // https://api.github.com/users
    //https://jsonplaceholder.typicode.com/users
    var BASE_URL = "https://jsonplaceholder.typicode.com"
    private lateinit var epoxyController: EpoxyController

    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView
    //private lateinit var epoxyRecyclerView: EpoxyRecyclerView
    private lateinit var layoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)

        layoutManager = LinearLayoutManager(this, HORIZONTAL,false)
        recyclerView.layoutManager =  layoutManager

        epoxyController = EpoxyController()
        recyclerView.adapter = epoxyController.adapter

          //  epoxyRecyclerView=  findViewById(R.id.epoxyRecyclerView)
        //epoxyRecyclerView.setController(epoxyController)
        getData()
        // Initialize the MapView
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    private fun getData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val retroData = retrofit.getData()
        retroData.enqueue(object :  Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                val data = response.body()
              //  Log.d("data",data.toString())
                epoxyController.setData(data)
               // epoxyController.requestModelBuild()
                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        if (dx > 0) {
                            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                            if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                                Log.d("onChecking", "onScrolled: ${data?.get(firstVisibleItemPosition)}")
                            }
                            val location = LatLng(data?.get(firstVisibleItemPosition)?.address?.geo?.lat!!.toDouble(),
                                data.get(firstVisibleItemPosition).address.geo.lng.toDouble())
                            val zoomLevel = 19.0f

                            googleMap.addMarker(MarkerOptions().position(location).title(data.get(firstVisibleItemPosition).address.city))
                            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, zoomLevel)
                            googleMap.animateCamera(cameraUpdate)
                        }
                        if(dx<0)
                        {
                            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                            if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                                Log.d("onChecking", "onScrolled: ${data?.get(firstVisibleItemPosition)}")
                            }
                            val location = LatLng(data?.get(firstVisibleItemPosition)?.address?.geo?.lat!!.toDouble(),
                                data.get(firstVisibleItemPosition).address.geo.lng.toDouble())
                            val zoomLevel = 15.0f

                            googleMap.addMarker(MarkerOptions().position(location).title(data.get(firstVisibleItemPosition).address.city))
                            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, zoomLevel)
                            googleMap.animateCamera(cameraUpdate)
                        }
                    }
                })
            }
            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
            }

        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val location = LatLng(-37.3159, 81.1496)
        val zoomLevel = 15.0f

        googleMap.addMarker(MarkerOptions().position(location).title("Gwenborough"))
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, zoomLevel)
        googleMap.animateCamera(cameraUpdate)
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}
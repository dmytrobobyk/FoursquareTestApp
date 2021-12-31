package com.example.testapp.ui.main.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testapp.BuildConfig
import com.example.testapp.common.BaseFragment
import com.example.testapp.common.util.getDistanceBetweenTwoPoints
import com.example.testapp.common.viewmodel.base.ViewModelFactory
import com.example.testapp.databinding.FragmentMainBinding
import com.example.testapp.models.Result
import com.example.testapp.ui.MainActivity
import com.example.testapp.ui.details.PlaceMapActivityDetails
import com.example.testapp.ui.details.PlaceMapActivityDetails.Companion.PLACE_DETAILS_INFO_KEY
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import com.example.testapp.ui.main.PlacesViewModel
import com.example.testapp.ui.main.adapter.PlacesAdapter
import com.example.testapp.ui.main.di.DaggerPlacesFragmentComponent
import com.example.testapp.ui.main.di.PlacesFragmentModule
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class PlacesFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: PlacesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<PlacesViewModel>
    private lateinit var viewModel: PlacesViewModel
    private lateinit var receivePlacesInterface: ReceivePlacesInterface

    companion object {
        fun newInstance() = PlacesFragment()
    }

    interface ReceivePlacesInterface {
        fun onPlacesReceived(places: List<Result>)
    }

    private val component by lazy {
        DaggerPlacesFragmentComponent.builder()
            .mainComponent((activity as MainActivity).component)
            .placesFragmentModule(PlacesFragmentModule())
            .build()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        receivePlacesInterface = (activity as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[PlacesViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (!::adapter.isInitialized) {
            adapter = PlacesAdapter {
                run {
                    val centerOfSeattle = LatLng(BuildConfig.SEATTLE_LATTITUDE.toDouble(), BuildConfig.SEATTLE_LONGITUDE.toDouble())
                    val selectedPoint = LatLng(it.geocodes.main.latitude, it.geocodes.main.longitude)
                    val distanceTitle = "To Seattle center is ${getDistanceBetweenTwoPoints(selectedPoint, centerOfSeattle)} km"
                    val category = if(it.categories.isEmpty()) {
                        "No category"
                    } else {
                        it.categories[0].name
                    }
                    val placeDetailsInfo = PlaceDetailsInfo(
                        it.name,
                        distanceTitle,
                        category,
                        it.fsq_id,
                        it.geocodes.main.latitude,
                        it.geocodes.main.longitude,
                        it.location.address,
                        it.location.country,
                        it.location.postcode,
                        it.location.region
                    )
                    val intent = Intent(requireActivity(), PlaceMapActivityDetails::class.java)
                    intent.putExtra(PLACE_DETAILS_INFO_KEY, placeDetailsInfo)
                    startActivity(intent)
                }
            }
        }

        binding.placesRecyclerView.adapter = adapter

        viewModel.placesList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            receivePlacesInterface.onPlacesReceived(it)
        })
    }

}
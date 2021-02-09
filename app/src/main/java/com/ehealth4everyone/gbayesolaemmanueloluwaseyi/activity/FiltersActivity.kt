package com.ehealth4everyone.gbayesolaemmanueloluwaseyi.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.R
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.activity.FiltersActivity.UIStateViewModel.UIState.DATA_FOUND
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.activity.FiltersActivity.UIStateViewModel.UIState.DEFAULT
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.activity.FiltersActivity.UIStateViewModel.UIState.FAILED
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.activity.FiltersActivity.UIStateViewModel.UIState.LOADING
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.activity.FiltersActivity.UIStateViewModel.UIState.NO_DATA
import com.ehealth4everyone.gbayesolaemmanueloluwaseyi.adapter.FiltersRecyclerAdapter
import com.ehealth4everyone.restapi.GbayesolaEmmanuelCloud
import com.ehealth4everyone.restapi.models.FilterLists
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.filter_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FiltersActivity : AppCompatActivity() {
    var filtersAdapter: FiltersRecyclerAdapter? = null

    lateinit var filterLists: List<FilterLists>

    var uiStateViewModel: UIStateViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_main)

        uiStateViewModel = ViewModelProviders.of(this).get(UIStateViewModel::class.java)
        setUpViewModels()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        filter_list?.layoutManager = layoutManager
        filter_list?.setHasFixedSize(true)

        swipe_to_refresh?.post {
            swipe_to_refresh?.isRefreshing = true
            loadFilterList()
        }

        swipe_to_refresh?.setOnRefreshListener {
            swipe_to_refresh?.isRefreshing = (true)
            loadFilterList()
            uiStateViewModel?.setUIState(DEFAULT)
        }
    }

    /*** This function method controls the state of the UI ***/
    private fun setUpViewModels() {
        uiStateViewModel?.stateUI?.observe(this, { state ->
            when (state) {
                DEFAULT -> {
                    content_area?.visibility = View.VISIBLE
                    net_error?.visibility = (View.GONE)
                    emptyElement?.visibility = (View.GONE)
                }
                LOADING -> {
                    //determines the UI state when the app is loading data
                    shimmer_view_container?.visibility = View.VISIBLE
                    content_area?.visibility = View.GONE
                    shimmer_view_container!!.startShimmer()
                }
                DATA_FOUND -> {
                    //determines the UI state when the app has found data
                    swipe_to_refresh?.isRefreshing = false
                    shimmer_view_container?.stopShimmer()
                    shimmer_view_container?.visibility = View.GONE
                    content_area?.visibility = View.VISIBLE
                    net_error?.visibility = View.GONE
                    emptyElement?.visibility = View.GONE
                }
                NO_DATA -> {
                    //determines the UI state when there is no data to load.
                    content_area?.visibility = View.GONE
                    emptyElement?.visibility = View.VISIBLE
                }
                FAILED -> {
                    //determines the UI state when the app failed to get data.
                    shimmer_view_container!!.stopShimmer()
                    shimmer_view_container?.visibility = View.GONE
                    val snackBar = Snackbar.make(
                        main_container!!,
                        "Network Connection Error",
                        Snackbar.LENGTH_SHORT
                    )
                    snackBar.show()
                    swipe_to_refresh?.isRefreshing = false
                    content_area?.visibility = View.GONE
                    net_error?.visibility = View.VISIBLE
                }

            }
        })
    }


    class UIStateViewModel : ViewModel() {
        private val uiState = MutableLiveData<Int>()

        val stateUI: LiveData<Int> = uiState

        internal object UIState {
            const val DEFAULT = 0
            const val LOADING = 1
            const val DATA_FOUND = 2
            const val NO_DATA = 3
            const val FAILED = 4
        }

        fun setUIState(state: Int) {
            uiState.postValue(state)
            Log.d(TAG, "UI State: '$state' Set.")
        }

        companion object {
            private const val TAG = "UIStateVM"
        }

        init {
            uiState.postValue(DEFAULT)
        }

    }


    private fun loadFilterList() {
        uiStateViewModel?.setUIState(LOADING)

        //make request to fetch users:
        val appId = "b4cdeed3-327b-4591-9b06-aaf043e65497"

        GbayesolaEmmanuelCloud.getInstance(applicationContext)?.getFilterLists(appId, object :
            Callback<List<FilterLists?>?> {
            override fun onResponse(
                call: Call<List<FilterLists?>?>,
                response: Response<List<FilterLists?>?>
            ) {
                if (response.code() == 200) {
                    filterLists = response.body() as List<FilterLists>

                    filtersAdapter = FiltersRecyclerAdapter(this@FiltersActivity, filterLists)

                    filter_list?.adapter = filtersAdapter

                    uiStateViewModel?.setUIState(DATA_FOUND)

                    //No filter was found:
                    if (filtersAdapter!!.itemCount < 1) {
                        uiStateViewModel?.setUIState(NO_DATA)
                    }


                }
            }

            override fun onFailure(call: Call<List<FilterLists?>?>, t: Throwable) {
                t.printStackTrace()
                uiStateViewModel?.setUIState(FAILED)
            }
        })
    }
}
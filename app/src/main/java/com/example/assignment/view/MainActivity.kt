package com.example.assignment.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.assignment.R
import com.example.assignment.adaptor.CoinRankAdapter
import com.example.assignment.adaptor.Top3RankAdapter
import com.example.assignment.adaptor.viewholder.CoinViewHolder
import com.example.assignment.fragment.BottomSheetFragment
import com.example.assignment.interfaces.LoadMoreScrollListener
import com.example.assignment.model.Coin
import com.example.assignment.model.CoinResponse
import com.example.assignment.model.ResponseBodyModel
import com.example.assignment.viewmodel.CoinViewModel

class MainActivity : AppCompatActivity(), Top3RankAdapter.OnItemClickListener, CoinRankAdapter.OnItemClickListener, CoinViewHolder.CoinListener {

    private lateinit var top3RankAdapter: Top3RankAdapter
    private lateinit var coinRankAdapter: CoinRankAdapter
    private lateinit var top3List: ArrayList<Coin>
    private lateinit var rvTop3: RecyclerView
    private lateinit var rvCoins: RecyclerView
    private lateinit var top3Layout: View
    private lateinit var coinLayout: View
    private lateinit var notFoundLayout: View
    private lateinit var searchEditText: EditText
    private lateinit var clearBtn: ImageView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var searchText: String = ""
    private var offset: Int = 3
    private var list: ArrayList<Coin> = ArrayList()

    private val coinViewModel: CoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initElement()
        initViewModel()
    }

    private fun initElement() {
        rvTop3 = findViewById(R.id.rvTop3)
        rvCoins = findViewById(R.id.rvCoins)
        clearBtn = findViewById(R.id.clearBtn)
        searchEditText = findViewById(R.id.searchEditText)
        top3Layout = findViewById(R.id.top3Layout)
        notFoundLayout = findViewById(R.id.notFoundLayout)
        coinLayout = findViewById(R.id.coinLayout)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        progressBar = findViewById(R.id.progressBar)
        nestedScrollView = findViewById(R.id.nestedScrollView)

        setAdaptor()
        initRecycler()

        // Action
        searchAction()
        setupRefresh()
        clearBtn.setOnClickListener {
            searchEditText.text = null
            showFragment("top3")

            // Get coins
            coinViewModel.getTop3Coins()
            coinViewModel.getCoins(offset)
        }

        // Get coins
        coinViewModel.getTop3Coins()
        coinViewModel.getCoins(offset)
    }

    private fun initRecycler() {
        rvCoins.isNestedScrollingEnabled = false
        rvCoins.layoutManager = linearLayoutManager
        nestedScrollView.setOnScrollChangeListener(object : LoadMoreScrollListener(linearLayoutManager) {
            override fun onLoadMore() {
                offset += list.size
                coinViewModel.getCoins(offset)
            }
        })
    }

    private fun hideFragment(type: String) {
        when (type) {
            "coin" -> coinLayout.visibility = View.GONE
            "notFound" -> notFoundLayout.visibility = View.GONE
            else -> top3Layout.visibility = View.GONE
        }
    }

    private fun setupRefresh() {
        swipeRefresh.setOnRefreshListener {
            showFragment("coin")
            if (searchText.isEmpty() || searchText.isBlank()) {
                showFragment("top3")
                coinViewModel.getCoins(offset)
            } else {
                coinViewModel.search(searchText, offset)
            }
        }
    }

    private fun hideRefresh() {
        swipeRefresh.isRefreshing = false
    }

    private fun showFragment(type: String) {
        when (type) {
            "coin" -> {
                coinLayout.visibility = View.VISIBLE
            }
            "notFound" -> notFoundLayout.visibility = View.VISIBLE
            else -> top3Layout.visibility = View.VISIBLE
        }
    }

    private fun searchAction() {
        searchEditText.addTextChangedListener {
            searchText = it.toString()

            if (searchText.isBlank() || searchText.isEmpty()) {
                showFragment("top3")

                // Get coins
                coinRankAdapter.resetItems()
                coinViewModel.getCoins(offset)
            } else {
                hideFragment("top3")

                // Search coin
                coinViewModel.search(searchText, offset)
            }
        }
    }

    private fun initViewModel() {
        coinViewModel.top3CoinLiveData.observe(this, {
            getTop3Coins(it)
        })
        coinViewModel.coinLiveData.observe(this, {
            getCoins(it)
        })
        coinViewModel.searchCoinLiveData.observe(this, {
            search(it)
        })
        coinViewModel.coinDetailLiveData.observe(this, {
            getCoinDetail(it)
        })
    }

    private fun getTop3Coins(it: ResponseBodyModel<CoinResponse>) {
        hideRefresh()
        if (it.status == "success") {
            top3List = ArrayList()

            it.data!!.coins?.let { item ->
                top3List.addAll(item)

                setTop3List(top3List)

                showFragment("top3")
            }
        } else {
            Log.d("ERROR", it.message!!)
            Toast.makeText(baseContext, it.message!!, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCoins(it: ResponseBodyModel<CoinResponse>) {
        hideRefresh()
        if (it.status == "success") {
            list = ArrayList()
            it.data!!.coins?.let { item ->
                list.addAll(item)

                setCoinList(list)

                showFragment("coin")
            }
        } else {
            Log.d("ERROR", it.message!!)
            Toast.makeText(baseContext, it.message!!, Toast.LENGTH_SHORT).show()
        }
    }

    private fun search(it: ResponseBodyModel<CoinResponse>) {
        hideRefresh()
        if (it.status == "success") {
            if (it.data!!.coins!!.isEmpty()) {
                showFragment("notFound")
                hideFragment("coin")
                hideFragment("top3")
            } else {
                list = ArrayList()
                it.data!!.coins?.let { it1 ->
                    list.addAll(it1)
                    setSearchCoinList(list)

                    showFragment("coin")
                }
            }
        } else {
            Log.d("ERROR", it.message!!)
            Toast.makeText(baseContext, it.message!!, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCoinDetail(it: ResponseBodyModel<Coin>) {
        hideRefresh()
        if (it.status == "success") {
            it.data!!.let { it1 ->
                val bottomSheetDialog = BottomSheetFragment(it1)
                bottomSheetDialog.show(supportFragmentManager, "Bottom Sheet Dialog Fragment")
            }
        } else {
            Log.d("ERROR", it.message!!)
            Toast.makeText(baseContext, it.message!!, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdaptor() {
        // Top 3
        top3RankAdapter = Top3RankAdapter(ArrayList(), this, baseContext)
        rvTop3.adapter = top3RankAdapter
        rvTop3.layoutManager = object: GridLayoutManager(baseContext, resources.getInteger(R.integer.number_of_grid_items)) {
            override fun canScrollHorizontally(): Boolean { return false }
            override fun canScrollVertically(): Boolean { return false }
        }

        // Coins
        linearLayoutManager = object: LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean { return false }
        }

        coinRankAdapter = CoinRankAdapter(ArrayList(), this, this, baseContext)
        rvCoins.adapter = coinRankAdapter
        rvCoins.layoutManager = linearLayoutManager
    }

    private fun setTop3List(list: ArrayList<Coin>) {
        top3RankAdapter.list = list
    }

    private fun setCoinList(list: ArrayList<Coin>) {
        coinRankAdapter.addItems(list)
    }

    private fun setSearchCoinList(list: ArrayList<Coin>) {
        coinRankAdapter.resetItems()
        coinRankAdapter.addItems(list)
    }

    override fun onClick(item: Coin) {
        coinViewModel.getDetail(item.uuid!!)
    }

    override fun share(url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)

        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(shareIntent, "Share link using"))
    }
}
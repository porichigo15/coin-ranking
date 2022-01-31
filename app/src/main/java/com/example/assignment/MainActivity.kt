package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.assignment.adaptor.CoinRankAdapter
import com.example.assignment.adaptor.Top3RankAdapter
import com.example.assignment.adaptor.viewholder.CoinViewHolder
import com.example.assignment.fragment.BottomSheetFragment
import com.example.assignment.interfaces.LoadMoreScrollListener
import com.example.assignment.model.Coin
import com.example.assignment.model.CoinResponse
import com.example.assignment.model.ResponseBodyModel
import com.example.assignment.viewmodel.CoinViewModel

class MainActivity : AppCompatActivity(), Top3RankAdapter.OnItemClickListener, CoinViewHolder.CoinListener, CoinRankAdapter.OnItemClickListener {

    private lateinit var top3RankAdapter: Top3RankAdapter
    private lateinit var coinRankAdapter: CoinRankAdapter
    private lateinit var top3Layout: LinearLayout
    private lateinit var coinLayout: LinearLayout
    private lateinit var notFoundLayout: LinearLayout
    private lateinit var rvTop3: RecyclerView
    private lateinit var rvCoins: RecyclerView
    private lateinit var top3LayoutManager: GridLayoutManager
    private lateinit var coinLayoutManager: LinearLayoutManager
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var searchEditText: EditText
    private lateinit var clearBtn: ImageView

    private var coinList: ArrayList<Coin> = ArrayList()
    private var top3List: ArrayList<Coin> = ArrayList()
    private var offset: Int = 3
    private var searchText: String = ""

    private val coinViewModel: CoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initElement()
        initAdapter()
        initViewModel()
        initSwipeRefresh()
    }

    private fun initElement() {
        rvTop3 = findViewById(R.id.rvTop3)
        rvCoins = findViewById(R.id.rvCoins)
        top3Layout = findViewById(R.id.top3Layout)
        coinLayout = findViewById(R.id.coinLayout)
        notFoundLayout = findViewById(R.id.notFoundLayout)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        nestedScrollView = findViewById(R.id.nestedScrollView)
        searchEditText = findViewById(R.id.searchEditText)
        clearBtn = findViewById(R.id.clearBtn)

        // Action
        searchAction()
        clearAction()

        // Get coins
        coinViewModel.getCoins(offset)
        coinViewModel.getTop3Coins()
    }

    private fun searchAction() {
        searchEditText.addTextChangedListener {
            searchText = it.toString()

            if (searchText.isBlank() || searchText.isEmpty()) {
                showLayout("top3")

                offset = 3
                // Get coins
                coinRankAdapter.resetItems()
                coinViewModel.getCoins(offset)
            } else {
                hideLayout("top3")

                // Search coin
                coinViewModel.search(searchText, offset)
            }
        }
    }

    private fun clearAction() {
        clearBtn.setOnClickListener {
            searchEditText.setText("")
            offset = 3
            top3RankAdapter.resetItems()
            coinRankAdapter.resetItems()
            coinViewModel.getTop3Coins()
            coinViewModel.getCoins(offset)
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
            searchCoin(it)
        })
        coinViewModel.coinDetailLiveData.observe(this, {
            getCoinDetail(it)
        })
    }

    private fun showLayout(type: String) {
        when (type) {
            "top3" -> top3Layout.visibility = View.VISIBLE
            "coins" -> coinLayout.visibility = View.VISIBLE
            "notFound" -> notFoundLayout.visibility = View.VISIBLE
        }
    }

    private fun hideLayout(type: String) {
        when (type) {
            "top3" -> top3Layout.visibility = View.GONE
            "coins" -> coinLayout.visibility = View.GONE
            "notFound" -> notFoundLayout.visibility = View.GONE
        }
    }

    private fun hideRefresh() {
        swipeRefresh.isRefreshing = false
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            offset = 3
            top3RankAdapter.resetItems()
            coinRankAdapter.resetItems()
            coinViewModel.getTop3Coins()
            coinViewModel.getCoins(offset)
        }
    }

    private fun getTop3Coins(it: ResponseBodyModel<CoinResponse>) {
        hideRefresh()
        if (it.status == "success") {
            top3List = ArrayList()
            hideLayout("notFound")
            showLayout("top3")

            it.data!!.coins?.let { item ->
                top3List.addAll(item)

                setTop3List(top3List)
            }
        } else {
            Log.d("ERROR", it.message!!)
            Toast.makeText(baseContext, it.message!!, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCoins(it: ResponseBodyModel<CoinResponse>) {
        hideRefresh()
        if (it.status == "success") {
            hideLayout("notFound")
            showLayout("coins")

            it.data!!.coins?.let { item ->
                coinList.addAll(item)

                setCoinList(coinList)
            }
        } else {
            Log.d("ERROR", it.message!!)
            Toast.makeText(baseContext, it.message!!, Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchCoin(it: ResponseBodyModel<CoinResponse>) {
        hideRefresh()
        if (it.status == "success") {
            if (it.data!!.coins!!.isEmpty()) {
                showLayout("notFound")
                hideLayout("top3")
                hideLayout("coins")
            } else {
                hideLayout("notFound")
                hideLayout("top3")
                showLayout("coins")

                it.data!!.coins?.let { item ->
                    coinList.addAll(item)
                    setCoinList(coinList)
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
            it.data!!.let { item ->
                val bottomSheetDialog = BottomSheetFragment(item)
                bottomSheetDialog.show(supportFragmentManager, "Bottom Sheet Dialog Fragment")
            }
        } else {
            Log.d("ERROR", it.message!!)
            Toast.makeText(baseContext, it.message!!, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setTop3List(list: ArrayList<Coin>) {
        top3RankAdapter.addItems(list)
    }

    private fun setCoinList(list: ArrayList<Coin>) {
        coinRankAdapter.addItems(list)
    }

    private fun initAdapter() {
        // Top 3
        top3LayoutManager = object: GridLayoutManager(baseContext, resources.getInteger(R.integer.number_of_grid_items)) {
            override fun canScrollHorizontally(): Boolean { return false }
            override fun canScrollVertically(): Boolean { return false }
        }
        top3RankAdapter = Top3RankAdapter(ArrayList(), this, baseContext)
        rvTop3.adapter = top3RankAdapter
        rvTop3.layoutManager = top3LayoutManager

        // Coins
        coinLayoutManager = object: LinearLayoutManager(baseContext) {
            override fun canScrollVertically(): Boolean { return false }
        }
        coinRankAdapter = CoinRankAdapter(ArrayList(), this, this, baseContext)
        rvCoins.adapter = coinRankAdapter
        rvCoins.layoutManager = coinLayoutManager

        nestedScrollView.setOnScrollChangeListener(object: LoadMoreScrollListener(coinLayoutManager) {
            override fun onLoadMore() {
                offset += coinList.size
                coinViewModel.getCoins(offset)
            }
        })
    }

    override fun onClick(item: Coin) {
        coinViewModel.getDetail(item.uuid!!)
    }

    override fun share(url: String) {

    }
}
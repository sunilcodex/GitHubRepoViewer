package com.noname.app.ui.Main.gitHub

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.noname.app.R
import com.noname.app.data.MainRepository
import com.noname.app.data.MainViewModelFactory
import com.noname.app.data.Model.RepositoryDto
import com.noname.app.data.Resource
import com.noname.app.data.api.ApiClient
import com.noname.app.data.remote.RemoteData
import com.noname.app.databinding.ActivityGitHubRepoBinding
import com.noname.app.ui.Library.StatusView.CustomStatusViewLayout
import com.noname.app.ui.Library.StatusView.StatusView
import com.noname.app.ui.Library.StatusView.ViewClass
import com.noname.app.ui.Library.utils.Network
import com.noname.app.ui.Main.viewmodel.MainViewModel
import com.noname.app.ui.Main.presentation.RepositoryAdapter
import com.task.data.local.LocalData
import kotlinx.coroutines.Dispatchers

class GitHubRepoActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityGitHubRepoBinding
    private val binding get() = _binding

    private lateinit var factory: MainViewModelFactory<MainRepository>
    private val MainviewModel: MainViewModel by viewModels { factory }

    private lateinit var repositoryAdapter: RepositoryAdapter
    private var currentPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityGitHubRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding!!.multipleStatusView.InitializeStatus(
            StatusView(
                ViewClass.NOT_FOUND,
                CustomStatusViewLayout(this@GitHubRepoActivity, R.raw.page_not_found)

            )
        )
        binding!!.multipleStatusView.InitializeStatus(
            StatusView(
                ViewClass.STATUS_EMPTY,
                CustomStatusViewLayout(
                    this@GitHubRepoActivity,
                    R.raw.not_found_product,
                    "Please try again with another keyboard or maybe use generic term"
                )
            )
        )
        binding!!.multipleStatusView.InitializeStatus(
            StatusView(
                ViewClass.STATUS_NO_NETWORK,
                CustomStatusViewLayout(
                    this@GitHubRepoActivity,
                    R.raw.animation_network,
                    "\uD83D\uDEAB No Internet Connection\\nPlease check your network and try again."
                )
            )
        )


        binding.swiperefresh.setProgressViewOffset(false, 0, 200)
        binding.swiperefresh.setColorSchemeResources(R.color.black)
        binding.swiperefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.swiperefresh.setRefreshing(false)
            loadRepositories(currentPage)
        })


        setupViewModel()
        setupRecyclerView()
        setupObservers()

        // Load initial page
        loadRepositories(currentPage)
    }

    private fun setupViewModel() {
        factory = MainViewModelFactory(
            this,
            "Parse data from factory.",
            MainRepository(
                RemoteData(ApiClient.getRetrofit(), Network(this)),
                LocalData(this),
                Dispatchers.IO
            )
        )
    }

    private fun setupRecyclerView() {
        repositoryAdapter =
            RepositoryAdapter(mutableListOf(), ::onRepositoryClick, ::loadMoreRepositories)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = repositoryAdapter

        setupScrollListener()
    }

    private fun setupObservers() {

        MainviewModel.isConnectedNet.observe(this) { isConnected ->

            val hasData = repositoryAdapter.itemCount != 0

            when {
                !isConnected && hasData -> {
                    // Network nahi hai, lekin data pehle se loaded hai
                    _binding!!.multipleStatusView.showViewByViewClass(
                        ViewClass.REMOVE_VIEW,
                        this@GitHubRepoActivity
                    )
                }

                !isConnected && !hasData -> {
                    // Na net hai, na data
                    _binding!!.multipleStatusView.showViewByViewClass(
                        ViewClass.STATUS_NO_NETWORK,
                        this@GitHubRepoActivity
                    )
                }

                isConnected && !hasData -> {
                    // Net hai, lekin data abhi nahi aaya
                    _binding!!.multipleStatusView.showViewByViewClass(
                        ViewClass.STATUS_NO_NETWORK,
                        this@GitHubRepoActivity
                    )
                }

                isConnected && hasData -> {
                    // Net bhi hai, data bhi hai
                    _binding!!.multipleStatusView.showViewByViewClass(
                        ViewClass.REMOVE_VIEW,
                        this@GitHubRepoActivity
                    )
                }
            }
        }

        MainviewModel.rootRepositoryModelData.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    isLoading = true
                    repositoryAdapter.setLoading(true)
                }

                is Resource.Success -> {
                    isLoading = false
                    repositoryAdapter.setLoading(false)
                    resource.data?.let { repositoryAdapter.addRepositories(it) }
                    currentPage++
                }

                is Resource.DataError -> {
                    isLoading = false
                    repositoryAdapter.setLoading(false)
                    val errorMessage = getErrorMessage(resource.errorCode)
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


    private fun loadMoreRepositories() {
        if (!isLoading) {
            MainviewModel.getUserRepositories("google", currentPage, 20)
        }
    }

    private fun loadRepositories(page: Int) {
        if (!isLoading) {
            MainviewModel.getUserRepositories("google", page, 20)
        }
    }

    private fun onRepositoryClick(repository: RepositoryDto) {

    }

    private fun setupScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    loadRepositories(currentPage)
                }
            }
        })
    }

    fun getErrorMessage(errorCode: Int?): String {
        return when (errorCode) {
            -101 -> "No internet connection. Please check your network."
            -102 -> "Unable to connect to server. Try again later."
            -103 -> "Connection timed out. Please retry."
            -100 -> "Something went wrong. Please try again."
            100 -> "Continue"
            101 -> "Switching Protocols"
            200 -> "OK"
            201 -> "Created"
            202 -> "Accepted"
            204 -> "No Content"
            301 -> "Moved Permanently"
            302 -> "Found (Previously Moved Temporarily)"
            304 -> "Not Modified"
            400 -> "Bad Request – Please check the request."
            401 -> "Unauthorized – Please log in again."
            403 -> "Forbidden – You don't have permission to access this."
            404 -> "Not Found – The requested data could not be found."
            405 -> "Method Not Allowed – Invalid request method."
            408 -> "Request Timeout – Server took too long to respond."
            409 -> "Conflict – Duplicate request or data conflict."
            429 -> "Too Many Requests – Please slow down."
            500 -> "Internal Server Error – Something went wrong on the server."
            501 -> "Not Implemented – Server doesn't support the functionality."
            502 -> "Bad Gateway – Invalid response from upstream server."
            503 -> "Service Unavailable – Server is down or busy. Try again later."
            504 -> "Gateway Timeout – Server didn’t respond in time."
            null -> "No internet connection or unknown error."
            else -> "Unexpected error occurred. (Code: $errorCode)"
        }
    }

}

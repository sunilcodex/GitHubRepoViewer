    package com.noname.app.ui.Main.viewmodel
    
    import android.content.Context
    import androidx.lifecycle.*
    import com.noname.app.data.MainRepository
    import com.noname.app.data.Model.RepositoryDto
    import com.noname.app.data.Resource
    import com.noname.app.ui.Library.Network.Connectivity
    //import com.ameyaapanel.app.ui.Main.fragment.mainFragment.BookingView.Model.RootBookingsModel
    import kotlinx.coroutines.launch
    
    
    class MainViewModel(var context: Context, val data: String, var Repository: MainRepository) :
        ViewModel() {

        val rootRepositoryModelDataPrivate = MutableLiveData<Resource<List<RepositoryDto>>>()
        val rootRepositoryModelData: LiveData<Resource<List<RepositoryDto>>> get() = rootRepositoryModelDataPrivate

        fun getUserRepositories(username: String, page: Int, perPage: Int) {
            if (Connectivity.isConnected(context)) {
                viewModelScope.launch {
                    rootRepositoryModelDataPrivate.value = Resource.Loading()
                    Repository.getUserRepositories(username, page, perPage).collect {
                        rootRepositoryModelDataPrivate.value = it
                    }
                }
                netConnected(true)
            } else {
                netConnected(false)
            }
        }



        //    =======================================================================================


        val isConnectedNet = MutableLiveData<Boolean>().apply { value = false }
        val isConnectedNetLive: LiveData<Boolean> get() = isConnectedNet


        fun netConnected(isConnected: Boolean) {
            isConnectedNet.value = isConnected
        }
    }
    

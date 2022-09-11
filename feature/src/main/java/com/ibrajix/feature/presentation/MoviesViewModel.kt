package com.ibrajix.feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrajix.feature.repository.MovieRepository
import com.ibrajix.feature.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesViewModel @Inject constructor(
   private val repository: MovieRepository
) : ViewModel()
{

    var scrollToTopAfterRefresh = false

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    sealed class Event {
        data class ShowErrorMessage(val error: Throwable) : Event()
    }

    private val _swipeRefresh = Channel<Refresh>()
    private val swipeRefresh = _swipeRefresh.receiveAsFlow()

    enum class Refresh {
        FORCE, NORMAL
    }

    val getMovies = swipeRefresh.flatMapLatest { refresh->
        repository.getPopularNews(
            refresh == Refresh.FORCE,
            onFetchSuccess = {
                scrollToTopAfterRefresh = true
            },
            onFetchFailed = { t->
                viewModelScope.launch {
                    _event.send(Event.ShowErrorMessage(t))
                }
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun onStart(){
        if (getMovies.value !is Resource.Loading){
            viewModelScope.launch {
                _swipeRefresh.send(Refresh.NORMAL)
            }
        }
    }

    fun onManualRefresh(){
        if (getMovies.value !is Resource.Loading){
            viewModelScope.launch {
                _swipeRefresh.send(Refresh.FORCE)
            }
        }
    }

}
package com.example.movieapp.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.*
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel(val app: Application,val moviesRepository: MoviesRepository) : AndroidViewModel(app) {
    val popularMovies : MutableLiveData<Resource<NewResponse>> = MutableLiveData()
    val topRated : MutableLiveData<Resource<NewResponse>> = MutableLiveData()
    val actors: MutableLiveData<Resource<CastResponse>> = MutableLiveData()
    val reviews: MutableLiveData<Resource<ReviewsResponse>> = MutableLiveData()
    val similarMovie: MutableLiveData<Resource<SimilarResponse>> = MutableLiveData()
    val seriesTv : MutableLiveData<Resource<SeriesResponse>> = MutableLiveData()
    val video : MutableLiveData<Resource<VideoResponse>> = MutableLiveData()
    val searchMovie : MutableLiveData<Resource<NewResponse>> = MutableLiveData()
    val localMovie : MutableLiveData<List<com.example.movieapp.models.Result>> = MutableLiveData()
    var id : String? = null
    init {
        getSeriesTv()
        getPopularMovies()
        getTopRatedMovies()

    }
    fun getPopularMovies() = viewModelScope.launch(Dispatchers.IO){
        popularMovies.postValue(Resource.Loading())
        val response = moviesRepository.getPopularMovies(1) // tra ra response tu api
        popularMovies.postValue(handleMoviesResponse(response))
    }

    fun searchMovie(query: String) = viewModelScope.launch(Dispatchers.IO) {
        popularMovies.postValue(Resource.Loading())
        val response = moviesRepository.getSearching(1,query)
        popularMovies.postValue(handleMoviesResponse(response))
    }

    fun getTopRatedMovies() = viewModelScope.launch(Dispatchers.IO){
        topRated.postValue(Resource.Loading())
        val response = moviesRepository.getTopRated(1)
        topRated.postValue(handleMoviesResponse(response))
    }

    fun getSeriesTv()= viewModelScope.launch(Dispatchers.IO) {
        try {
            seriesTv.postValue(Resource.Loading())
            val response = moviesRepository.getSeriesMovies(1)
            seriesTv.postValue(handleSeriesTvResponse(response))
        }
        catch (e:Exception){
            e.printStackTrace()
        }

    }

    fun getActors(id: String)  {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                actors.postValue(Resource.Loading())
                val response = moviesRepository.getActors(id)
                actors.postValue(handleActorsResponse(response))
            } catch (e: Exception){
                e.printStackTrace()
            }

        }
    }

    fun getReviews(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                reviews.postValue(Resource.Loading())
                val response = moviesRepository.getReviews(id)
                reviews.postValue(handleReviewsResponse(response))
            }
            catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSimilar(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                similarMovie.postValue(Resource.Loading())
                val response = moviesRepository.getSimilarMovie(id)
                similarMovie.postValue(handleSimilarMovie(response))
            }
            catch(e: Exception){
                e.printStackTrace()
            }

        }
    }

    fun getVideo(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                video.postValue(Resource.Loading())
                val response = moviesRepository.getVideo(id)
                video.postValue(handleVideoResponse(response))
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
     fun insertMovieToLocal(result: com.example.movieapp.models.Result) = viewModelScope.launch(Dispatchers.IO){
        moviesRepository.insertMovie(result)
    }

    fun deleteMovieLocal(result: com.example.movieapp.models.Result) =  viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.deleteMovie(result)
    }
    fun getLocalMovie() = moviesRepository.getLocalMovie()


    //Ham xu ly cac response tu api
    private fun handleSeriesTvResponse(response: Response<SeriesResponse>) : Resource<SeriesResponse> {
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleMoviesResponse(resposne : Response<NewResponse>) : Resource<NewResponse>{
        if(resposne.isSuccessful){
            resposne.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(resposne.message())
    }

    private fun handleActorsResponse(resposne : Response<CastResponse>) : Resource<CastResponse>{
        if(resposne.isSuccessful){
            resposne.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(resposne.message())
    }

    private fun handleReviewsResponse(response : Response<ReviewsResponse>) : Resource<ReviewsResponse> {
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSimilarMovie(response: Response<SimilarResponse>) : Resource<SimilarResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleVideoResponse(response: Response<VideoResponse>) : Resource<VideoResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    fun setMovieId(id: String, key: String?){
       when(key){
           "ACTOR"->{
                getActors(id)
           }
           "REVIEW"->{
               getReviews(id)
           }
           "SIMILAR"->{
               getSimilar(id)
           }
           "VIDEO"->{
               getVideo(id)
           }


       }

    }


}
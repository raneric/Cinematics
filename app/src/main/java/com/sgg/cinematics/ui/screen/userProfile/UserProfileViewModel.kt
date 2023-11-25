package com.sgg.cinematics.ui.screen.userProfile

import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.ui.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val repository: MovieRepository) :
        MainViewModel(repository) {
}
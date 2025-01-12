package com.nugu.nuguollim.ui.my_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.nugu.nuguollim.common.data.model.template.Template
import com.nugu.nuguollim.common.data.model.terms.Terms
import com.nugu.paging.template.FavoriteTemplatePagingSource
import com.nugu.paging.template.MyWritingTemplatePagingSource
import com.nuguollim.data.state.ResultState
import com.nuguollim.data.usecase.auth.ClearAuthInfoUseCase
import com.nuguollim.data.usecase.auth.GetMyUserDataUseCase
import com.nuguollim.data.usecase.auth.GetMyUserDataUseCase.Companion.run
import com.nuguollim.data.usecase.auth.SetNickNameUseCase
import com.nuguollim.data.usecase.auth.UnRegisterUseCase
import com.nuguollim.data.usecase.template.AddFavoriteUseCase
import com.nuguollim.data.usecase.template.GetFavoriteTemplatesUseCase
import com.nuguollim.data.usecase.template.GetMyWritingTemplatesUseCase
import com.nuguollim.data.usecase.template.GetTemplateUseCase
import com.nuguollim.data.usecase.template.RemoveFavoriteUseCase
import com.nuguollim.data.usecase.template.RemoveTemplateUseCase
import com.nuguollim.data.usecase.terms.GetTermsUseCase
import com.nuguollim.data.util.mutableResultStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    getMyUserDataUseCase: GetMyUserDataUseCase,
    private val unregisterUseCase: UnRegisterUseCase,
    private val clearAuthInfoUseCase: ClearAuthInfoUseCase,
    private val setNickNameUseCase: SetNickNameUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val getTermsUseCase: GetTermsUseCase,
    private val removeTemplateUseCase: RemoveTemplateUseCase,
    private val getTemplateUseCase: GetTemplateUseCase,
    private val getMyWritingTemplatesUseCase: GetMyWritingTemplatesUseCase,
    private val myWritingTemplatePagingSource: MyWritingTemplatePagingSource.Factory,
    private val getFavoriteTemplatesUseCase: GetFavoriteTemplatesUseCase,
    private val favoriteTemplatePagingSource: FavoriteTemplatePagingSource.Factory,
) : ViewModel() {

    val myUserData = getMyUserDataUseCase.run().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ResultState.Loading
    )

    val myWritingTemplates = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { myWritingTemplatePagingSource.create(getMyWritingTemplatesUseCase) }
    ).flow

    val favoriteTemplates = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { favoriteTemplatePagingSource.create(getFavoriteTemplatesUseCase) }
    ).flow

    private val _termsState = mutableResultStateFlow<List<Terms>>()
    val termsState = _termsState.asStateFlow()

    fun setNickName(
        nickname: String,
        fail: (Throwable) -> Unit = {},
        success: () -> Unit = {},
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> fail.invoke(throwable) }) {
            setNickNameUseCase.invoke(nickname)
            success.invoke()
        }
    }

    fun clearAuthInfo(
        fail: (Throwable) -> Unit = {},
        success: () -> Unit = {},
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> fail.invoke(throwable) }) {
            clearAuthInfoUseCase.invoke()
            success.invoke()
        }
    }

    fun unregister(
        fail: (Throwable) -> Unit,
        success: () -> Unit,
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> fail.invoke(throwable) }) {
            unregisterUseCase.invoke()
            clearAuthInfo {
                success.invoke()
            }
        }
    }

    fun addFavorite(id: Long) {
        viewModelScope.launch { addFavoriteUseCase.run(id) }
    }

    fun removeFavorite(id: Long) {
        viewModelScope.launch { removeFavoriteUseCase.run(id) }
    }

    fun getTerms(termsTitle: String) {
        getTermsUseCase.run(termsTitle)
            .onEach { _termsState.value = it }
            .launchIn(viewModelScope)
    }

    fun removeTemplate(
        id: Long,
        success: () -> Unit,
        fail: (Throwable) -> Unit
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> fail.invoke(throwable) }) {
            removeTemplateUseCase.run(id)
            success.invoke()
        }
    }

    fun getTemplate(
        id: Long,
        success: (Template) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> fail.invoke(throwable) }) {
            success.invoke(getTemplateUseCase.run(id))
        }
    }

}
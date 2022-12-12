package com.rootstrap.android.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.rootstrap.android.BuildConfig
import com.rootstrap.android.ui.dashboard.DashboardViewModel
import com.rootstrap.android.ui.login.LoginViewModel
import com.rootstrap.android.ui.product_detail.ProductDetailViewModel
import com.rootstrap.android.ui.products_list.ProductListViewModel
import com.rootstrap.android.ui.shopping_cart.ShoppingCartViewModel
import com.rootstrap.android.util.dispatcher.AppDispatcherProvider
import com.rootstrap.android.util.dispatcher.DispatcherProvider
import com.rootstrap.data.api.ApiProvider
import com.rootstrap.data.api.ApiServiceFactory
import com.rootstrap.data.api.interceptors.AuthenticationInterceptor
import com.rootstrap.data.api.interceptors.ConnectivityInterceptor
import com.rootstrap.data.api.interceptors.HeadersInterceptor
import com.rootstrap.data.api.interceptors.ResponseInterceptor
import com.rootstrap.data.managers.session.SessionManager
import com.rootstrap.data.managers.session.SessionManagerImpl
import com.rootstrap.data.repository.ProductRepository
import com.rootstrap.data.repository.UserCartRepository
import com.rootstrap.data.repository.UserRepository
import com.rootstrap.data.util.Prefs
import com.rootstrap.usecases.GetProducts
import com.rootstrap.usecases.GetUserCurrentCart
import com.rootstrap.usecases.SignIn
import com.rootstrap.usecases.SignOut
import com.rootstrap.usecases.SignUp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

@KoinExperimentalAPI
fun Application.initDI() {
    startKoin {
        androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        androidContext(this@initDI)
        fragmentFactory()
        modules(appModule, dataModule, scopesModule)
    }
}

val appModule = module {
    single<CoroutineDispatcher> { Dispatchers.Main }
    single { SignUp(get()) }
    single { SignIn(get()) }
    single { SignOut(get()) }
    single { GetProducts(get()) }
    single { GetUserCurrentCart(get()) }
    viewModel { ProductListViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { ShoppingCartViewModel(get()) }
    viewModel { ProductDetailViewModel() }
    viewModel { LoginViewModel() }
}

val dataModule = module {
    single { ApiServiceFactory(get(), get(), get()) }
    single { HeadersInterceptor() }
    single { ConnectivityInterceptor() }
    single { AuthenticationInterceptor(get()) }
    single { ResponseInterceptor(get(), get()) }
    single { ApiProvider(get()) }
    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            "prefs",
            Context.MODE_PRIVATE
        )
    }
    single<DispatcherProvider> { AppDispatcherProvider() }
    single { Prefs(get()) }
    single { UserRepository(get()) }
    single { ProductRepository() }
    single { UserCartRepository() }
    single<SessionManager> { SessionManagerImpl(get()) }
}

private val scopesModule = module {}

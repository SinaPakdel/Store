package com.sina.data.di

import com.sina.core.common.anotations.IODispatcher
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.local.db.OrderDao
import com.sina.data.local.CartLocalDataSource
import com.sina.data.local.ProductInfoLocalDataSource
import com.sina.data.local.impl.CartLocalDataSourceImpl
import com.sina.data.local.impl.ProductInfoLocalDataSourceImpl
import com.sina.data.remote.CartRemoteDataSource
import com.sina.data.remote.CategoryRemoteDataSource
import com.sina.data.remote.LoginRemoteDataSource
import com.sina.data.remote.ProductInfoRemoteDataSource
import com.sina.data.remote.ProductsRemoteDataSource
import com.sina.data.remote.ProfileRemoteDataSource
import com.sina.data.remote.impl.ProfileRemoteDataSourceImpl
import com.sina.data.remote.ReviewRemoteDataSource
import com.sina.data.remote.SearchProductsRemoteDataSource
import com.sina.data.remote.impl.CartRemoteDataSourceImpl
import com.sina.data.remote.impl.CategoryRemoteDataSourceImpl
import com.sina.data.remote.impl.LoginRemoteDataSourceImpl
import com.sina.data.remote.impl.ProductInfoRemoteDataSourceImpl
import com.sina.data.remote.impl.ProductListRemoteDataSourceImpl
import com.sina.data.remote.impl.ReviewRemoteDataSourceImpl
import com.sina.data.remote.impl.SearchProductsRemoteDataSourceImpl
import com.sina.data.repository.CartRepositoryImpl
import com.sina.data.repository.CategoryRepositoryImpl
import com.sina.data.repository.LoginRepositoryImpl
import com.sina.data.repository.ProductInfoRepositoryImpl
import com.sina.data.repository.ProductListRepositoryImpl
import com.sina.data.repository.ProfileRepositoryImpl
import com.sina.data.repository.ReviewRepositoryImpl
import com.sina.data.repository.SearchProductsRepositoryImpl
import com.sina.domain.repositroy.CartRepository
import com.sina.domain.repositroy.CategoryRepository
import com.sina.domain.repositroy.LoginRepository
import com.sina.domain.repositroy.ProductInfoRepository
import com.sina.domain.repositroy.ProductsRepository
import com.sina.domain.repositroy.ProfileRepository
import com.sina.domain.repositroy.ReviewRepository
import com.sina.domain.repositroy.SearchProductsRepository
import com.sina.core.network.services.StoreServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    /**
     * Cart module
     */
    @Provides
    fun provideCartLocalDataSource(dao: OrderDao): CartLocalDataSource =
        CartLocalDataSourceImpl(dao)

    @Provides
    fun provideCartRemoteDataSource(storeServices: StoreServices): CartRemoteDataSource =
        CartRemoteDataSourceImpl(storeServices)

    @Provides
    fun provideCartRepository(
        cartLocalDataSource: CartLocalDataSource,
        cartRemoteDataSource: CartRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): CartRepository = CartRepositoryImpl(
        cartLocalDataSource, cartRemoteDataSource, ioDispatcher
    )

    /**
     * Category
     */

    @Provides
    @Singleton
    fun provideCategoryDataSource(storeServices: StoreServices): CategoryRemoteDataSource =
        CategoryRemoteDataSourceImpl(storeServices)

    @Provides
    @Singleton
    fun provideCategoryRepository(
        productDataSource: CategoryRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): CategoryRepository = CategoryRepositoryImpl(productDataSource, ioDispatcher)

    /**
     * login
     */
    @Singleton
    @Provides
    fun provideRemoteCustomerDataSource(storeServices: StoreServices): LoginRemoteDataSource =
        LoginRemoteDataSourceImpl(storeServices)

//    @Provides
//    @Singleton
//    fun provideLocalCustomerDataSource(customerDao: CustomerDao): LoginLocalDataSource =
//        LoginLocalDataSourceImpl(customerDao)

    @Provides
    @Singleton
    fun provideCustomerRepository(
        loginRemoteDataSource: LoginRemoteDataSource,
//        loginLocalDataSource: LoginLocalDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        appDataStore: AppDataStore
    ): LoginRepository = LoginRepositoryImpl(loginRemoteDataSource, appDataStore, ioDispatcher)

    /**
     * ProductInfo
     */

    @Provides
    fun provideProductInfoRemoteDataSource(storeServices: StoreServices): ProductInfoRemoteDataSource =
        ProductInfoRemoteDataSourceImpl(storeServices)

    @Provides
    @Singleton
    fun provideProductInfoLocalDataSource(orderDao: OrderDao): ProductInfoLocalDataSource =
        ProductInfoLocalDataSourceImpl(orderDao)

    @Provides
    fun provideProductInfoRepository(
        productInfoRemoteDataSource: ProductInfoRemoteDataSource,
        productInfoLocalDataSource: ProductInfoLocalDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ProductInfoRepository = ProductInfoRepositoryImpl(
        productInfoRemoteDataSource,
        productInfoLocalDataSource,
        ioDispatcher
    )

    /**
     * ProductList
     */

    @Provides
    fun provideProductsDataSource(storeServices: StoreServices): ProductsRemoteDataSource =
        ProductListRemoteDataSourceImpl(storeServices)

    @Provides
    fun provideProductsRepository(
        productsRemoteDataSource: ProductsRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ProductsRepository = ProductListRepositoryImpl(productsRemoteDataSource, ioDispatcher)

    /**
     *  Review
     */

    @Provides
    fun provideReviewRemoteDataSource(storeServices: StoreServices): ReviewRemoteDataSource =
        ReviewRemoteDataSourceImpl(storeServices)

    @Provides
    fun provideItemRepository(
        reviewRemoteDataSource: ReviewRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ReviewRepository = ReviewRepositoryImpl(reviewRemoteDataSource, ioDispatcher)

    /**
     * Search
     */

    @Provides
    fun provideSearchProductsRemoteDataSource(storeServices: StoreServices): SearchProductsRemoteDataSource =
        SearchProductsRemoteDataSourceImpl(storeServices)

    @Provides
    fun provideSearchProductsRepository(
        searchProductsRemoteDataSource: SearchProductsRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): SearchProductsRepository =
        SearchProductsRepositoryImpl(searchProductsRemoteDataSource, ioDispatcher)

    /**
     * Profile
     */
    @Provides
    fun provideProfileRemoteDataSource(storeServices: StoreServices): ProfileRemoteDataSource =
        ProfileRemoteDataSourceImpl(storeServices)

    @Provides

    fun provideProfileRepository(
        profileRemoteDataSource: ProfileRemoteDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ProfileRepository = ProfileRepositoryImpl(profileRemoteDataSource, ioDispatcher)
}


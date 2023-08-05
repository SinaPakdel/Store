package com.sina.core.network.services

import com.sina.core.model.customer.UpdateCustomer
import com.sina.core.network.model.categorydto.CategoryDTOItem
import com.sina.core.network.model.coupondto.CouponDto
import com.sina.core.network.model.coupondto.UpdateCouponDto
import com.sina.core.network.model.customerdto.CustomerDto
import com.sina.core.network.model.orderdto.OrderDto
import com.sina.core.network.model.productinfodto.ProductInfoDto
import com.sina.core.network.model.product_list.ProductLstDTOItem
import com.sina.core.network.model.reviewdto.DeleteReviewDto
import com.sina.core.network.model.reviewdto.ReviewDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface StoreServices {

    @POST(Route.CUSTOMERS)
    suspend fun createCustomer(
        @Body customerDto: CustomerDto,
    ): Response<CustomerDto>

    @GET(Route.CUSTOMERS)
    suspend fun retrieveCustomer(
        @Query("search") customerEmail: String
    ): Response<List<CustomerDto>>

    @POST(Route.ORDERS)
    suspend fun createNewCustomerOrder(
        @Body orderDto: OrderDto
    ): Response<OrderDto>

    @GET(Route.ORDERS)
    suspend fun retrieveCustomerOrder(
        @Query("search") search: String,
        @Query("per_page") perPage: Int = 1,
    ): Response<List<OrderDto>>

    @GET(Route.PRODUCTS)
    suspend fun getProductsList(
        @Query(PARAMS.PAGE) page: Int = 1,
        @QueryMap filters: Map<String, String>
    ): Response<List<ProductLstDTOItem>>
    
    @GET(Route.PRODUCTS)
    suspend fun getListOfOrderCart(
        @Query("include") include: String
    ): Response<List<ProductLstDTOItem>>

    @GET(Route.ORDERS_ID)
    suspend fun getCurrentOrder(
        @Path("id") orderId: Int
    ): Response<OrderDto>

    @GET(Route.PRODUCTS_CATEGORIES)
    suspend fun getCategories(
    ): Response<List<CategoryDTOItem>>

    @GET(Route.PRODUCT_DETAILS)
    suspend fun getProductInfo(
        @Path("id") id: Int,
    ): Response<ProductInfoDto>

    @POST(Route.ORDERS)
    suspend fun createOrder(
        @Body orderDto: OrderDto
    ): Response<OrderDto>

    @PUT(Route.ORDERS_ID)
    suspend fun updateOrder(
        @Path("id") id: Int,
        @Body orderDto: OrderDto
    ): Response<OrderDto>

    @GET(Route.PRODUCTS)
    suspend fun getProductsBySearch(
        @Query(PARAMS.PAGE) page: Int,
        @Query("search") query: String,
        @QueryMap filters: Map<String, String>
    ): Response<List<ProductLstDTOItem>>

    @GET(Route.PRODUCTS)
    suspend fun getNewProduct(
        @Query("after") after: String
    ): Response<List<ProductLstDTOItem>>

    @GET(Route.REVIEWS)
    suspend fun getReviewsProduct(
        @Query("product") productId: Int
    ): Response<List<ReviewDto>>

    @POST(Route.REVIEWS)
    suspend fun createReview(
        @Body createReview: ReviewDto,
    ): Response<ReviewDto>

    @DELETE(Route.REVIEWS_ID)
    suspend fun deleteReview(
        @Path("id") reviewId: Int,
    ): Response<DeleteReviewDto>

    @PUT(Route.REVIEWS_ID)
    suspend fun updateReview(
        @Path("id") reviewId: Int,
        @Body createReview: ReviewDto,
    ): Response<ReviewDto>

    @GET(Route.REVIEWS)
    suspend fun getReviewProductsByCustomer(
        @Query("reviewer_email") customerEmail: String
    ): Response<List<ReviewDto>>

    @GET(Route.COUPONS)
    suspend fun validateCoupon(
        @Query("code") code: String,
    ): Response<List<CouponDto>>

    @PUT(Route.ORDERS_ID)
    suspend fun submitCoupon(
        @Path("id") orderId: Int,
        @Body updateCoupon: UpdateCouponDto,
    ): Response<OrderDto>

    @GET(Route.REVIEWS_ID)
    suspend fun getReview(
        @Path("id") reviewId: Int
    ): Response<ReviewDto>

    @PUT(Route.CUSTOMERS_ID)
    suspend fun updateCustomer(
        @Path("id") customerId: Int,
        @Body customer: UpdateCustomer
    ): Response<CustomerDto>

    private object Route {
        private const val PREFIX = "/wp-json/wc/v3"
        const val PRODUCT_DETAILS = "$PREFIX/products/{id}"
        const val PRODUCTS = "$PREFIX/products"
        const val PRODUCTS_CATEGORIES = "$PREFIX/products/categories"

        /* customer */
        const val CUSTOMERS = "$PREFIX/customers"
        const val CUSTOMERS_ID = "$PREFIX/customers/{id}"

        /* Orders */
        const val ORDERS = "$PREFIX/orders"
        const val ORDERS_ID = "$PREFIX/orders/{id}"

        /* Reviews */
        const val REVIEWS = "$PREFIX/products/reviews"
        const val REVIEWS_ID = "$PREFIX/products/reviews/{id}"

        /* Coupons */
        const val COUPONS = "$PREFIX/coupons"
    }

    private object PARAMS {
        const val PRODUCT_ID = "id"
        const val PAGE = "page"
        const val PER_PAGE = "per_page"

    }
}
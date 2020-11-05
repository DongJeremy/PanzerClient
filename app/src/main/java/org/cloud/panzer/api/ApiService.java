package org.cloud.panzer.api;

import org.cloud.panzer.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    String BASE_URL="https://www.wpccw.com/mobile/";

    @GET("index.php?w=index")
    Observable<String> getHomeInfoData();

    @GET("index.php?act=index&op=getgg&ac_id=1")
    Observable<String> getArticleListData();

    @GET("index.php?act=goods&op=goods_body")
    Observable<String> getGoodsImagesData(@Query("goods_id") String id);

    @GET("index.php?act=goods&op=goods_detail")
    Observable<String> getGoodsDetailData(@Query("goods_id") String id);

    @GET("index.php?act=member_cart&op=cart_list")
    Observable<String> getCartListData(@Query("key") String key);

    @FormUrlEncoded
    @POST("index.php?act=member_cart&op=cart_edit_quantity")
    Observable<String> cartEditQuantity(@Field("key") String key, @Field("cart_id") String cartId, @Field("quantity") String quantity);

    @FormUrlEncoded
    @POST("index.php?act=member_cart&op=cart_del")
    Observable<String> cartDelete(@Field("key") String key, @Field("cart_id") String cartId);

    @FormUrlEncoded
    @POST("index.php?act=member_cart&op=cart_add")
    Observable<String> cartAdd(@Field("key") String key, @Field("goods_id") String goodsId, @Field("quantity") String quantity);

    @POST("index.php")
    Observable<String> login(@Body LoginBean loginBean);
}

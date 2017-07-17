package com.salton123.chinavoice.model.api;

import com.salton123.chinavoice.model.bean.MusicListBean;
import com.salton123.chinavoice.model.domain.AreaBean;
import com.salton123.chinavoice.model.domain.MVDetailBean;
import com.salton123.chinavoice.model.domain.MVListBean;
import com.salton123.chinavoice.model.domain.VideoBean;
import com.salton123.config.RetrofitManager;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/11 22:45
 * Time: 22:45
 * Description:
 */
public class ChinaVoiceApi {


    public static final String WANGYI_HOST = "http://www.wawa.fm:9090/wawa/api.php/magazine/";

    public interface WyApiService {

        @GET("mgmagazinelist/r/10/page/{id}/sign=2230926e0bb334c908c9f7fabdaf42014e1afb31c17cd4d53b52fcd3bc34d501&api_key=08b1e567157582019f7fe639c841c42a&timestrap=1488600156")
        Observable<List<MusicListBean>> getMusicList(@Path("id") String id);
    }

    public static WyApiService GetWyApiService() {
        return RetrofitManager.getRetrofit(WANGYI_HOST).create(WyApiService.class);
    }


    public static final String HOT_MUSIC = "http://mapi.yinyuetai.com/";

    public interface MusicApiService {

        /**
         * 获取区域分类
         *
         * @param info
         * @return
         */
        @GET("video/get_mv_areas.json")
        Observable<List<AreaBean>> getAreaList(@Query("deviceinfo") String info);

        /**
         * 获取视频列表
         *
         * @param info
         * @param area
         * @param offset
         * @param size
         * @return
         */
        @GET("video/list.json")
        Observable<MVListBean> getMVListUrl(@Query("deviceinfo") String info, @Query("area") String area, @Query("offset") int offset, @Query("size") int size);

        /**
         * 获取主页
         *
         * @param info
         * @param offset
         * @param size
         * @return
         */
        @GET("suggestions/front_page.json")
        Observable<List<VideoBean>> getMainPageUrl(@Query("deviceinfo") String info, @Query("offset") int offset, @Query("size") int size);

        /**
         * 获取悦单主页
         *
         * @param info
         * @param offset
         * @param size
         * @return
         */
        @GET("playlist/list.json")
        Observable<MVListBean> getMainPageYueDanUrl(@Query("deviceinfo") String info, @Query("offset") int offset, @Query("size") int size);

        /**
         * 获取音乐节目列表
         *
         * @param info
         * @param offset
         * @param size
         * @param artistIds
         * @return
         */
        @GET("playlist/show.json")
        Observable<MVListBean> getYinYueProgramList(@Query("deviceinfo") String info, @Query("offset") int offset, @Query("size") int size, @Query("artistIds") int artistIds);

        /**
         * 获取V榜地址
         *
         * @param info
         * @return
         */
        @GET("vchart/get_vchart_areas.json")
        Observable<MVListBean> getVChartAreasUrl(@Query("deviceinfo") String info);

        /**
         * 获取V榜的周期
         *
         * @param info
         * @param area
         * @return
         */
        @GET("vchart/period.json")
        Observable<MVListBean> getVChartPeriodUrl(@Query("deviceinfo") String info, @Query("area") String area);

        /**
         * 获取V榜列表
         *
         * @param info
         * @param area
         * @param dateCode
         * @return
         */
        @GET("vchart/period.json")
        Observable<MVListBean> getVChartListUrl(@Query("deviceinfo") String info, @Query("area") String area, @Query("dateCode") int dateCode);

        /**
         * 获取相关MV
         *
         * @param info
         * @param id
         * @return
         */
        @GET("video/show.json")
        Observable<MVDetailBean> getRelativeVideoListUrl(@Query("deviceinfo") String info, @Query("id") int id);

        /**
         * 通过id 获取某人的悦单
         *
         * @param info
         * @param id
         * @return
         */
        @GET("playlist/show.json")
        Observable<MVListBean> getPeopleYueDanList(@Query("deviceinfo") String info, @Query("id") int id);
    }

    public static final String deviceinfo = "{\"aid\":\"10201036\",\"os\":\"Android\","
            + "\"ov\":" + "\"" + android.os.Build.VERSION.RELEASE + "\"" + ","
            + "\"rn\":\"720*1280\","
            + "\"dn\":" + "\"" + android.os.Build.MODEL + "\"" + ","
            + "\"cr\":\"46000\","
            + "\"as\":"
            + "\"WIFI\","
            + "\"uid\":"
            + "\"dbcaa6c4482bc05ecb0bf39dabf207d2\","
            + "\"clid\":110025000}";

    public static MusicApiService GetMusicApiService() {
        return RetrofitManager.getRetrofit(HOT_MUSIC).create(MusicApiService.class);
    }

}

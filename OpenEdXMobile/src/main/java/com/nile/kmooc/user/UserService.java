package com.nile.kmooc.user;

import com.google.inject.Inject;
import com.nile.kmooc.http.ApiConstants;

import com.nile.kmooc.model.Page;
import com.nile.kmooc.profiles.BadgeAssertion;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    /**
     * A RoboGuice Provider implementation for UserService.
     */
    class Provider implements com.google.inject.Provider<UserService> {
        @Inject
        private Retrofit retrofit;

        @Override
        public UserService get() {
            return retrofit.create(UserService.class);
        }
    }

    @GET("/api/user/v1/accounts/{username}")
    Call<Account> getAccount(@Path("username") String username);

    @PATCH("/api/user/v1/accounts/{username}")
    Call<Account> updateAccount(@Path("username") String username, @Body Map<String, Object> fields);

    @POST("/api/user/v1/accounts/{username}/image")
    Call<ResponseBody> setProfileImage(@Path("username") String username, @Header("Content-Disposition") String contentDisposition, @Body RequestBody file);

    @DELETE("/api/user/v1/accounts/{username}/image")
    Call<ResponseBody> deleteProfileImage(@Path("username") String username);

    @GET("/api/mobile/v0.5/users/{username}/course_enrollments")
    Call<ResponseBody> getUserEnrolledCourses(@Path("username") String username,
                                              @Query("org") String org);

    @GET("/api/badges/v1/assertions/user/{username}?" + ApiConstants.PARAM_PAGE_SIZE)
    Call<Page<BadgeAssertion>> getBadges(@Path("username") String username,
                                         @Query("page") int page);
}

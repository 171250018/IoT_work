package com.seciii.oasis.controller.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author deng
 * @date 2019/03/11
 */
@Controller
public class ViewController {
    @RequestMapping(value = "/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/datasource/time")
    public String getDataSourceTime() {
        return "timePerspective";
    }

    @RequestMapping(value = "/datasource/see")
    public String getDataSourceSaw() {
        return "datasourceSaw";
    }

    @RequestMapping(value = "/api/service")
    public String getApiService() {
        return "apiService";
    }

    @RequestMapping(value = "/api/detail")
    public String getApiDetail() {
        return "apiDetail";
    }

    @RequestMapping(value = "/api/connect")
    public String getApiConnect() {
        return "apiConnect";
    }

    @RequestMapping(value = "/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @RequestMapping(value = "/admin/movie/manage")
    public String getAdminMovieManage() {
        return "adminMovieManage";
    }

    @RequestMapping(value = "/admin/session/manage")
    public String getAdminSessionManage() {
        return "adminScheduleManage";
    }

    @RequestMapping(value = "/admin/cinema/manage")
    public String getAdminCinemaManage() {
        return "adminCinemaManage";
    }

    @RequestMapping(value = "/admin/promotion/manage")
    public String getAdminPromotionManage() {
        return "adminPromotionManage";
    }

    @RequestMapping(value = "/admin/cinema/statistic")
    public String getAdminCinemaStatistic() {
        return "adminCinemaStatistic";
    }

    @RequestMapping(value = "/admin/movieDetail")
    public String getAdminMovieDetail(@RequestParam int id) { return "adminMovieDetail"; }

    @RequestMapping(value = "/admin/memberManage")
    public String getAdminMemberManage() { return "adminMemberManage"; }

    @RequestMapping(value = "/admin/refundManage")
    public String getAdminRefundManage() { return "adminRefundManage"; }

    @RequestMapping(value = "/user/home")
    public String getUserHome() {
        return "userHome";
    }

    @RequestMapping(value = "/user/buy")
    public String getUserBuy() {
        return "userBuy";
    }

    @RequestMapping(value = "/user/movieDetail")
    public String getUserMovieDetail(@RequestParam int id) {
        return "userMovieDetail";
    }

    @RequestMapping(value = "/user/movieDetail/buy")
    public String getUserMovieBuy(@RequestParam int id) {
        return "userMovieBuy";
    }

    @RequestMapping(value = "/user/movie")
    public String getUserMovie() {
        return "userMovie";
    }

    @RequestMapping(value = "/user/member")
    public String getUserMember() {
        return "userMember";
    }

    @RequestMapping(value = "/user/salesLog")
    public String getUserSalesLog() {
        return "userSalesLog";
    }

    @RequestMapping(value = "/user/unpayTicket")
    public String getUserUnpayTicket() {
        return "userUnpayTicket";
    }

    @RequestMapping(value = "/user/refund")
    public String getUserRefund() {
        return "userRefund";
    }

    @RequestMapping(value = "/user/rechargeLog")
    public String getUserRechargeLog() {
        return "userRechargeLog";
    }

    @RequestMapping(value = "/super/admin/role/manage")
    public String getSuperAdminRoleManage() {
        return "superAdminRoleManage";
    }
}

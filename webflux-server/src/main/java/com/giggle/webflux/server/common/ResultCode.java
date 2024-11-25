package com.giggle.webflux.server.common;

public class ResultCode {
    public static Integer UNKNOW_METHOD = 504;
    public static Integer SUCCESS = 200;//成功状态码
    public static Integer ERROR = 201;//失败状态码
    public static Integer UNAUTHORIZED = 401;//未授权
    public static Integer UN_ACCOUNT = 402;//未找到用户
    public static Integer UN_OPEN = 403;//未开放

    public static Integer WX_UNAUTHORIZED = 301; // 用户微信未授权
    public static Integer UN_USER_PHONE = 302; // 未绑定手机号
    public static Integer UN_USER_IV = 307; // 未绑定手机号
    public static Integer ACCOUNT_LOCKED = 303; // 账户被锁定
    public static Integer ADD_RECEIPT_ERROR = 304; // 小票上传异常
    public static Integer QR_RECEIPT_ERROR = 305; // 小票上传异常
    public static Integer QR_RECEIPT_REPETION = 306; // 小票上传异常

    public static Integer SERVER_ERROR = 500;//系统内部异常
    public static Integer WX_CREAT_ORDER_ERROR = 501;//系统内部异常
    public static Integer WX_PAY_NOTIFY_ERROR = 502;//支付回调异常
}

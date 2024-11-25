package com.giggle.webflux.invoke.rest;

import com.giggle.webflux.invoke.bean.FluxPraseClassBean;
import com.giggle.webflux.invoke.bean.FluxPraseMethodBean;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (rest请求handler)
 * @date 2020/12/3 22:16
 */
public interface RestHandler {



    /**
     * 初始化
     * @param fluxPraseClassBean
     * @return
     */
    void init(FluxPraseClassBean fluxPraseClassBean);

    /**
     * 反射调用方法
     * @param fluxPraseMethodBean
     * @return
     */
    Object invokeRest(FluxPraseMethodBean fluxPraseMethodBean);

}

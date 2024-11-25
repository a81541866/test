package com.giggle.webflux.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author guozichen
 * @ClassName:
 * @Description:
 * @date 2020/11/30 10:13
 */
@Data
public class TUserDto {

    private Long id;

    private String userName;

    private Integer sex;

    private LocalDateTime createDime;
}

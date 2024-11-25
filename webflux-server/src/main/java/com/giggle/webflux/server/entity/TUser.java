package com.giggle.webflux.server.entity;

import lombok.*;
import lombok.experimental.Wither;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (这里用一句话描述累的作用)
 * @date 2020/11/24 22:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Wither
@Table("t_user")
public class TUser {
    @Id
    private Long id;

    @Column(value = "user_name")
    private String userName;

    private Integer sex;

    @Column(value = "create_time")
    private LocalDateTime createDime;
}

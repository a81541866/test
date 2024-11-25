package com.giggle.webflux.server.dao;

import com.giggle.webflux.server.entity.TUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (repository)
 * @date 2020/11/24 22:23
 */
@Repository
public interface TUserDao extends ReactiveCrudRepository<TUser, Long> {
}

package com.giggle.webflux.server.service;

import com.giggle.webflux.server.dao.TUserDao;
import com.giggle.webflux.server.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (这里用一句话描述累的作用)
 * @date 2020/11/24 22:24
 */
@Service
public class TUserService {

    @Autowired
    private TUserDao tUserDao;

    public Flux<TUser> findAll () {
        return tUserDao.findAll();
    }

    public Mono<TUser> findById (long id) {
        return tUserDao.findById(id);
    }


    public Mono<TUser> save (Mono<TUser> tUser) {
        return tUser.flatMap(e -> tUserDao.save(e));
    }

    public Mono<Void> update (Mono<TUser> tUser) {
       return tUser.flatMap(item ->
             findById(item.getId())
                    .map(em -> item.withId(em.getId()))
                    .flatMap(tUserDao::save)
                    .then()
        ).then();
    }

    public Mono<Void> delete (Mono<TUser> tUser) {
        return tUser.flatMap(e -> tUserDao.delete(e)).then();
    }

    public Mono<Void> deleteById (long id) {
        return tUserDao.deleteById(id);
    }
}

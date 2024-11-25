package com.giggle.webflux.server.convert;


import com.giggle.webflux.api.dto.TUserDto;
import com.giggle.webflux.server.entity.TUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (这里用一句话描述累的作用)
 * @date 2020/11/30 10:18
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TUserConvert {

    TUserConvert INSTANCE = Mappers.getMapper(TUserConvert.class);

    @Mappings({})
    TUserDto domain2dto(TUser TUser);

    @Mappings({})
    TUser dtoToDomain(TUserDto tUserDto);

    List<TUserDto> domain2dto(List<TUser> TUsers);
}

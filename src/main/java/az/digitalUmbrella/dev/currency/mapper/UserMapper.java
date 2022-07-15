package az.digitalUmbrella.dev.currency.mapper;

import az.digitalUmbrella.dev.currency.dto.request.UserCreateRequest;
import az.digitalUmbrella.dev.currency.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserCreateRequest userCreateRequest);

}
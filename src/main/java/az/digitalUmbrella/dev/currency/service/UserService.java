package az.digitalUmbrella.dev.currency.service;

import az.digitalUmbrella.dev.currency.dto.request.UserCreateRequest;
import az.digitalUmbrella.dev.currency.error.ErrorCodes;
import az.digitalUmbrella.dev.currency.error.ServiceException;
import az.digitalUmbrella.dev.currency.mapper.UserMapper;
import az.digitalUmbrella.dev.currency.model.Role;
import az.digitalUmbrella.dev.currency.model.User;
import az.digitalUmbrella.dev.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static az.digitalUmbrella.dev.currency.error.ErrorCodes.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userEntityRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userEntityRepository.findByUsername(username)
                .orElseThrow(
                        () -> ServiceException.of(
                                USER_NOT_FOUND, "User not found with userName: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles()
                .forEach(role -> authorities.add(
                        new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

    public void save(UserCreateRequest userCreateRequest) {

        if (userEntityRepository.existsByUsername(userCreateRequest.getUsername())) {
            throw ServiceException.of(ErrorCodes.ALREADY_TAKEN, "Username is already taken!");
        }

        User newUser = userMapper.toUser(userCreateRequest);
        newUser.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

        var role = new Role();
        role.setName("ADMIN");
        role.setDescription("ROLE ADMIN");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        newUser.setRoles(roleSet);
        userEntityRepository.save(newUser);
    }

}

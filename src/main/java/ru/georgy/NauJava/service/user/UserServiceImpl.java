package ru.georgy.NauJava.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.georgy.NauJava.mapper.UserMapper;
import ru.georgy.NauJava.model.Role;
import ru.georgy.NauJava.model.User;
import ru.georgy.NauJava.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(mapRoles(user))
                .build();
    }

    @Override
    @Transactional
    public UserResponse registerUser(UserInput userInput) {
        if (userRepository.findByUsername(userInput.username()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = userMapper.toEntity(userInput);
        user.setPasswordHash(passwordEncoder.encode(userInput.password()));
        user.setRoles(Set.of(Role.USER));

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    private User findByUsername(String username){
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.orElseThrow(
                () -> new UsernameNotFoundException("User %s not found".formatted(username)));
    }

    private Collection<GrantedAuthority> mapRoles(User appUser)
    {
        return appUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }
}

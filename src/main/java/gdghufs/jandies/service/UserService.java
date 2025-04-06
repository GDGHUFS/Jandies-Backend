package gdghufs.jandies.service;

import gdghufs.jandies.dto.UserDto;
import gdghufs.jandies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto findById(Long userId) {
        return UserDto.fromEntity(userRepository.findByid(userId).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public Long count() {
        return userRepository.count();
    }
}

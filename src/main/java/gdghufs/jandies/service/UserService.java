package gdghufs.jandies.service;

import gdghufs.jandies.dto.LinkTreeDto;
import gdghufs.jandies.dto.UserDto;
import gdghufs.jandies.entity.LinkTree;
import gdghufs.jandies.entity.LinkTreeType;
import gdghufs.jandies.entity.User;
import gdghufs.jandies.repository.LinkTreeRepository;
import gdghufs.jandies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final LinkTreeRepository linkTreeRepository;

    public UserDto findById(Long userId) {
        return UserDto.fromEntity(userRepository.findByid(userId).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        User existingUser = userRepository.findByid(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setBio(userDto.getBio());

        return UserDto.fromEntity(userRepository.save(existingUser));
    }

    public Long count() {
        return userRepository.count();
    }


    public Boolean saveLinkTree(Long userId, LinkTreeDto linkTreeDto) {
        User user = userRepository.findByid(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        linkTreeRepository.save(
                LinkTree.builder()
                        .user(user)
                        .link(linkTreeDto.getLink())
                        .type(linkTreeDto.getType())
                        .build()
        );

        return true;
    }

    public Boolean deleteLinkTree(Long userId, Long linkTreeId) {
        User user = userRepository.findByid(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        LinkTree linkTree = linkTreeRepository.findByid(linkTreeId)
                .orElseThrow(() -> new RuntimeException("링크트리를 찾을 수 없습니다."));

        if (!Objects.equals(linkTree.getUser().getId(), user.getId())) {
            throw new RuntimeException("링크트리의 소유자가 아닙니다.");
        }

        linkTreeRepository.delete(linkTree);
        return true;
    }

    public Boolean updateLinkTree(Long userId, Long linkTreeId, LinkTreeDto linkTreeDto) {
        User user = userRepository.findByid(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        LinkTree linkTree = linkTreeRepository.findByid(linkTreeId)
                .orElseThrow(() -> new RuntimeException("링크트리를 찾을 수 없습니다."));

        if (!Objects.equals(linkTree.getUser().getId(), user.getId())) {
            throw new RuntimeException("링크트리의 소유자가 아닙니다.");
        }

        linkTree.setLink(linkTreeDto.getLink());
        linkTree.setType(linkTreeDto.getType());

        return true;
    }



}

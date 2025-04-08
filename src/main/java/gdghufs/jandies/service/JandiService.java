package gdghufs.jandies.service;

import gdghufs.jandies.dto.FarmDto;
import gdghufs.jandies.dto.FarmRequest;
import gdghufs.jandies.entity.Farm;
import gdghufs.jandies.entity.FarmType;
import gdghufs.jandies.entity.Jandi;
import gdghufs.jandies.entity.Location;
import gdghufs.jandies.entity.User;
import gdghufs.jandies.repository.FarmRepository;
import gdghufs.jandies.repository.JandiRepository;
import gdghufs.jandies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JandiService {

    private final JandiRepository jandiRepository;

    public List<Jandi> getAllJandi(User user) {
        return jandiRepository.findAllById_UserId((user.getId()));
    }

    public List<Jandi> findAllById_UserId(Long idUserId) {
        return jandiRepository.findAllById_UserId(idUserId);
    }

}
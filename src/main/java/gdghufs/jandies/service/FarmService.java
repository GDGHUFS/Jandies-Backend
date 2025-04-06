package gdghufs.jandies.service;

import gdghufs.jandies.dto.FarmDto;
import gdghufs.jandies.dto.FarmRequest;
import gdghufs.jandies.entity.*;
import gdghufs.jandies.repository.FarmRepository;
import gdghufs.jandies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmService {

    @Value("${image.upload.dir}")  // application.properties에 설정된 경로를 주입
    private String uploadDir;

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;


    public FarmDto findById(Long id) {
        return farmRepository.findById(id)
                .map(FarmDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("해당 농장을 찾을 수 없습니다. farm ID = " + id));
    }


    public List<FarmDto> findAll() {
        return farmRepository.findAll().stream()
                .map(FarmDto::fromEntity)
                .collect(Collectors.toList());
    }

    public FarmDto createFarm(FarmRequest farmRequest) throws IOException {
        // 이미지 파일 저장
        MultipartFile file = farmRequest.getImage();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // 이미지 파일을 서버에 저장
        File destinationFile = new File(uploadDir + File.separator + fileName);
        file.transferTo(destinationFile);

        // Farm 객체 생성
        Farm farm = Farm.builder()
                .name(farmRequest.getName())
                .type(farmRequest.getType())
                .location(farmRequest.getLocation())
                .imagePath("/images/" + fileName)  // 저장된 이미지 경로
                .build();

        // Farm 저장
        return FarmDto.fromEntity(farmRepository.save(farm));
    }

    // updateFarm
    public FarmDto updateFarm(Long farmid, FarmRequest farmRequest) throws IOException {
        Farm farm = farmRepository.findById(farmid).orElseThrow(
                () -> new IllegalArgumentException("해당 농장을 찾을 수 없습니다. 농장 ID = " + farmid));
        // 기존 이미지 경로를 사용하고 싶다면 주석 해제
        // 이미지 파일 저장
        MultipartFile file = farmRequest.getImage();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // 이미지 파일을 서버에 저장
        File destinationFile = new File(uploadDir + File.separator + fileName);
        file.transferTo(destinationFile);

        farm.setName(farmRequest.getName());
        farm.setType(farmRequest.getType());
        farm.setLocation(farmRequest.getLocation());
        farm.setImagePath("/images/" + fileName);

        // Farm 저장
        return FarmDto.fromEntity(farmRepository.save(farm));
    }

    public Boolean deleteFarm(Long farmid) {
        Farm farm = farmRepository.findById(farmid).orElseThrow(
                () -> new IllegalArgumentException("해당 농장을 찾을 수 없습니다. 농장 ID = " + farmid));

        // 농장에 속한 사용자들 제거
        for (User user : farm.getUsers()) {
            user.getFarms().remove(farm);
        }
        // 농장 삭제
        farmRepository.delete(farm);
        return true;
    }

    public FarmType[] getFarmTypes() {
        return FarmType.values();
    }

    public Location[] getLocations() {
        return Location.values();
    }


    public Boolean joinFarm(Long farmid, Long userid) {
        Farm farm = farmRepository.findById(farmid).orElseThrow(
                () -> new IllegalArgumentException("해당 농장을 찾을 수 없습니다. 농장 ID = " + farmid));
         User user = userRepository.findById(userid).orElseThrow(
                 () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. 사용자 ID = " + userid));


        // 이미 가입된 농장인지 확인
        if (user.getFarms().contains(farm)) {
            throw new IllegalArgumentException("이미 가입된 농장입니다.");
        }
        // 농장에 사용자 추가
        user.getFarms().add(farm);
        farm.getUsers().add(user);
        // 저장
        userRepository.save(user);
        farmRepository.save(farm);

        return true;
    }

    public Boolean leaveFarm(Long farmid, Long userid) {
        Farm farm = farmRepository.findById(farmid).orElseThrow(
                () -> new IllegalArgumentException("해당 농장을 찾을 수 없습니다. 농장 ID = " + farmid));
        User user = userRepository.findById(userid).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. 사용자 ID = " + userid));

        // 농장에서 사용자 제거
        user.getFarms().remove(farm);
        farm.getUsers().remove(user);
        // 저장
        userRepository.save(user);
        farmRepository.save(farm);

        return true;
    }

}
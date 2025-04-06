package gdghufs.jandies.dto;

import gdghufs.jandies.entity.LinkTree;
import gdghufs.jandies.entity.LinkTreeType;
import gdghufs.jandies.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "링크트리 데이터 전송 객체")
public class LinkTreeDto {
    @Schema(description = "링크트리 ID", example = "1")
    private Long id;

    @Schema(description = "링크트리 타입", example = "Github, Instagram, Naverblog ...")
    private LinkTreeType type;

    @Schema(description = "링크 주소", example = "https://github.com/GDGHUFS")
    private String link;

    public static LinkTreeDto fromEntity(LinkTree linkTree) {
        return new LinkTreeDto(
                linkTree.getId(),
                linkTree.getType(),
                linkTree.getLink()
        );
    }
}
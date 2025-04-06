package gdghufs.jandies.dto;

import gdghufs.jandies.entity.LinkTreeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "링크트리 데이터 전송 객체")
public class LinkTreeResponse {
    @Schema(description = "링크트리 ID", example = "1")
    private Long id;

    @Schema(description = "링크트리 타입", example = "GITHUB")
    private LinkTreeType type;

    @Schema(description = "링크 주소", example = "https://github.com/GDGHUFS")
    private String link;

    @Schema(description = "유저 ID", example = "1")
    private Long userId;
}
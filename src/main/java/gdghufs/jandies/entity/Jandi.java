package gdghufs.jandies.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jandi {

    @EmbeddedId
    private JandiId id;
    private Long count;
    private Boolean isActive;

//    @ManyToOne
//    @JoinColumn(name = "userId")
//    @MapsId("userId")
//    private User user;
}

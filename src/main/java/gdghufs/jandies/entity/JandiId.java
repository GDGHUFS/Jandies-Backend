package gdghufs.jandies.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JandiId implements Serializable {

    private Long userId;
    private LocalDate date;


    // equals, hashCode 꼭 오버라이딩 해줘야 해요
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JandiId)) return false;
        JandiId jandiId = (JandiId) o;
        return Objects.equals(userId, jandiId.userId) &&
                Objects.equals(date, jandiId.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, date);
    }
}

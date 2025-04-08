package gdghufs.jandies.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private FarmType type;

    @Enumerated(value = EnumType.STRING)
    private Location location;

    @ManyToMany(mappedBy = "farms", fetch = FetchType.EAGER)
    private List<User> users;

    private String imagePath;
}
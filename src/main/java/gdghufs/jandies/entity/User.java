package gdghufs.jandies.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 모든 필드를 받는 생성자
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Long githubId;
    private String name;
    private String bio;
    private String login;
    private String email;
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private LocalDateTime userCreatedAt;
    @Column(nullable = false)
    private LocalDateTime githubCreatedAt;

    @OneToMany
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private List<LinkTree> linkTree;

    @ManyToMany
    @JoinTable(
            name = "user_farm",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "farm_id")
    )
    private List<Farm> farms;

}
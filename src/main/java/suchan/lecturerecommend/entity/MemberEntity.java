package suchan.lecturerecommend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import suchan.lecturerecommend.dto.Member;

@Entity
@Getter
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    public static Member EntityToDto(MemberEntity memberEntity) {
        return Member.builder()
            .name(memberEntity.getName())
            .password(memberEntity.getPassword())
            .build();
    }
}


package suchan.lecturerecommend.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import suchan.lecturerecommend.dto.MemberDto;
import suchan.lecturerecommend.entity.interest.InterestCategory;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_lecture_id")
    private MyLecture myLecture;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_category_id")
    private InterestCategory interestCategory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_id")
    private Recommend recommend;



    public Member() {

    }


    public static MemberDto EntityToDto(Member member) {
        return MemberDto.builder()
            .name(member.getName())
            .password(member.getPassword())
            .build();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


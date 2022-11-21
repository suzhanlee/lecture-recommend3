package suchan.lecturerecommend.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import suchan.lecturerecommend.entity.interest.InterestCategory;
import suchan.lecturerecommend.entity.interest.InterestOrgName;
import suchan.lecturerecommend.entity.interest.InterestVideoTime;


@Entity
@Getter
@NoArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String shortDescription;
    private LocalDateTime startDisplay;
    private Boolean mobileAvailable;
    private String teachers;
    private String orgName;
    private String category;
    //    @Nullable
//    private String middleCategory;
    private String language;
    private String effortTime;
    private String videoTime;

    @JoinColumn(name = "my_lecture_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MyLecture myLecture;

    @OneToMany(mappedBy = "lecture")
    private List<RecommendLecture> recommendLectures = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_category_id")
    private InterestCategory interestCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_org_name_id")
    private InterestOrgName interestOrgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_video_time_id")
    private InterestVideoTime interestVideoTime;


    public Lecture(String name, String shortDescription, LocalDateTime startDisplay,
        Boolean mobileAvailable,
        String teachers, String orgName, String category, String language,
        String effortTime, String videoTime) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.startDisplay = startDisplay;
        this.mobileAvailable = mobileAvailable;
        this.teachers = teachers;
        this.orgName = orgName;
        this.category = category;
//        this.middleCategory = middleCategory;
        this.language = language;
        this.effortTime = effortTime;
        this.videoTime = videoTime;
    }
}

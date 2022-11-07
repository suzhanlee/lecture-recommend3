package suchan.lecturerecommend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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
    private String startDisplay;
    private String mobileAvailable;
    private String teachers;
    private String orgName;
    private String category;
    @Nullable
    private String middleCategory;
    private String language;
    private String effortTime;
    private String videoTime;

    public Lecture(String name, String shortDescription, String startDisplay,
        String mobileAvailable,
        String teachers, String orgName, String category, String middleCategory, String language,
        String effortTime, String videoTime) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.startDisplay = startDisplay;
        this.mobileAvailable = mobileAvailable;
        this.teachers = teachers;
        this.orgName = orgName;
        this.category = category;
        this.middleCategory = middleCategory;
        this.language = language;
        this.effortTime = effortTime;
        this.videoTime = videoTime;
    }
}

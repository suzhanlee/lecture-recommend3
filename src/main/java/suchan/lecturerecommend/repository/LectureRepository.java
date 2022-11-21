package suchan.lecturerecommend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import suchan.lecturerecommend.entity.Lecture;
import suchan.lecturerecommend.entity.interest.InterestCategory;


public interface LectureRepository extends JpaRepository<Lecture, Long> {


    List<Lecture> findByCategory(String category);  //간단한 조건검색 3종류

    List<Lecture> findByMobileAvailable(Boolean mobileAvailable);

    List<Lecture> findByOrgName(String orgName);
    List<Lecture> findByName(String name);



}

package suchan.lecturerecommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import suchan.lecturerecommend.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {



}

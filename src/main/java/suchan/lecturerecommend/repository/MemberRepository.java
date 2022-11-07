package suchan.lecturerecommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import suchan.lecturerecommend.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


}

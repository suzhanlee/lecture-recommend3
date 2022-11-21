package suchan.lecturerecommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suchan.lecturerecommend.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


    boolean existsByName(String name);
}

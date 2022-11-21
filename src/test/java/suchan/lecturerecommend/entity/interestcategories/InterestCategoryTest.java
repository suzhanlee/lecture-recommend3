package suchan.lecturerecommend.entity.interestcategories;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class InterestCategoryTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void check() {
        //given
        CategoryName categoryName = new CategoryName();
        categoryName.setCategoryName("aa");
        OrgName orgName = new OrgName();
        orgName.setOrgName("bb");
        VideoTime videoTime = new VideoTime();
        videoTime.setVideoTime("cc");
        //when
        em.persist(categoryName);
        em.persist(orgName);
        em.persist(videoTime);

        //then
    }


}
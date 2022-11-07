package suchan.lecturerecommend.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LectureServiceTest {

    @Autowired
    private LectureService lectureService;

    @Test
    void getDataFromApi() {
        //given
        String dataFromApi = lectureService.getDataFromApi();
        Map<String, Object> objectMap = lectureService.parse(dataFromApi);
        for (Object value : objectMap.values()) {
            System.out.println("value = " + value);
        }
        //when
        lectureService.getLecture();

        //then
    }


}
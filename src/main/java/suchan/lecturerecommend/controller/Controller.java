package suchan.lecturerecommend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import suchan.lecturerecommend.service.LectureService;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final LectureService lectureService;

    @GetMapping("/read/lecture")
    public void readLecture() {
        lectureService.getData();


    }

}

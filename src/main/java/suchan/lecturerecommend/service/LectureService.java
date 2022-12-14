package suchan.lecturerecommend.service;

import static suchan.lecturerecommend.entity.QLecture.lecture;
import static suchan.lecturerecommend.entity.QRecommendLecture.recommendLecture;
import static suchan.lecturerecommend.entity.interest.QInterestCategory.interestCategory;
import static suchan.lecturerecommend.entity.interest.QInterestOrgName.interestOrgName;
import static suchan.lecturerecommend.entity.interest.QInterestVideoTime.interestVideoTime;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import suchan.lecturerecommend.domain.Condition;
import suchan.lecturerecommend.domain.InterestCondition;
import suchan.lecturerecommend.entity.Lecture;
import suchan.lecturerecommend.entity.interest.InterestCategory;
import suchan.lecturerecommend.entity.interest.InterestOrgName;
import suchan.lecturerecommend.entity.interest.InterestVideoTime;
import suchan.lecturerecommend.entity.interest.QInterestVideoTime;
import suchan.lecturerecommend.repository.LectureRepository;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public LectureService(LectureRepository lectureRepository, EntityManager em) {
        this.lectureRepository = lectureRepository;
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void getData() {
        for (int i = 1; i <= 19; i++) {
            String dataFromApi = getDataFromApi(i);

            JSONParser jsonParser = new JSONParser();

            JSONObject jsonObject;
            try {
                jsonObject = (JSONObject) jsonParser.parse(dataFromApi);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            JSONArray jsonArray = (JSONArray) jsonObject.get("results");
            Map<String, Object> resultMap = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.

            for (int k = 0; k < jsonArray.size(); k++) {
                JSONObject results = (JSONObject) jsonArray.get(k);

                String name = results.get("name").toString();
                String shortDescription = results.get("short_description").toString();
//                String startDisplay = results.get("start_display").toString();  //LocalDateTime
                LocalDateTime startDisplay = LocalDateTime.parse(results.get("start").toString());


                '2021-06-29T00:00:00Z'


                Boolean mobileAvailable = Boolean.valueOf(
                    results.get("mobile_available").toString());
                String teachers = results.get("teachers").toString();
                String orgName = results.get("org_name").toString();
                String category = results.get("classfy_name").toString();
                String language = results.get("language_name").toString();
                String effortTime = results.get("effort_time").toString();  //time
                String videoTime = results.get("video_time").toString();  //time

                Lecture lecture = new Lecture(name, shortDescription, startDisplay,
                    mobileAvailable,
                    teachers, orgName
                    , category, language, effortTime, videoTime);

                lectureRepository.save(lecture);
            }
        }
    }

    public String getDataFromApi(int page) {

        String apiUrl =
            "http://apis.data.go.kr/B552881/kmooc/courseList?ServiceKey=ihBJ4WlE6bHXgdOEI5W8IW6PNNkm5aFiJ6x5y8cU7gqgan5%2BqeNl6s%2B16%2Bg%2Bp5LHdajEgAyLcaT%2FvGs251%2BqYw%3D%3D&page="
                + page;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Lecture> findByName(){ //?????? trie?????? ???????????? ?????? ??????
//        lectureRepository.findByName();
//    }

    public List<Lecture> findByCondition(Condition condition) { //???????????? ????????????

        return queryFactory
            .select(lecture)
            .from(lecture)
            .where(categoryEq(condition.getCategory()),
//                startDisplayGt(condition.getStartDisplayGt()),
//                videoTimeGt(condition.getVideoTimeGt()),
                isMobileAvailable(condition.isMobileAvailable())
            )
            .fetch();

    }

    private BooleanExpression categoryEq(String category) {
        if (category == null) {
            return null;
        } else {
            return lecture.category.eq(category);
        }

    }

//    private BooleanExpression startDisplayGt(LocalDateTime startDisplayGt) {
//
//
//
//    }
//
//    private BooleanExpression videoTimeGt(Integer videoTimeGt) {
//
//
//    }

    private BooleanExpression isMobileAvailable(boolean mobileAvailable) {
        return lecture.mobileAvailable.eq(mobileAvailable);
    }

    public List<Lecture> findByRecommend(int offset, int limit) { //??????????????? ??????????????? ??????????????? ????????? ????????? ??????

        return queryFactory
            .select(lecture)
            .from(lecture)
            .join(lecture.recommendLectures, recommendLecture)
            .orderBy(recommendLecture.count().desc())
            .orderBy(lecture.count().desc()) // ??????????????? ????????? ?????????
            .offset(offset)
            .limit(limit)
            .fetch();
    }

//    public List<Lecture> findByAllInterest(InterestCondition interestCondition) { //?????????????????? ??????(?????? ??????)
//
//        return queryFactory
//            .select(lecture)
//            .from(lecture)
//            .where(interestCategoryEq(interestCondition.getCategoryName()))
//            .where(interestOrgNameEq(interestCondition.getOrgName()))
//            .where(interestVideoTimeGt(interestCondition.getVideoTime()))
//            .fetch();
//
//    }
//
//
//    private BooleanExpression interestCategoryEq(String categoryName) {
//        if (categoryName == null) {
//            return null;
//
//        } else {
//            return lecture.category.eq(categoryName);
//        }
//
//    }
//
//    private BooleanExpression interestOrgNameEq(String orgName) {
//        if (orgName == null) {
//            return null;
//        } else {
//            return lecture.orgName.eq(orgName);
//        }
//    }
//
//    private BooleanExpression interestVideoTimeGt(String videoTime) { //?????? ????????? ??????????????? ???????????????
//        if (videoTime == null) {
//            return null;
//        } else {
//            return lecture.videoTime.eq(videoTime);
//        }
//    }


    //controller?????? [o,o,x] ??????????????? ?????? ??????????????? ?????????(??????????????? ?????????)
    //interest??? ?????? ???????????? ???????????? ????????????????????? ????????????
    public List<Lecture> findByAllInterest(int offset, int limit) { //?????????????????? ??????(?????? ??????) -> (?????? ?????????)

        return queryFactory
            .select(lecture)
            .from(lecture)
            .where(getInterestCategories(),
                getInterestOrgNames(),
                getInterestVideoTime()
                )
            .offset(offset)
            .limit(limit)
            .fetch();
    }

    private static BooleanExpression getInterestOrgNames() {
        return lecture.orgName.in(
            new JPQLQuery[]{JPAExpressions
                .select(interestOrgName)
                .from(interestOrgName)}
        );
    }

    private static BooleanExpression getInterestCategories() {
        return lecture.category.in(
            new JPQLQuery[]{JPAExpressions
                .select(interestCategory)
                .from(interestCategory)}
        );
    }

    private static BooleanExpression getInterestVideoTime() {//?????? ????????? ??????????????? ???????????????
        return lecture.videoTime.eq(
            JPAExpressions
                .select(interestVideoTime.videoTime)
                .from(interestVideoTime)
        );
    }

    //????????? ??????????????? ????????? count(?????????)??? ????????????.
    public void saveLecture() { // ??? ?????? ????????? ???????????? + ????????? ????????? (lecture_id??? controller ?????? ??????)

    }















}


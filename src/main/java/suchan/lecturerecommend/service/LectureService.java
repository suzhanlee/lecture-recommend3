package suchan.lecturerecommend.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import suchan.lecturerecommend.entity.Lecture;
import suchan.lecturerecommend.repository.LectureRepository;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;



    public void getData() {
        for (int i = 1; i <= 20; i++) {
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

            for (int k = 0; k < jsonArray.size(); k++) {
                JSONObject results = (JSONObject) jsonArray.get(k);

                String name = results.get("name").toString();
                String shortDescription = results.get("short_description").toString();
                String startDisplay = results.get("start_display").toString();
                String mobileAvailable = results.get("mobile_available").toString();
                String teachers = results.get("teachers").toString();
                String orgName = results.get("org_name").toString();
                String category = results.get("classfy_name").toString();
//                String middleCategory = results.get("middle_classfy_name").toString();
                String language = results.get("language_name").toString();
                String effortTime = results.get("effort_time").toString();
                String videoTime = results.get("video_time").toString();

                Lecture lecture = new Lecture(name, shortDescription, startDisplay,
                    mobileAvailable,
                    teachers, orgName
                    , category, "없음", language, effortTime, videoTime);

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


}


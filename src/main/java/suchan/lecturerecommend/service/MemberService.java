package suchan.lecturerecommend.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import suchan.lecturerecommend.entity.MemberEntity;
import suchan.lecturerecommend.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;

    public void save(MemberEntity memberEntity) {
        memberRepository.save(memberEntity);
    }

    public void encodePassword(MemberEntity memberEntity) {


    }





}

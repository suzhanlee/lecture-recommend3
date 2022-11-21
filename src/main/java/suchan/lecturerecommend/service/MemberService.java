package suchan.lecturerecommend.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import suchan.lecturerecommend.domain.Auth;
import suchan.lecturerecommend.domain.Auth.SignUp;
import suchan.lecturerecommend.entity.Member;
import suchan.lecturerecommend.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;

    public void save(Member member) {
        memberRepository.save(member);
    }


    public Member register(SignUp member) {

        boolean exists = memberRepository.existsByName(member.getUsername());
        if(exists) {
            throw new RuntimeException("이미 사용 중인 아이디 입니다.");
        }
//        member.setPassword();
        Member memberEntity = Member.builder()
            .name(member.getUsername())
            .password(member.getPassword())
            .build();

        return memberRepository.save(memberEntity);

    }


}

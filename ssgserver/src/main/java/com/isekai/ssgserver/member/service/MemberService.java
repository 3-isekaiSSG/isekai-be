package com.isekai.ssgserver.member.service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.MemberJoinDto;
import com.isekai.ssgserver.member.dto.MemberLoginDto;
import com.isekai.ssgserver.member.entity.Member;
import com.isekai.ssgserver.member.repository.MemberRepository;
import com.isekai.ssgserver.util.jwt.JwtProvider;
import com.isekai.ssgserver.util.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void isDuplicate(String accountId) {
        Optional<Member> existingMember = memberRepository.findByAccountId(accountId);
        if (existingMember.isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
        }
    }

    public void join(MemberJoinDto joinDto) {
        // 가입 정보 저장 + 비밀번호는 암호화해서 저장
        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());

        Member member = joinDto.toEntity(encodedPassword);
        memberRepository.save(member);
    }


    public JwtToken login(MemberLoginDto loginDto) {
        Member member = memberRepository.findByAccountId(loginDto.getAccountId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        JwtToken jwtToken = jwtProvider.createToken(member.getAccountId());
        return new JwtToken(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}

package xxxxxx.yyyyyy.zzzzzz.domain.service.member;

import javax.validation.constraints.Size;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import xxxxxx.yyyyyy.zzzzzz.domain.model.Member;
import xxxxxx.yyyyyy.zzzzzz.domain.repository.member.MembersSearchCriteria;

@Validated
public interface MemberService {

    Page<Member> searchMembers(MembersSearchCriteria criteria, Pageable pageable);

    Member createMember(Member inputtedMember);

    Member getMember(String memberId);

    Member updateMember(String memberId, Member inputtedMember);

    void deleteMember(String memberId);

}

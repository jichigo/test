package xxxxxx.yyyyyy.zzzzzz.domain.service.member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;
import javax.validation.constraints.Size;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import xxxxxx.yyyyyy.zzzzzz.domain.model.Member;
import xxxxxx.yyyyyy.zzzzzz.domain.repository.member.MembersSearchCriteria;

@Validated
@Service
public class MemberServiceImpl implements MemberService {

    private ConcurrentMap<String, Member> members = new ConcurrentHashMap<String, Member>();

    @Inject
    Mapper beanMapper;

    public Page<Member> searchMembers(MembersSearchCriteria criteria,
            Pageable pageable) {
        List<Member> members = new ArrayList<Member>();
        for (int i = 0; i < pageable.getPageSize(); i++) {
            Member member = new Member();
            member.setMemberId(generateMemberId());
            members.add(member);
        }
        return new PageImpl<Member>(members, pageable, 10);
    }

    public Member createMember(Member inputtedMember) {
        inputtedMember.setMemberId(generateMemberId());
        members.put(inputtedMember.getMemberId(), inputtedMember);
        return inputtedMember;
    }

    public Member getMember(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            throw new ResourceNotFoundException(ResultMessages.error().add(
                    "e.xx.fw.5001", memberId));
        }
        return member;
    }

    public Member updateMember(String memberId, Member inputtedMember) {
        Member member = getMember(memberId);
        beanMapper.map(inputtedMember, member);
        return member;
    }

    public void deleteMember(String memberId) {
        members.remove(memberId);
    }

    private String generateMemberId() {
        String memberId = UUID.randomUUID().toString();
        return memberId;
    }

}

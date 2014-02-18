package xxxxxx.yyyyyy.zzzzzz.app.rest;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriTemplate;

import xxxxxx.yyyyyy.zzzzzz.app.rest.MemberResource.MemberCreating;
import xxxxxx.yyyyyy.zzzzzz.app.rest.MemberResource.MemberUpdating;
import xxxxxx.yyyyyy.zzzzzz.domain.model.Member;
import xxxxxx.yyyyyy.zzzzzz.domain.repository.member.MembersSearchCriteria;
import xxxxxx.yyyyyy.zzzzzz.domain.service.member.MemberService;

@RequestMapping("members")
@Controller
public class MembersRestController {

    @Value("${baseUri}/members/{member}")
    String uriTemplateText;
    
    @Inject
    MemberService memberSevice;

    @Inject
    Mapper beanMapper;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD }) // (4)
    @ResponseBody // (5)
    public ResponseEntity<MembersResource> getMembers(
            @Validated MembersSearchQuery query, // (6)
            Pageable pageable) { // (7)

        // (8)
        MembersSearchCriteria criteria = beanMapper.map(query,
                MembersSearchCriteria.class);
        Page<Member> page = memberSevice.searchMembers(criteria, pageable);

        HttpHeaders responseHeaders = new HttpHeaders();

        // (9)
        MembersResource responseResource = new MembersResource();
        responseResource.setTotalCount(page.getTotalElements());
        for (Member member : page.getContent()) {
            MemberResource memberResource = beanMapper.map(member,
                    MemberResource.class);
            responseResource.addMemeber(memberResource);
        }
        
        // (10)
        return new ResponseEntity<MembersResource>(responseResource, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST) // (1)
    @ResponseBody // (2)
    public ResponseEntity<MemberResource> createMember(
           @Validated({ Default.class, MemberCreating.class }) HttpEntity<MemberResource> requestedResource) { // (3)

        // (4)
        Member inputtedMember = beanMapper.map(requestedResource.getBody(), Member.class);
        Member createdMember = memberSevice.createMember(inputtedMember);

        // (5)
        MemberResource responseResource = beanMapper.map(createdMember,
                MemberResource.class);

        // (6)
        HttpHeaders responseHeaders = new HttpHeaders();
        UriTemplate uriTemplateOfMembers = new UriTemplate(uriTemplateText);
        responseHeaders.setLocation(uriTemplateOfMembers
                .expand(responseResource.getMemberId()));

        // (7)
        return new ResponseEntity<MemberResource>(responseResource, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    @ResponseBody
    public ResponseEntity<Void> optionsMembers() {

        HttpHeaders responseHeaders = new HttpHeaders();
        Set<HttpMethod> allowMethods = new LinkedHashSet<HttpMethod>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.HEAD);
        allowMethods.add(HttpMethod.POST);
        allowMethods.add(HttpMethod.OPTIONS);
        responseHeaders.setAllow(allowMethods);

        return new ResponseEntity<Void>(responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "{memberId}", method = { RequestMethod.GET,
            RequestMethod.HEAD }) // (1)
    @ResponseBody // (2)
    public ResponseEntity<MemberResource> getMember(
            @PathVariable("memberId") String memberId) { // (3)

        // (4)
        Member member = memberSevice.getMember(memberId);

        // (5)
        MemberResource responseResource = beanMapper.map(member,
                MemberResource.class);

        // (6)
        return new ResponseEntity<MemberResource>(responseResource, HttpStatus.OK);
    }

    @RequestMapping(value = "{memberId}", method = RequestMethod.PUT) // (1)
    @ResponseBody // (2)
    public ResponseEntity<MemberResource> updateMember(
            @PathVariable("memberId") String memberId, // (3)
            @RequestBody @Validated({ Default.class, MemberUpdating.class }) MemberResource requestedResource) { // (4)

        // (5)
        Member inputtedMember = beanMapper.map(requestedResource, Member.class);
        Member updatedMember = memberSevice.updateMember(memberId,
                inputtedMember);

        // (6)
        MemberResource responseResource = beanMapper.map(updatedMember,
                MemberResource.class);

        // (7)
        return new ResponseEntity<MemberResource>(responseResource, HttpStatus.OK);
    }

    @RequestMapping(value = "{memberId}", method = RequestMethod.DELETE) // (1)
    @ResponseBody // (2)
    public ResponseEntity<Void> deleteMember(
            @PathVariable("memberId") String memberId) { // (3)

        // (4)
        memberSevice.deleteMember(memberId);
        
        // (5)
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{memberId}", method = RequestMethod.OPTIONS)
    @ResponseBody
    public ResponseEntity<Void> optionsMember(
            @PathVariable("memberId") String memberId) {

        HttpHeaders responseHeaders = new HttpHeaders();
        Set<HttpMethod> allowMethods = new LinkedHashSet<HttpMethod>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.HEAD);
        allowMethods.add(HttpMethod.PUT);
        allowMethods.add(HttpMethod.DELETE);
        allowMethods.add(HttpMethod.OPTIONS);
        responseHeaders.setAllow(allowMethods);

        return new ResponseEntity<Void>(responseHeaders, HttpStatus.OK);
    }
    
}

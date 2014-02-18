package xxxxxx.yyyyyy.zzzzzz.app.rest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class MembersRestControllerTest {

    private final RestTemplate rest = new RestTemplate();

    private final ObjectMapper mapper;

    public MembersRestControllerTest() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testGetMembers() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members?name={name}";

        try {

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("name", "hoge");
            ResponseEntity<MembersResource> response = rest.getForEntity(uri,
                    MembersResource.class, uriVariables);
            MembersResource membersResource = response.getBody();
            System.out.println(membersResource.getTotalCount());
            System.out.println(membersResource.getMembers());

            dumpResponseEntity(response);

        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test
    public void testCreateMember() throws JsonGenerationException, JsonMappingException, IOException {

        createMember();

    }

    public MemberResource createMember() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members";

        try {
            MemberResource newMember = new MemberResource();
            MemberCredentialResource credential = new MemberCredentialResource();
            newMember.setCredential(credential);
            newMember.setFirstName("Kazuki");
            newMember.setLastName("Shimizu");
            newMember.setDateOfBirth(new Date());
            newMember.setEmailAddress("kazuki.shimizu@test.com");
            newMember.setGender("M");
            newMember.setTelephoneNumber("09012345678");
            newMember.setZipCode("1710051");
            newMember.setAddress("Tokyo");
            credential.setPassword("newpassword");

            Map<String, String> uriVariables = new HashMap<String, String>();
            ResponseEntity<MemberResource> response = rest.postForEntity(uri,
                    newMember, MemberResource.class, uriVariables);
            MemberResource createdMemberResource = response.getBody();

            dumpResponseEntity(response);

            return createdMemberResource;

        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test
    public void testOptionsMembers() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members";

        try {

            Map<String, String> uriVariables = new HashMap<String, String>();

            Set<HttpMethod> allowMethods = rest.optionsForAllow(uri,
                    uriVariables);

            System.out.println(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(allowMethods));

        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test
    public void testGetMember() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members/{memberId}";

        try {

            MemberResource createdMemberResource = createMember();

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", createdMemberResource.getMemberId());

            ResponseEntity<MemberResource> response = rest.getForEntity(uri,
                    MemberResource.class, uriVariables);

            dumpResponseEntity(response);

        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test
    public void testUpdateMember() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members/{memberId}";

        try {

            MemberResource createdMemberResource = createMember();

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", createdMemberResource.getMemberId());

            createdMemberResource.setZipCode("2840024");

            ResponseEntity<MemberResource> response = rest.exchange(uri,
                    HttpMethod.PUT,
                    new HttpEntity<MemberResource>(createdMemberResource),
                    MemberResource.class, uriVariables);

            dumpResponseEntity(response);

        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test
    public void testDeleteMember() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members/{memberId}";

        try {

            MemberResource createdMemberResource = createMember();

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", createdMemberResource.getMemberId());

            createdMemberResource.setZipCode("2840024");

            ResponseEntity<Void> response = rest.exchange(uri,
                    HttpMethod.DELETE, new HttpEntity<Void>((Void) null),
                    Void.class, uriVariables);

            dumpResponseEntity(response);

        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test
    public void testOptionsMember() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members/{memberId}";

        try {

            MemberResource createdMemberResource = createMember();

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", createdMemberResource.getMemberId());

            Set<HttpMethod> allowMethods = rest.optionsForAllow(uri,
                    uriVariables);

            System.out.println(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(allowMethods));

        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Test
    public void testBadRequestCausedByBindException() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members?name={name}";

        try {

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("name", "");
            rest.getForEntity(uri, MembersResource.class, uriVariables);

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() != HttpStatus.BAD_REQUEST) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    @Test
    public void testBadRequestCausedByMethodArgumentNotValidException() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members";

        try {
            MemberResource newMember = new MemberResource();
            newMember.setZipCode("aaa");

            Map<String, String> uriVariables = new HashMap<String, String>();
            rest.postForEntity(uri, newMember, MemberResource.class,
                    uriVariables);

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() != HttpStatus.BAD_REQUEST) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    @Test
    public void testBadRequestCausedByHttpMessageNotReadableException() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members";

        try {
            MemberResourceForTypeError newMember = new MemberResourceForTypeError();
            newMember.setDateOfBirth("char");

            Map<String, String> uriVariables = new HashMap<String, String>();
            rest.postForEntity(uri, newMember, MemberResource.class,
                    uriVariables);

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() != HttpStatus.BAD_REQUEST) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    @Test
    public void testBadRequestCausedByHttpMessageNotReadableException1() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members/{memberId}";

        try {
            MemberResource createdMemberResource = createMember();

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", createdMemberResource.getMemberId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>("{hoge}", headers);

            rest.exchange(uri, HttpMethod.PUT, request, MemberResource.class,
                    uriVariables);

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() != HttpStatus.BAD_REQUEST) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    @Test
    public void testNotFoundCausedByResourceNotFoundException() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members/{memberId}";

        try {

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", "xxxx");

            rest.getForEntity(uri, MemberResource.class, uriVariables);

        } catch (HttpStatusCodeException e) {

            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    @Test
    public void testNotFoundCausedByUnkonwnUri() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/xxx/{memberId}";

        try {

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", "xxxx");

            rest.getForEntity(uri, MemberResource.class, uriVariables);

        } catch (HttpStatusCodeException e) {

            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    @Test
    public void testMethodNotAllowOnMembers() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members";

        try {

            Map<String, String> uriVariables = new HashMap<String, String>();

            rest.delete(uri, uriVariables);

        } catch (HttpStatusCodeException e) {

            if (e.getStatusCode() != HttpStatus.METHOD_NOT_ALLOWED) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    @Test
    public void testMethodNotAllowOnMember() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members/{memberId}";

        try {

            MemberResource createdMemberResource = createMember();

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", createdMemberResource.getMemberId());

            rest.postForEntity(uri, createdMemberResource,
                    MemberResource.class, uriVariables);

        } catch (HttpStatusCodeException e) {

            if (e.getStatusCode() != HttpStatus.METHOD_NOT_ALLOWED) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    @Test
    public void testNotAcceptable() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members/{memberId}.xml";

        try {

            MemberResource createdMemberResource = createMember();

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", createdMemberResource.getMemberId());

            ResponseEntity<MemberResource> response = rest.getForEntity(uri,
                    MemberResource.class, uriVariables);

            dumpResponseEntity(response);

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() != HttpStatus.NOT_ACCEPTABLE) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    @Test
    public void testUnsuportedMediaType() throws JsonGenerationException, JsonMappingException, IOException {

        String uri = "http://localhost:9999/terasoluna-gfw-web-blank/members/{memberId}";

        try {
            MemberResource createdMemberResource = createMember();

            Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("memberId", createdMemberResource.getMemberId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> request = new HttpEntity<String>("<a></a>", headers);

            rest.exchange(uri, HttpMethod.PUT, request, MemberResource.class,
                    uriVariables);

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() != HttpStatus.UNSUPPORTED_MEDIA_TYPE) {
                e.printStackTrace();
                throw e;
            }
            dumpHttpStatusCodeException(e);
        }

    }

    private void dumpResponseEntity(ResponseEntity<?> responseEntity) throws JsonGenerationException, JsonMappingException, IOException {
        System.out.println(responseEntity.getStatusCode());
        System.out.println("---");
        System.out.println(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(responseEntity.getHeaders()));
        System.out.println("---");
        System.out.println(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(responseEntity.getBody()));
    }

    private void dumpHttpStatusCodeException(HttpStatusCodeException e) throws JsonGenerationException, JsonMappingException, IOException {
        System.out.println(e.getStatusCode());
        System.out.println("---");
        System.out.println(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(e.getResponseHeaders()));

    }

}

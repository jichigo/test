package xxxxxx.yyyyyy.zzzzzz.app.rest;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;

public class MemberResource implements Serializable {

    private static final long serialVersionUID = 1L;

    public static interface MemberCreating {
    }

    public static interface MemberUpdating {
    }
    
    private String memberId;

    @NotEmpty
    @Size(max = 128)
    private String firstName;

    @NotEmpty
    @Size(max = 128)
    private String lastName;

    @NotEmpty
    @ExistInCodeList(codeListId="CL_GENDER")
    private String gender;

    @NotNull
    private Date dateOfBirth;

    @NotEmpty
    @Size(max = 256)
    private String emailAddress;

    @JsonSerialize(include = Inclusion.NON_NULL)
    @Size(max = 20)
    private String telephoneNumber;

    @JsonSerialize(include = Inclusion.NON_NULL)
    @Size(max = 20)
    private String zipCode;

    @JsonSerialize(include = Inclusion.NON_NULL)
    @Size(max = 256)
    private String address;

    @JsonSerialize(include = Inclusion.NON_NULL)
    @NotNull
    @Valid
    private MemberCredentialResource credential;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MemberCredentialResource getCredential() {
        return credential;
    }

    public void setCredential(MemberCredentialResource credential) {
        this.credential = credential;
    }

}

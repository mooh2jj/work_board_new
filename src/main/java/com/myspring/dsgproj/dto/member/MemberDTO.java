package com.myspring.dsgproj.dto.member;

public class MemberDTO {

	private String name;
	private String birth;
	private String email;
	private String pw;
	private String pwcheck;
	
	private String userKey;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getPwcheck() {
		return pwcheck;
	}
	public void setPwcheck(String pwcheck) {
		this.pwcheck = pwcheck;
	}
	
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [name=" + name + ", birth=" + birth + ", email=" + email + ", pw=" + pw + ", pwcheck="
				+ pwcheck + ", userKey=" + userKey + "]";
	}
	
	
}

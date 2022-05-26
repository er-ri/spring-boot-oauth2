package com.example.securitylogin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_entity")
public class UserEntity {
	@Id
    @Column(
            name = "user_name",
            nullable = false
    )
    private String userName;
    
    @Column(
            name = "password",
            nullable = false
    )
    private String password;
    
    @Column(
            name = "github_id",
            nullable = false
    )
    private String githubId;
    
    @Column(
            name = "line_id",
            nullable = false
    )
    private String lineId;
    
    public String getGithubId() {
		return githubId;
	}

	public void setGithubId(String githubId) {
		this.githubId = githubId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	@Column(
            name = "active",
            nullable = true
    )
    private boolean active;
    
    @Column(
            name = "authorities",
            nullable = true
    )
    private String authorities;
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
}

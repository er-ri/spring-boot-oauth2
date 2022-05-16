package com.example.securitylogin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
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
            name = "active",
            nullable = true
    )
    private boolean active;
    
    @Column(
            name = "roles",
            nullable = true
    )
    private String roles;
    
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
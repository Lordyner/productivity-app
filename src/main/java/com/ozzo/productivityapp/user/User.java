package com.ozzo.productivityapp.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ozzo.productivityapp.opportunity.Opportunity;
import com.ozzo.productivityapp.role.Role;
import com.ozzo.productivityapp.role.RoleEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name="users")
public class User implements UserDetails {

	private static final long serialVersionUID = -1869271205282372120L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idUser;
	
	@OneToMany(mappedBy = "user")
    @PrimaryKeyJoinColumn
	private List<Opportunity> opportunities = new ArrayList<>();
	

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "id_user"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	
	
	private String userName;
	private String email;
	private String password;
	
	private boolean locked = false;
	private boolean enabled = false;
	
	private String refreshToken;

	
	/* Profile information */
	private LocalDate birth_date;
	private String firstName;
	private String lastName;
	private String profileImageLink;

	
	public User() {
		super();
	}
	
	public User(List<Opportunity> opportunities, LocalDate birth_date, String firstName, String lastName,
			String profileImageLink, String userName, String email, String password, Set<RoleEnum> userRole,
			boolean locked, boolean enabled) {
		this.opportunities = opportunities;
		this.birth_date = birth_date;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profileImageLink = profileImageLink;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.locked = locked;
		this.enabled = enabled;
	}
	
	/* Setter */
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public void setDate(LocalDate birth_date) {
		this.birth_date = birth_date;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setProfileImageLink(String profileImageLink) {
		this.profileImageLink = profileImageLink;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	/* Getter */
	
	public Integer getIdUser() {
		return idUser;
	}
	public LocalDate getDate() {
		return birth_date;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getProfileImageLink() {
		return profileImageLink;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
			.map(role -> new SimpleGrantedAuthority(role.getName().toString()))
			.collect(Collectors.toList());		
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	
	

	

}

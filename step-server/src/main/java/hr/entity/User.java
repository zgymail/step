package hr.entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hr.pb.Author.PBUserGender;
import com.hr.pb.Author.PBUserRegisterType;
@Entity
public class User implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private PBUserRegisterType registerType;
	
	private String identity;//unionid
	
	private String nickname="";
	
	private String password="";
	
	private PBUserGender gender=PBUserGender.None;
	
	private String headicon;
	
	private boolean locked;
	
	private String openid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PBUserRegisterType getRegisterType() {
		return registerType;
	}

	public void setRegisterType(PBUserRegisterType registerType) {
		this.registerType = registerType;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PBUserGender getGender() {
		return gender;
	}

	public void setGender(PBUserGender gender) {
		this.gender = gender;
	}

	public String getHeadicon() {
		return headicon;
	}

	public void setHeadicon(String headicon) {
		this.headicon = headicon;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}



	
}

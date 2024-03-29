package fashion.dto;

public class Member {
	
	private int userId;
	private String name;
	private String password;
	private String gender;
	private String email;
	private String address;
	
	public Member(){
		
	}
	
	


	public Member(int userId, String name, String password, String gender, String email, String address) {
		
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.email = email;
		this.address = address;
	}




	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
	

	
	
	
	
	
    

}

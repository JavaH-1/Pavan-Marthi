package in.sp.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student
{
	@Value("Deepak")
	private String name;
	
	@Value("103")
	private int rollno;
	
	@Value("deepak@gmail.com")
	private String emailid;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	public void display()
	{
		System.out.println("Name : "+name);
		System.out.println("Roll No : "+rollno);
		System.out.println("Email Id : "+emailid);
	}
}
package in.sp.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.sp.beans.Student;
import in.sp.config.AppConfig;

public class MainApp 
{
	public static void main(String[] args)
	{
        //Spring container is created
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
        //Bean object is requested from Spring Container and stored in Student reference
		Student std = context.getBean(Student.class);

		std.display();
	}
}
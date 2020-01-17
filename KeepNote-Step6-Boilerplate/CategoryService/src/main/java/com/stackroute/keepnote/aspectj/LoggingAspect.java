package com.stackroute.keepnote.aspectj;
import java.util.Date;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of User controller, any particular
	 * method will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	@Before("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void beforeLog() {
		logger.info("@Before : "+new Date());
	}
	
	
	@After("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void afterLog() {
		logger.info("@After : "+new Date());
	}
	
	
	@AfterReturning(pointcut="execution(* com.stackroute.keepnote.controller.*.*(..))",returning="obj")
	public void afterReturning(Object obj) {
		logger.info("returning obj: "+obj);
		
	}
	
	@AfterThrowing(pointcut="execution(* com.stackroute.keepnote.controller.*.*(..))",throwing="exception")
	public void afterThrowing(Exception exception) {
		logger.info("exception: "+exception);
		
	}
}

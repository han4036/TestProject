package board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Aspect
@Log4j2
public class LoggerAspect {

	@Around("execution(* board..controller.*Controller.*(..)) or"+
			"execution(* board..service.*Impl.*(..)) or"+
			"execution(* board..dao.*Mapper.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		if(name.indexOf("Controller") > -1) {
			
			type = "Controller \t:  ";
			
		} else if (name.indexOf("Service") > -1) {
			
			type = "ServiceImpl \t:  ";
			
		} else if(name.indexOf("Mapper") > -1) {
			
			type = "Mapper \t\t:  ";
		}
		
		log.debug("===========================================================");
		log.debug("                    Aspect");
		log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		log.debug("===========================================================");
		
		
		
		return joinPoint.proceed();
		
	}
}

package com.isekai.ssgserver.config.aop;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@Aspect
public class TimeTraceAspect {

	// 시간 측정 타겟 설정 - TimeTrace 어노테이션이 붙은 메서드
	// @Pointcut("execution(* com.example.demo.repository..*(..))")
	@Pointcut("@annotation(com.isekai.ssgserver.config.aop.TimeTrace)")
	private void timeTracePointcut() {
	}

	// 메서드 실행 전후로 Advice 호출
	@Around("timeTracePointcut()")
	public Object traceTime(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();

		try {
			stopWatch.start();
			return joinPoint.proceed(); // 실제 타겟 호출
		} finally {
			stopWatch.stop();
			log.info("{} - 실행 시간 = {}s",
				joinPoint.getSignature().toShortString(),
				stopWatch.getTotalTimeSeconds());
		}
	}
}

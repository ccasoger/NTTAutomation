package com.nttdata.ct.web.service.aspect.evidence;

import com.nttdata.ct.web.service.stepdefinition.ManageScenario;
import com.nttdata.ct.web.service.util.UtilWeb;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

@Aspect
@Component
public class ScreenShotAspect {

    @Autowired
    private ManageScenario scenario;

    @Pointcut("execution(public * *(..)) && @within(ScreenShot)") //this should work for the public pointcut
    public void screenShotPublicMethods() {
        // Do nothing because is a PointCut.
    }

    @Around("screenShotPublicMethods()")
    public Object screenShotPublicMethods(ProceedingJoinPoint invocation) throws Throwable {
        UtilWeb.logger(this.getClass()).log(Level.INFO, "Take screenshot around >>> \"{0}\" method.",
                invocation.getSignature().getName());
        scenario.printFullView();
        Object object = invocation.proceed();
        scenario.printFullView();
        return object;
    }

    @Before("@annotation(ScreenShotBefore)")
    public void takeScreenShotBefore(JoinPoint point) {
        UtilWeb.logger(this.getClass()).log(Level.INFO, "Take screenshot before >>> \"{0}\" method.", point.getSignature().getName());
        scenario.printFullView();
    }

    @After("@annotation(ScreenShotAfter)")
    public void takeScreenShotAfter(JoinPoint point) {
        UtilWeb.logger(this.getClass()).log(Level.INFO, "Take screenshot after >>> \"{0}\" method.", point.getSignature().getName());
        scenario.printFullView();
    }
}
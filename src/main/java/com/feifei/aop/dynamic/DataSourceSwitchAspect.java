package com.qding.bigdata.aop.dynamic;

import com.qding.bigdata.annotations.DataSourceRoute;
import com.qding.bigdata.enums.DbTypeEnum;
import com.qding.bigdata.model.DbConfig;
import com.qding.bigdata.service.DbConfigService;
import com.qding.bigdata.utils.DataSourceHolder;
import com.qding.bigdata.utils.DataSourceManager;
import com.qding.bigdata.utils.DriverConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;


/**
 * @author yanpf
 * @date 2019/5/30 16:29
 * @description
 */

@Slf4j
@Component
@Aspect
public class DataSourceSwitchAspect {

    @Autowired
    DriverConfig defaultConfig;

    @Autowired
    DataSourceManager dataSourceManager;

    @Autowired
    DbConfigService dbConfigService;

    ExpressionParser parser = new SpelExpressionParser();

    LocalVariableTableParameterNameDiscoverer u =
            new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@within(com.qding.bigdata.annotations.DataSourceRoute) || @annotation(com.qding.bigdata.annotations.DataSourceRoute)")
    private void dataSourceRoute() {
        //切面
    }

    @Before("dataSourceRoute()")
    public void beforeSwitchDS(JoinPoint point) {
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();

        Object[] arguments = point.getArgs();
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            String[] paramNames = u.getParameterNames(method);
            //获取DataSourceRoute
            String key = getDataSourceRouteValue(className, method, argClass);
            DriverConfig driverConfig = defaultConfig;
            if (!StringUtils.isEmpty(key)) {
                Expression expression = parser.parseExpression(key);
                EvaluationContext context = new StandardEvaluationContext();
                for (int i = 0; i < arguments.length; i++) {
                    context.setVariable(paramNames[i], arguments[i]);
                }
                try {
                    key = expression.getValue(context, String.class);
                } catch (Exception e) {
                    //do nothing
                }
                DbConfig dbConfig = dbConfigService.getDbConfigByName(key);
                DriverConfig targetDriverConfig = new DriverConfig();
                targetDriverConfig.setUrl(dbConfig.getUrl());
                targetDriverConfig.setDriverClass(DbTypeEnum.valueOf(dbConfig.getDbType()).getDriver());
                targetDriverConfig.setPassword(dbConfig.getPassword());
                targetDriverConfig.setUserName(dbConfig.getUserName());
                DataSourceContextHolder.setDB(dataSourceManager.getDataSource(targetDriverConfig));
            } else {
                DataSourceContextHolder.setDB(dataSourceManager.getDataSource(driverConfig));
            }

        } catch (Exception e) {
            log.error("数据源切换切面执行失败:{}", e.getMessage(), e);
        }
    }

    @After("dataSourceRoute()")
    public void afterSwitchDS() {
        DataSourceHolder db = DataSourceContextHolder.getDB();
        dataSourceManager.returnDataSource(db.getDriverConfig(), db);
        DataSourceContextHolder.clearDB();
    }


    /**
     * 获取DataSourceRoute对象 优先方法，其次类，最后接口
     * @param className
     * @param method
     * @param argClass
     * @return
     * @throws NoSuchMethodException
     */
    private String getDataSourceRouteValue( Class<?> className, Method method, Class[] argClass) throws NoSuchMethodException {
        DataSourceRoute dataSourceRoute = method.getAnnotation(DataSourceRoute.class);
        if (dataSourceRoute == null) {
            dataSourceRoute = className.getAnnotation(DataSourceRoute.class);
        }
        //专门处理mapper
        if (dataSourceRoute == null) {
            Class<?>[] interfaces = className.getInterfaces();
            if (interfaces != null) {
                for (Class<?> anInterface : interfaces) {
                    if (anInterface.getMethod(method.getName(), argClass) != null) {
                        dataSourceRoute = anInterface.getAnnotation(DataSourceRoute.class);
                    }
                    if (dataSourceRoute != null) {
                        break;
                    }
                }
            }
        }

        if(dataSourceRoute != null){
            return dataSourceRoute.value();
        }

        return "";
    }
}

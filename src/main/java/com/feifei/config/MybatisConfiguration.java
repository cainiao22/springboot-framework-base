package com.qding.bigdata.config;

import com.qding.bigdata.plugins.MybatisLogPlugin;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author yanpf
 * @date 2019/5/30 17:32
 * @description
 */

@Configuration
@MapperScan(basePackages = "com.qding.bigdata.mapper")
//该注解的参数对应的类必须存在，否则不解析该注解修饰的配置类
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
public class MybatisConfiguration {

    //创建SqlSessionFactory
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return  factoryBean.getObject();
    }

    //利用SqlSessionFactory创建SqlSessionTemplate
    @Bean
    public SqlSessionTemplate localSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        //加载自定义插件
        sqlSessionFactory.getConfiguration().addInterceptor(new MybatisLogPlugin(2000));
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 自动加载被去掉了，这个也就没用了
     **/
    /*@Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.addInterceptor(new MybatisLogPlugin(2000));
    }*/

}

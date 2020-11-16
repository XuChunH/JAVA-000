package com.yehui.starter.starter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author yehui
 * @date 2020/11/16
 */
@Configuration
@ConditionalOnClass(value = School.class)
@ConditionalOnProperty(prefix = "school.starter", value = "enable", matchIfMissing = true)
public class SchoolAutoConfiguration {

    @ConditionalOnMissingBean(value = Student.class)
    @Bean
    public Student student() {
        return Student.create();
    }

    @ConditionalOnMissingBean(value = Klass.class)
    @Bean
    public Klass klass(List<Student> students) {

        final Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }

    @ConditionalOnMissingBean(value = School.class)
    @Bean
    public School school(Klass klass, Student student) {

        final School school = new School();
        school.setClass1(klass);
        school.setStudent100(student);

        return school;
    }

}

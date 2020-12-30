package com.winchem.log.sys.validation.annotation;

import com.winchem.log.sys.validation.validator.JustryDengValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @program: winchem_afersale
 * 提示:
 *     1、message、contains、payload是必须要写的
 *     2、还需要什么方法可根据自己的实际业务需求，自行添加定义即可
 *
 * 注:当没有指定默认值时，那么在使用此注解时，就必须输入对应的属性值
 * @description:
 * @author: zhanglb
 * @create: 2020-11-06 11:08
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
// 指定此注解的实现，即:验证器
@Constraint(validatedBy ={JustryDengValidator.class})
public @interface ConstraintsJustryDeng {

    // 当验证不通过时的提示信息
    String message() default "JustryDeng : param value must contais specified value!";

    // 根据实际需求定的方法
    String contains() default "";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default { };

    // 负载
    Class<? extends Payload>[] payload() default { };

}

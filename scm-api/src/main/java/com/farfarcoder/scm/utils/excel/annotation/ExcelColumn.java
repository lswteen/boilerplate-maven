package com.farfarcoder.scm.utils.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String headerName();
    int order();
    String dateFormat() default "yyyy-MM-dd HH:mm:ss";
    boolean skip() default false;
}

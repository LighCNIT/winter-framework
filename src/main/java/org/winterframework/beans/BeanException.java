package org.winterframework.beans;

import org.winterframework.beans.factory.BeanFactory;

/**
 * Bean异常类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description Bean相关的运行时异常，继承RuntimeException为非检查异常
 *              用于在Bean的创建、获取、装配等过程中抛出异常
 */
public class BeanException extends RuntimeException{

    /**
     * 构造方法 - 仅包含异常信息
     * @param msg 异常信息描述
     */
    public BeanException(String msg){
        super(msg);
    }

    /**
     * 构造方法 - 包含异常信息和原因
     * @param msg 异常信息描述
     * @param throwable 原始异常，用于异常链追踪
     */
    public BeanException(String msg,Throwable throwable){
        super(msg,throwable);
    }
}
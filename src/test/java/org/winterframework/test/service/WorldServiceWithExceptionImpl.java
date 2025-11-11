package org.winterframework.test.service;

/**
 * @author Ligh
 * 2025/10/25 11:46
 **/
public class WorldServiceWithExceptionImpl implements WorldService{

    @Override
    public void explode() {
        System.out.println("The Earth is going to explode with an Exception");
        throw new RuntimeException();
    }

    @Override
    public String getName() {
        return null;
    }
}

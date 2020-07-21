package com.ecommerce.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;

import java.util.concurrent.FutureTask;

public class BlockHoundTests {

    @BeforeAll
    public static void setUp(){
        BlockHound.install();
    }

    @Test
    public void shouldReturnBlockingOperationError(){
        try{
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });
        }catch (Exception ex){
            Assertions.assertTrue(ex.getCause() instanceof BlockingOperationError);
        }
    }

}

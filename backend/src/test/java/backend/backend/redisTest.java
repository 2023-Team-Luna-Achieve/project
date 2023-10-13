package backend.backend;

import backend.backend.auth.config.util.RedisUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class redisTest {
    @Autowired
    private RedisUtil redisUtil;
    
    @Test
    @Rollback(value = false)
    public void redisTest() throws Exception {
        String email = "test@test.com";
        String code = "aaa111";

        //when
        redisUtil.setDataExpire(email, code, 10 * 60L);

        //then
        Assertions.assertTrue(redisUtil.existData("test@test.com"));
        Assertions.assertFalse(redisUtil.existData("test1@test.com"));
        Assertions.assertEquals(redisUtil.getData(email), "aaa111");
    } 
}

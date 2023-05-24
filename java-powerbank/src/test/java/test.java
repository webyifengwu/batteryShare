import com.auth0.jwt.JWT;
import com.jee.demo.mapper.UserMapper;
import com.jee.demo.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class test {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        System.out.println(userMapper.getUserByUser("admin", "123456"));
    }

    @Test
    public void testToken() {
        System.out.println(JwtUtil.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODM0NDE0MTQsImlhdCI6MTY4MzQzOTYxNCwidXNlcm5hbWUiOiJhZG1pbiJ9.n--znZxXjvONsGEcQYjYJXL2tMp9vT52sF22fYYW70g"));
    }
}

import com.jee.demo.domain.Order;
import com.jee.demo.domain.User;
import com.jee.demo.mapper.OrderMapper;
import com.jee.demo.mapper.UserMapper;
import com.jee.demo.service.UserService;
import com.jee.demo.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class demo {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void etst() {
//        System.out.println(userService.getUserList(1,5));
        System.out.println(new Date().toString());
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            Double t = Double.parseDouble(r.nextInt(23) + 1 + "");
            if (t.compareTo(15.0) >= 0) {
                System.out.println(t * 15);
            }
        }
    }

    @Test
    public void testUpdateTime() throws UnsupportedEncodingException {

    }

    @Test
    public void testResultUtil() {
        String pZone ="d12";
        String dpZone="a20";
        int dis1 = Math.abs(Integer.parseInt(pZone.substring(1)) - Integer.parseInt(dpZone.substring(1))) * 3;
        System.out.println("dis1"+ dis1);
        int dis2= Math.abs((pZone.charAt(0) - dpZone.charAt(0)))*1000;
        System.out.println("dis2"+ dis2);
        String dis=dis1+dis2+"";
        System.out.println(dis);
    }

    @Test
    public void testUpdatePal() {
        Order order = new Order();
        order.setOrderId("001");
        order.setTime(5.0);
        order.setTotal(10.0);
        order.setStatus(0);
        orderMapper.updateOrder(order);
    }
}

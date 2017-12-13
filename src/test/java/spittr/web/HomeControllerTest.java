package spittr.web;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import spittr.web.HomeController;
/**
 * 
 * </br>
 * *********************
 * </br>
 * 作者：隔壁老王(ಥ _ ಥ) 21岁
 * </br>          
 * *********************
 * </br>
 * 日期：2017年12月13日
 * </br>                
 * *********************
 * </br>
 * 时间：下午3:01:05        
 * </br>        
 * *********************
 * </br>
 * 【MockMvc】解决高频启动Web浏览器/服务器问题，降低开发的时间成本
 * 
 * 编写控制器的单元测试实际上是简单的</br>
 * 需要注意以下几点：</br>
 * 1. 请求的路径是什么？ </br>
 * 2. 响应的结果是什么？ </br>
 * 这两点是测试的核心。
 */
public class HomeControllerTest {
 
  /**
   * 1. 创建HomeController的模拟器：MockMvc mockMvc
   * 2. 模拟发送请求： get("/")
   * 3. 断言请求的响应结果：视图名="home"
   * @throws Exception
   */
  @Test
  public void testHomePage() throws Exception {
    HomeController controller = new HomeController();
    MockMvc mockMvc = standaloneSetup(controller).build(); //【测试类型】单元测试
    mockMvc.perform(get("/")) //【请求】GET: /
           .andExpect(view().name("home")); //【期望】视图名为"home"
  }

}

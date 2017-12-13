package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * 时间：下午2:54:05        
 * </br>        
 * *********************
 * </br>
 * http://localhost:8080/spittr/ entry this controller
 * 
 * 
 */
@Controller
@RequestMapping("/") //类级别请求方式：所有的 "/"请求都将由HomeController来处理
public class HomeController {

  @RequestMapping(method = GET)
  public String home(Model model) {
    return "home";  //返回视图名"home" --> /Spittr/src/main/webapp/WEB-INF/views/home.jsp
  }

}

package spittr.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import spittr.Spittle;
import spittr.data.SpittleRepository;
/**
 * 
 * </br>
 * *********************
 * </br>
 * 作者：隔壁老王(ಥ _ ಥ) 21岁
 * </br>          
 * *********************
 * </br>
 * 日期：2017年12月14日
 * </br>                
 * *********************
 * </br>
 * 时间：上午11:25:02        
 * </br>        
 * *********************
 * </br>
 */
@Controller
@RequestMapping("/spittles")
public class SpittleController {

  private static final String MAX_LONG_AS_STRING = "9223372036854775807";

  private SpittleRepository spittleRepository;

  @Autowired
  public SpittleController(SpittleRepository spittleRepository) {
    this.spittleRepository = spittleRepository;
  }
  /**
   * 
   * @param max
   * @param count
   * @return  {@code List<Spittle>} 控制器返回集合或POJO对象时，会默认填充到{@code Model}模型中。如此一来，页面就能从模型中取出值并呈现。
   * 这里不写{@code return "viewName"}也行，如果没有设置{@code return "viewName"}，Spring MVC会根据请求路径{@code Request URL}
   * 来判断返回的视图。以此为例，请求（{@link http://localhost:8080/spittr/spittles}）后执行该方法，Spring根据'/spittles'推出呈现的视图是{@code spittles.jsp}
   * 
   * 
   */
  @RequestMapping(method=RequestMethod.GET)
  public List<Spittle> spittles(
      @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
      @RequestParam(value="count", defaultValue="20") int count) {
    return spittleRepository.findSpittles(max, count); 
  }
  /**
   * 
   * @param spittleId 
   * @param model 
   * @return
   */
  @RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
  public String spittle(
      @PathVariable("spittleId") long spittleId, 
      Model model) {
    model.addAttribute(spittleRepository.findOne(spittleId));
    return "spittle";
  }

  @RequestMapping(method=RequestMethod.POST)
  public String saveSpittle(SpittleForm form, Model model) throws Exception {
    spittleRepository.save(new Spittle(null, form.getMessage(), new Date(), 
        form.getLongitude(), form.getLatitude()));
    return "redirect:/spittles";
  }

}

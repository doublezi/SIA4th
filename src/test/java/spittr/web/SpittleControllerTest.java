package spittr.web;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import spittr.Spittle;
import spittr.data.SpittleRepository;
import spittr.web.SpittleController;
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
 * 时间：下午3:51:32        
 * </br>        
 * *********************
 * </br>
 * 关于如何写Spring MVC Test，见此类
 */
public class SpittleControllerTest {

  @Test
  public void houldShowRecentSpittles() throws Exception {
    /*
     * 【1】 .createSpittleList(int) 作用：创建int个Spittle对象
     */
	List<Spittle> expectedSpittles = createSpittleList(20); 
	/*
	 * 【2】.mock(Class) 作用:利用模拟器创建SpittleRepository对象。
	 */
    SpittleRepository mockRepository = mock(SpittleRepository.class); 
    /*
     * 【3】
     */
    when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
        .thenReturn(expectedSpittles);
    /*
     * 【4】 传入{【2】}中的参数并创建一个SpittleController控制器
     * 
     */
    SpittleController controller = new SpittleController(mockRepository);
    /*
     * 【5】 传入{【4】}的参数并创建控制器的模拟器，该模拟器用于单元测试
     */
    MockMvc mockMvc = standaloneSetup(controller)//
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();
    /*
     * 【6】 发送 /spittles 请求，呈现 spittles.jsp 页面并且页面包含 spittleList 属性，属性内包含预期的内容。
     */
    mockMvc.perform(get("/spittles"))
       .andExpect(view().name("spittles"))
       .andExpect(model().attributeExists("spittleList")) 
       .andExpect(model().attribute("spittleList", 
                  hasItems(expectedSpittles.toArray())));
  }

  @Test
  public void shouldShowPagedSpittles() throws Exception {
	/*
	 * 【1】创建含有50个Spittle对象的集合  
	 */
    List<Spittle> expectedSpittles = createSpittleList(50);
    /*
     * 【2】创建SpittleRepository对象的模拟器（与{houldShowRecentSpittles().【2】}一样）
     */
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    /*
     * 【3】寓意：当mockRepository调用分页查询器时，返回50个对象
     */
    when(mockRepository.findSpittles(238900, 50))//分页查询器被调用
        .thenReturn(expectedSpittles);//50个对象
    /*
     * 【4】实例化SpittleController
     */
    SpittleController controller = new SpittleController(mockRepository);
    /*
     * 【5】按{【4】}创建控制器的模拟器，该模拟器用于单元测试
     */
    MockMvc mockMvc = standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();
    /*
     * 【6】发送GET请求呈现 spittles.jsp 页面，页面包含属性 spittleList ，属性spittleList可遍历。
     */
    mockMvc.perform(get("/spittles?max=238900&count=50"))
      .andExpect(view().name("spittles"))
      .andExpect(model().attributeExists("spittleList"))
      .andExpect(model().attribute("spittleList", 
                 hasItems(expectedSpittles.toArray())));
  }
  
  @Test
  public void testSpittle() throws Exception {
	/*
	 * 【1】 创建一个含有 消息 和 时间 的 Spittle对象
	 */
    Spittle expectedSpittle = new Spittle("Hello", new Date());
    /*
     * 【2】创建SpittleRepository对象的模拟器（与{houldShowRecentSpittles().【2】}一样）
     */
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    /*
     * 【3】调用findOne(int)方法后返回一个Spittle对象
     */
    when(mockRepository.findOne(12345)).thenReturn(expectedSpittle);
    /*
     * 【4】创建SpittleController控制器
     */
    SpittleController controller = new SpittleController(mockRepository);
    /*
     * 【5】传入{【4】}的参数并创建控制器的模拟器，该模拟器用于单元测试
     */
    MockMvc mockMvc = standaloneSetup(controller).build();
    /*
     * 【6】发送请求GET，呈现视图spittle.jsp，视图存有spittle属性，该属性与expectedSpittle等同。
     */
    mockMvc.perform(get("/spittles/12345"))
      .andExpect(view().name("spittle"))
      .andExpect(model().attributeExists("spittle"))
      .andExpect(model().attribute("spittle", expectedSpittle));
  }

  @Test
  public void saveSpittle() throws Exception {
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(post("/spittles")
           .param("message", "Hello World") // this works, but isn't really testing what really happens
           .param("longitude", "-81.5811668")
           .param("latitude", "28.4159649")
           )
           .andExpect(redirectedUrl("/spittles"));
    
    verify(mockRepository, atLeastOnce()).save(new Spittle(null, "Hello World", new Date(), -81.5811668, 28.4159649));
  }
  
  private List<Spittle> createSpittleList(int count) {
    List<Spittle> spittles = new ArrayList<Spittle>();
    for (int i=0; i < count; i++) {
      spittles.add(new Spittle("Spittle " + i, new Date()));
    }
    return spittles;
  }
}

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
     *  .createSpittleList(int) 作用：创建int个Spittle对象
     */
	List<Spittle> expectedSpittles = createSpittleList(20); 
	/*
	 *  .mock(Class) 作用:创建类的模拟器。
	 *   创建SpittleRepository模拟器
	 */
    SpittleRepository mockRepository = mock(SpittleRepository.class); 
    /*
     * 
     */
    when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
        .thenReturn(expectedSpittles);
    /*
     * 
     */
    SpittleController controller = new SpittleController(mockRepository);
    /*
     *  .setSingleView(...) 作用：mockMvc不需要再解析控制器中的视图名
     */
    MockMvc mockMvc = standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();
    /*
     * 发送 /spittles 请求，呈现 spittles.jsp 页面并且页面包含 spittleList 属性，属性内包含预期的内容。
     */
    mockMvc.perform(get("/spittles"))
       .andExpect(view().name("spittles"))
       .andExpect(model().attributeExists("spittleList")) 
       .andExpect(model().attribute("spittleList", 
                  hasItems(expectedSpittles.toArray())));
  }

  @Test
  public void shouldShowPagedSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittleList(50);
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findSpittles(238900, 50))
        .thenReturn(expectedSpittles);
    
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();

    mockMvc.perform(get("/spittles?max=238900&count=50"))
      .andExpect(view().name("spittles"))
      .andExpect(model().attributeExists("spittleList"))
      .andExpect(model().attribute("spittleList", 
                 hasItems(expectedSpittles.toArray())));
  }
  
  @Test
  public void testSpittle() throws Exception {
    Spittle expectedSpittle = new Spittle("Hello", new Date());
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findOne(12345)).thenReturn(expectedSpittle);
    
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

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

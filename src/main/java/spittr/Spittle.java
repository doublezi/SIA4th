package spittr;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
 * 时间：下午3:33:39        
 * </br>        
 * *********************
 * </br>
 * 这是一个基本的POJO数据对象
 * </br>
 * 基于{@code Apache Common Lang}重写了{@code equral()}和{@code hashCode()}
 * </br>
 * 作者说了：重写目的是为了在为控制器的处理器方法编写测试时，是很有用（质疑中...）
 * </br>
 */
public class Spittle {

  private final Long id;
  private final String message;
  private final Date time;
  private Double latitude;
  private Double longitude;

  public Spittle(String message, Date time) {
    this(null, message, time, null, null);
  }
  
  public Spittle(Long id, String message, Date time, Double longitude, Double latitude) {
    this.id = id;
    this.message = message;
    this.time = time;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public long getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public Date getTime() {
    return time;
  }
  
  public Double getLongitude() {
    return longitude;
  }
  
  public Double getLatitude() {
    return latitude;
  }
  
  @Override
  public boolean equals(Object that) {
    return EqualsBuilder.reflectionEquals(this, that, "id", "time");
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "id", "time");
  }

@Override
public String toString() {
	return "Spittle [id=" + id + ", message=" + message + ", time=" + time + ", latitude=" + latitude + ", longitude="
			+ longitude + "]";
}
  
  
}

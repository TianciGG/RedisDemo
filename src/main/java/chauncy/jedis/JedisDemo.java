package chauncy.jedis;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * @classDesc: 功能描述(客户端连接服务器端redis,操作Redis五种基本数据类型)
 * @author: ChauncyWang
 * @createTime: 2019年6月26日 下午11:39:10
 * @version: 1.0
 */
public class JedisDemo {
	private static Jedis jedis;

	public static void main(String[] args) {
		//连接redis服务器，192.168.0.100:6379
		jedis = new Jedis("192.168.0.218", 6379);
		//权限认证
        //jedis.auth("admin");  
        //ping通与否，若能正常ping通后续代码不受影响
		jedis.ping();
		//setString();
		//setMap();
		//setList();
		//sortList();
		//setSet();
		setZset();
	}

	/**
	 * @methodDesc:功能描述(存储String类型)
	 * @author: ChauncyWang
	 * @param:
	 * @createTime: 2019年6月27日 下午2:30:22
	 * @returnType: void
	 */
	static public void setString() {
		//企业中大多数都是使用String类型，用String存储Json
		 //-----添加数据----------  
        jedis.set("name","chauncy");//向key-->name中放入了value-->xinxin  
        System.out.println(jedis.get("name"));//执行结果：xinxin  
        
        jedis.append("name", " is handsome guy"); //拼接
        System.out.println(jedis.get("name")); 
        
        jedis.del("name");  //删除某个键
        System.out.println(jedis.get("name"));
        //设置多个键值对
        jedis.mset("name","chauncy1","age","18","qq","88888XXX");
        jedis.incr("age"); //进行加1操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
	}

	/**
	 * @methodDesc: 功能描述(存储Hash类型)
	 * @author: ChauncyWang
	 * @param:
	 * @createTime: 2019年6月27日 下午2:32:29
	 * @returnType: void
	 */
	static public void setMap() {
		  //-----添加数据----------  
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "chauncy");
        map.put("age", "18");
        map.put("qq", "888888");
        jedis.hmset("user",map);
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List  
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数  
        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rsmap);  
  
        //删除map中的某个键值  
        jedis.hdel("user","age");
        System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null  
        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2 
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true  
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key  
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value 
  
        Iterator<String> iter=jedis.hkeys("user").iterator();  
        while (iter.hasNext()){  
            String key = iter.next();  
            System.out.println(key+":"+jedis.hmget("user",key));  
        }  
	}

	/**
	 * @methodDesc: 功能描述(存储List类型)
	 * @author: ChauncyWang
	 * @param:
	 * @createTime: 2019年6月27日 下午3:02:28
	 * @returnType: void
	 */
	static public void setList() {
		//开始前，先移除所有的内容  
        jedis.del("java framework");  
        System.out.println(jedis.lrange("java framework",0,-1));  
        //先向key java framework中存放三条数据  
        jedis.lpush("java framework","spring");  
        jedis.lpush("java framework","struts");  
        jedis.lpush("java framework","hibernate");  
        //再取出所有数据jedis.lrange是按范围取出，  
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
        System.out.println(jedis.lrange("java framework",0,-1));  
        
        jedis.del("java framework");
        jedis.rpush("java framework","spring");  
        jedis.rpush("java framework","struts");  
        jedis.rpush("java framework","hibernate"); 
        System.out.println(jedis.lrange("java framework",0,-1));
	}
	
	/**   
	 * @methodDesc: 功能描述(对List类型进行排序)  
	 * @author: ChauncyWang
	 * @param:    
	 * @createTime: 2019年6月27日 下午6:02:34   
	 * @returnType: void  
	 */  
	static public void sortList(){  
        //jedis 排序  
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）  
        jedis.del("a");//先清除数据，再加入数据进行测试  
        jedis.rpush("a", "1");  
        jedis.lpush("a","6");  
        jedis.lpush("a","3");  
        jedis.lpush("a","9");  
        System.out.println(jedis.lrange("a",0,-1));// [9, 3, 6, 1]  
        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果  
        System.out.println(jedis.lrange("a",0,-1));  
    }  

	
	/**
	 * @methodDesc: 功能描述(存储Set类型)
	 * @author: ChauncyWang
	 * @param:
	 * @createTime: 2019年6月27日 下午3:05:39
	 * @returnType: void
	 */
	static public void setSet() {
		//添加  
        jedis.sadd("user","chauncy1"); 
        // 不可重复，不会进行插入
        jedis.sadd("user","chauncy1");  
        jedis.sadd("user","chauncy2");  
        jedis.sadd("user","chauncy3");
        jedis.sadd("user","chauncy4");  
        //移除noname  
        jedis.srem("user","chauncy1");  
        System.out.println(jedis.smembers("user"));//获取所有加入的value  
        System.out.println(jedis.sismember("user", "chauncy1"));//判断 chauncy1 是否是user集合的元素  
        System.out.println(jedis.srandmember("user"));  
        System.out.println(jedis.scard("user"));//返回集合的元素个数  

	}

	/**
	 * @methodDesc: 功能描述(存储SortedSet类型)
	 * @author: ChauncyWang
	 * @param:
	 * @createTime: 2019年6月27日 下午3:28:51
	 * @returnType: void
	 */
	static public void setZset() {
		//值不允许重复，排序分数允许重复，值重复的情况，取排序分数大的值。
		jedis.zadd("zsetKey", 1, "zset2");
		jedis.zadd("zsetKey", 1, "zset1");
		jedis.zadd("zsetKey", 1, "zset3");
		jedis.zadd("zsetKey", 1, "zset1");
		jedis.zadd("zsetKey", 2, "zset3");
		jedis.zadd("zsetKey", 2, "zset4");
		jedis.zadd("zsetKey", 3, "zset5");
	}
}

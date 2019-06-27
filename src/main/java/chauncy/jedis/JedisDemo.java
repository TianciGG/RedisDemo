package chauncy.jedis;
import java.util.HashMap;

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
		jedis = new Jedis("192.168.0.218", 6379);
		jedis.ping();
		// setString();
		// setMap();
		// setList();
		// setSet();
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
		System.out.println("redis连接成功。。。");
		jedis.set("chauncy", "21");
		System.out.println("存储成功！");
	}

	/**
	 * @methodDesc: 功能描述(存储Hash类型)
	 * @author: ChauncyWang
	 * @param:
	 * @createTime: 2019年6月27日 下午2:32:29
	 * @returnType: void
	 */
	static public void setMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user1", "chauncy1");
		map.put("user2", "chauncy2");
		jedis.hmset("mapKey", map);
	}

	/**
	 * @methodDesc: 功能描述(存储List类型)
	 * @author: ChauncyWang
	 * @param:
	 * @createTime: 2019年6月27日 下午3:02:28
	 * @returnType: void
	 */
	static public void setList() {
		jedis.lpush("listKey", "redis1");
		jedis.lpush("listKey", "redis2");
		jedis.lpush("listKey", "redis3");
	}

	/**
	 * @methodDesc: 功能描述(存储Set类型)
	 * @author: ChauncyWang
	 * @param:
	 * @createTime: 2019年6月27日 下午3:05:39
	 * @returnType: void
	 */
	static public void setSet() {
		jedis.sadd("setKey", "redis1");
		// 不可重复，会进行插入
		jedis.sadd("setKey", "redis1");
		jedis.sadd("setKey", "redis2");
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

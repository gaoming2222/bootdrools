package com.springboot.tools;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class redisHelper {
	protected static Logger logger = Logger.getLogger(redisHelper.class);
	protected static JedisPool jedisPool;
	protected static int EXPIRE = 130;
	static{
		
	}
}

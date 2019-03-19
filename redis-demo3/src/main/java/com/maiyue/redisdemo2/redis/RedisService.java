package com.maiyue.redisdemo2.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

	private Logger logger = LoggerFactory.getLogger(RedisService.class);

	@Autowired
	private JedisPool jedisPool;

	public static class Redis {
		private static StringBuffer buffer() {
			return new StringBuffer("CAREYE:").append("USER:");
		}

		public static String userInfo(String name) {
			StringBuffer buffer = buffer().append("INFO:").append(name);
			return buffer.toString();
		}

		public static String token(String token) {
			StringBuffer buffer = buffer().append("TOKEN:").append(token);
			return buffer.toString();
		}
	}

	// 获取key的value值
	public String get(String key) {

		String str = "";
		String ss = Redis.token(key);
		try (Jedis jedis = jedisPool.getResource()) {
			str = jedis.get(ss);
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return str;
	}

	// 增加String缓存
	public String set(String key, String value) {

		String str = "";
		String ss = Redis.token(key);
		try (Jedis jedis = jedisPool.getResource()) {
			str = jedis.set(ss, value);
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return str;
	}

	// 增加String缓存并设置过期时间
	public String setExpire(String key, int seconds, String value) {
		String str = "";
		try (Jedis jedis = jedisPool.getResource()) {
			str = jedis.setex(Redis.token(key), (int) TimeUnit.MINUTES.toMinutes(seconds), value);
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return str;
	}

	// 存入map到缓存中
	public String setUserMap(String key, Map<String, String> map) {
		String str = "";
		try (Jedis jedis = jedisPool.getResource()) {
			str = jedis.hmset(Redis.userInfo(key), map);
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return str;
	}

	// 存入map到缓存中
	public String setTokenMap(String key, Map<String, String> map) {
		String str = "";
		try (Jedis jedis = jedisPool.getResource()) {
			str = jedis.hmset(Redis.token(key), map);
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return str;
	}

	// 拿到缓存某个map
	public Map<Object, Object> getUserMap(String key) {

		Map<Object, Object> map = null;
		try (Jedis jedis = jedisPool.getResource()) {
			Map<String, String> all = jedis.hgetAll(Redis.userInfo(key));
			if (all != null && all.size() > 0) {
				map = new HashMap<>();
				for (Entry<String, String> kv : all.entrySet()) {
					map.put(kv.getKey(), kv.getValue());
				}
				return map;
			}
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return null;
	}

	public Map<Object, Object> getTokenMap(String key) {
		Map<Object, Object> map = null;
		try (Jedis jedis = jedisPool.getResource()) {
			Map<String, String> all = jedis.hgetAll(Redis.userInfo(key));
			if (all != null && all.size() > 0) {
				map = new HashMap<>();
				for (Entry<String, String> kv : all.entrySet()) {
					map.put(kv.getKey(), kv.getValue());
				}
				return map;
			}
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return null;
	}

	// 设置过期时间
	public long expire(String key, int seconds) {
		List<String> keys = new ArrayList<String>();
		keys.add(Redis.userInfo(key));
		keys.add(Redis.token(key));
		try (Jedis jedis = jedisPool.getResource()) {
			for (String k : keys) {
				jedis.expire(k, seconds);
			}
			return 1;
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return 0;
	}

	// 删除缓存
	public long delete(String key) {
		List<String> keys = new ArrayList<String>();
		keys.add(Redis.userInfo(key));
		keys.add(Redis.token(key));
		try (Jedis jedis = jedisPool.getResource()) {
			for (String k : keys) {
				jedis.del(k);
			}
			return 1;
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return 0;
	}

	// 得到map所有的key
	public Set<String> getMapKey(String key) {
		Set<String> set = null;
		try (Jedis jedis = jedisPool.getResource()) {
			List<String> keys = new ArrayList<String>();
			keys.add(Redis.userInfo(key));
			keys.add(Redis.token(key));
			for (String k : keys) {
				set = jedis.hkeys(k);
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String str = it.next();
					set.add(str);
				}

			}
			return set;
		} catch (Exception e) {
			logger.info("非法调用");
		}
		return null;
	}
}

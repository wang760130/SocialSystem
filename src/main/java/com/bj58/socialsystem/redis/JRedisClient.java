package com.bj58.socialsystem.redis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

/**
 * 说明:   JRedisHelper帮助类，规范入口	 
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年1月15日
 */
public class JRedisClient implements JedisCommands{
	
	private ShardedJedisPool pool = null;
	
	public JRedisClient(ShardedJedisPool pool){
		this.pool = pool;
	}
	
	@Override
	public Long ttl(String key){
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.ttl(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	@Override
	public String set(String key, String value) {
		String result = null;
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			result = jedis.set(key, value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	public String set(String key, String value,int second) {
		String result = setOnlyExist(key,value,second);
		if(null == result || !result.equals("OK")){
			result = setOnlyNotExist(key,value,second);
		}
		return result;
	}
	
	public String setOnlyNotExist(String key, String value, int second) {
		return this.set(key,value,"NX","EX",second);
	}
	
	public String setOnlyExist(String key, String value, int second) {
		return this.set(key,value,"XX","EX",second);
	}
	
	
	public String set(String key, String value, String nxxx, String expx,
			long time) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.set(key, value, nxxx, expx, time);
		} finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public String get(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.get(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public long len(String key){
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.llen(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Boolean exists(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.exists(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	public Long persist(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.persist(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Long expire(String key, int seconds) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.expire(key, seconds);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Long incrBy(String key, long integer) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.incrBy(key, integer);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Long incr(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.incr(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public List<String> lrange(String key,int start,int end){
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.lrange(key, start, end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public String lpop(String key){
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.lpop(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Long lpush(String key,String ...strings){
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.lpush(key,strings);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String type(String key) {
	ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.type(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.expireAt(key,unixTime);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.setbit(key,offset,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.setbit(key,offset,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Boolean getbit(String key, long offset) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.getbit(key,offset);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.setrange(key,offset, value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.getrange(key,startOffset, endOffset);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String getSet(String key, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.getSet(key,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long setnx(String key, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.setnx(key,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String setex(String key, int seconds, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.setex(key,seconds,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long decrBy(String key, long integer) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.decrBy(key,integer);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long decr(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.decr(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long append(String key, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.append(key,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String substr(String key, int start, int end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.substr(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Long hset(String key, String field, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hset(key,field,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String hget(String key, String field) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hget(key,field);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hsetnx(key,field,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hmset(key,hash);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hmget(key,fields);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hincrBy(key,field,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Boolean hexists(String key, String field) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hexists(key,field);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long hdel(String key, String... field) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hdel(key,field);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long hlen(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hlen(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<String> hkeys(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hkeys(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<String> hvals(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hvals(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hgetAll(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long rpush(String key, String... string) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.rpush(key,string);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long llen(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.llen(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.lrange(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String ltrim(String key, long start, long end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.ltrim(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String lindex(String key, long index) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.lindex(key,index);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String lset(String key, long index, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.lset(key,index,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long lrem(String key, long count, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.lrem(key,count,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String rpop(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.rpop(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long sadd(String key, String... member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sadd(key,member);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<String> smembers(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.smembers(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long srem(String key, String... member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.srem(key,member);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String spop(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.spop(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long scard(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.scard(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Boolean sismember(String key, String member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sismember(key,member);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String srandmember(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.srandmember(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Long strlen(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.strlen(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zadd(String key, double score, String member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zadd(key,score,member);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zadd(key,scoreMembers);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrange(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Long zrem(String key, String... member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrem(key,member);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zincrby(key,score,member);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zrank(String key, String member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrank(key,member);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zrevrank(String key, String member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrank(key,member);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrange(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrangeWithScores(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			};
		}
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeWithScores(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zcard(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zcard(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Double zscore(String key, String member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zscore(key,member);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<String> sort(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sort(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sort(key,sortingParameters);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zcount(String key, double min, double max) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zcount(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zcount(String key, String min, String max) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zcount(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrangeByScore(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrangeByScore(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeByScore(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrangeByScore(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeByScore(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max,
			int offset, int count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrangeByScore(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min,
			int offset, int count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeByScore(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrangeByScoreWithScores(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeByScoreWithScores(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min,
			double max, int offset, int count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrangeByScoreWithScores(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min,
			int offset, int count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeByScore(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrangeByScoreWithScores(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeByScoreWithScores(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min,
			String max, int offset, int count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrangeByScoreWithScores(key,min,max);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min, int offset, int count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeByScoreWithScores(key,min,max,offset,count);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min, int offset, int count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeByScoreWithScores(key,min,max,offset,count);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zremrangeByRank(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zremrangeByScore(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zremrangeByScore(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.linsert(key,where,pivot,value);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Long lpushx(String key, String... string) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.lpushx(key,string);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long rpushx(String key, String... string) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.rpushx(key,string);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public List<String> blpop(String arg) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.blpop(arg);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<String> brpop(String arg) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.brpop(arg);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Long del(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.del(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public String echo(String string) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.echo(string);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
		
	}

	@Override
	public Long move(String key, int dbIndex) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.move(key,dbIndex);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long bitcount(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.bitcount(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public Long bitcount(String key, long start, long end) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.bitcount(key,start,end);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
		
	}

	@Override
	@Deprecated
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hscan(key,cursor);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	@Deprecated
	public ScanResult<String> sscan(String key, int cursor) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sscan(key,cursor);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	@Deprecated
	public ScanResult<Tuple> zscan(String key, int cursor) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zscan(key,cursor);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hscan(key,cursor);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sscan(key,cursor);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zscan(key,cursor);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long pfadd(String key, String... elements) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.pfadd(key,elements);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}

	@Override
	public long pfcount(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.pfadd(key);
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	/*public Set<String> keys() {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			
			return null;
		}finally{
			jedis.close();
		}
	}*/
	
	public ShardedJedis getJedisResource() {
		return pool.getResource();
	}
	
	public void returnJedisResource(ShardedJedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	@Override
	public Long bitpos(String key, boolean value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.bitpos(key, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long bitpos(String key, boolean value, BitPosParams params) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.bitpos(key, value, params);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<String> blpop(int timeout, String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.blpop(timeout, key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<String> brpop(int timeout, String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.brpop(timeout, key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.geoadd(key, memberCoordinateMap);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long geoadd(String key, double longitude, double latitude, String member) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.geoadd(key, longitude, latitude, member);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Double geodist(String key, String member1, String member2) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.geodist(key, member1, member2);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Double geodist(String key, String member1, String member2, GeoUnit unit) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.geodist(key, member1, member2, unit);
		} finally {
			if (jedis != null) {
				if (jedis != null) {
					jedis.close();
				}
			}
		}
	}

	@Override
	public List<String> geohash(String key, String... members) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.geohash(key, members);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<GeoCoordinate> geopos(String key, String... members) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.geopos(key, members);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude,
			double latitude, double radius, GeoUnit unit) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.georadius(key, longitude, latitude, radius, unit);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude,
			double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.georadius(key, longitude, latitude, radius, unit, param);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member,
			double radius, GeoUnit unit) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.georadiusByMember(key, member, radius, unit);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member,
			double radius, GeoUnit unit, GeoRadiusParam param) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.georadiusByMember(key, member, radius, unit, param);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Double hincrByFloat(String key, String field, double value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hincrByFloat(key, field, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor,
			ScanParams params) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.hscan(key, cursor, params);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Double incrByFloat(String key, double integer) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.incrByFloat(key, integer);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long pexpire(String key, long milliseconds) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.pexpire(key, milliseconds);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.pexpireAt(key, millisecondsTimestamp);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String psetex(String key, long milliseconds, String value) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.psetex(key, milliseconds, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long pttl(String key) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.pttl(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public String set(String key, String value, String nxxx) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.set(key, value, nxxx);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<String> spop(String key, long count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.spop(key, count);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<String> srandmember(String key, int count) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.srandmember(key, count);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sscan(key, cursor, params);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
		ShardedJedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zadd(key, scoreMembers, params);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Long zadd(String arg0, double arg1, String arg2, ZAddParams arg3) {
		return null;
	}

	@Override
	public Double zincrby(String arg0, double arg1, String arg2,
			ZIncrByParams arg3) {
		return null;
	}

	@Override
	public Long zlexcount(String arg0, String arg1, String arg2) {
		return null;
	}

	@Override
	public Set<String> zrangeByLex(String arg0, String arg1, String arg2) {
		return null;
	}

	@Override
	public Set<String> zrangeByLex(String arg0, String arg1, String arg2,
			int arg3, int arg4) {
		return null;
	}

	@Override
	public Long zremrangeByLex(String arg0, String arg1, String arg2) {
		return null;
	}

	@Override
	public Set<String> zrevrangeByLex(String arg0, String arg1, String arg2) {
		return null;
	}

	@Override
	public Set<String> zrevrangeByLex(String arg0, String arg1, String arg2,
			int arg3, int arg4) {
		return null;
	}

	@Override
	public ScanResult<Tuple> zscan(String arg0, String arg1, ScanParams arg2) {
		return null;
	}

}

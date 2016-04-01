package com.bj58.socialsystem.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class IdGenerator {
	
	
	List<Pair<JedisPool, String>> jedisPoolList;
	
	int retryTimes;
	
	int index = 0;
	
	private IdGenerator() {
		
	}
	
	private IdGenerator(List<Pair<JedisPool, String>> jedisPoolList, int retryTimes) {
		this.jedisPoolList = jedisPoolList;
		this.retryTimes = retryTimes;
	}
	
	static public IdGeneratorBuilder builder() {
		return new IdGeneratorBuilder();
	}
	
	static class IdGeneratorBuilder {
		List<Pair<JedisPool, String>> jedisPoolList = new ArrayList<Pair<JedisPool, String>>();
		
		int retryTimes = 5;
		
		public IdGeneratorBuilder addHost(String host, int port, String luaSha) {
			jedisPoolList.add(Pair.of(new JedisPool(host, port), luaSha));
			return this;
		}
		
		public IdGeneratorBuilder retryTimes(int retryTimes) {
			this.retryTimes = retryTimes;
			return this;
		}
		
		public IdGenerator build() {
			return new IdGenerator(jedisPoolList, retryTimes);
		}
	}
	
	public long next(String tab) {
		return next(tab, 0);
	}
	
	public long next(String tab, long shardId) {
		for(int i = 0; i < retryTimes; ++i) {
			Long id = innerNext(tab, shardId);
			if(id != null) {
				return id;
			}
		}
		throw new RuntimeException("Can not generate id!");
	}
	
	Long innerNext(String tab, long shardId) {
		index ++;
		Pair<JedisPool, String> pair = jedisPoolList.get(index % jedisPoolList.size());
		JedisPool jedisPool = pair.getLeft();
		
		String luaSha = pair.getRight();
		Jedis jedis = null;
		
		try {
			jedis = jedisPool.getResource();
			List<Long> result = (List<Long>) jedis.evalsha(luaSha, 2, tab, "" + shardId);
			return buildId(result.get(0), result.get(1), result.get(2), result.get(3));
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if(jedis != null) {
				jedisPool.returnResource(jedis);
			}
		}
		
		return null;
	}
	
	public static Long buildId(long second, long mincroSecond, long shardId, long seq) {
		long milliSecond = (second * 1000 + mincroSecond / 1000);
		return (milliSecond << (12 + 10)) + (shardId << 10) + seq;
	}
	
	public static List<Long> parseId(long id) {
		long miliSecond = id >>> 32;
		long shardId = (id & (0xFFF << 10)) >> 10;
		long seq = id & 0x3FF;
		
		List<Long> re = new ArrayList<Long>(4);
		re.add(miliSecond);
		re.add(shardId);
		re.add(seq);
		return re;
	}
	
	public static void main(String[] args) {
		String tab = "order";
		long userid = 123456789;
		
		IdGenerator generator = IdGenerator.builder().addHost("192.168.119.147", 6379, "fce3758b2e0af6cbf8fea4d42b379cd0dc374418").build();
		
		long id = generator.next(tab, userid);
		List<Long> result = IdGenerator.parseId(id);
		System.out.println("miliSeconds:" + result.get(0) + ", partition:"
				+ result.get(1) + ", seq:" + result.get(2));
	} 
}

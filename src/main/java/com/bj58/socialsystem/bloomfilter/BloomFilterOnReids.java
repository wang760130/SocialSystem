package com.bj58.socialsystem.bloomfilter;

import java.util.List;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;
import com.bj58.socialsystem.utils.HashUtils;

/**
 * BloomFilter based on redis
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月23日
 */
public class BloomFilterOnReids {
	
	private int maxKey;
	private float errorRate;
	private int hashFunctionCount;
	private int bitSize;
	
	private static JRedisClient redisClient = JRedisProvider.getInstance().redis;

	public BloomFilterOnReids(float errorRate, int maxKey) {
		this.maxKey = maxKey;
		this.errorRate = errorRate;
		
		bitSize = calcOptimalM(maxKey, errorRate);
		hashFunctionCount = calcOptimalK(bitSize, maxKey);
	}
	
	public void add(String key, String value) {
		int hashCode = value.hashCode();
		int[] offset = HashUtils.murmurHashOffset(hashCode, hashFunctionCount, bitSize);
		for(int i : offset) {
			redisClient.setbit(key, i, true);
		}
	}
	
	public void batchAdd(String key, String[] values) {
		
		ShardedJedis jedis = null;
		try {
			jedis = redisClient.getJedisResource();
			ShardedJedisPipeline pipeline = redisClient.getJedisResource().pipelined();
			
			for(String value : values) {
				int hashCode = value.hashCode();
				int [] offset = HashUtils.murmurHashOffset(hashCode, hashFunctionCount, bitSize);
				for(int i : offset) {
					pipeline.setbit(key, i, true);
				}
			}
			
			pipeline.sync();
		} finally {
			redisClient.returnJedisResource(jedis);
		}
	}
	
	public boolean isExit(String key, String value) {
		int hashCode = value.hashCode();
		int[] offset = HashUtils.murmurHashOffset(hashCode, hashFunctionCount, bitSize);
        for(int i : offset) {
        	if(!redisClient.getbit(key, i)) {
        		return false;
        	}
        }
        return true;
        
	}
	
	public long count(String key) {
		return redisClient.bitcount(key);
	}
	
	public String getRedisData(String key) {
		return redisClient.get(key);
	}

	public Long delKey(String key) {
		return redisClient.del(key);
	}
	
	public int calcOptimalM(int maxKey, float errorRate) {
		return (int) (Math.ceil(maxKey) * (Math.log(errorRate) / Math.log(0.6185)));
	}
	
	public int calcOptimalK(int bitSize, int maxKey) {
		return (int)Math.ceil(Math.log(2) * (bitSize / maxKey));
	}

	public int getMaxKey() {
		return maxKey;
	}

	public void setMaxKey(int maxKey) {
		this.maxKey = maxKey;
	}

	public float getErrorRate() {
		return errorRate;
	}

	public void setErrorRate(float errorRate) {
		this.errorRate = errorRate;
	}

	public int getHashFunctionCount() {
		return hashFunctionCount;
	}

	public void setHashFunctionCount(int hashFunctionCount) {
		this.hashFunctionCount = hashFunctionCount;
	}

	public int getBitSize() {
		return bitSize;
	}

	public void setBitSize(int bitSize) {
		this.bitSize = bitSize;
	}
	
	
}

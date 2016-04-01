package com.bj58.socialsystem.demo;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

import com.bj58.socialsystem.utils.StopWatch;


//-- 函数：尝试获得红包，如果成功，则返回json字符串，如果不成功，则返回空  
//-- 参数：红包队列名， 已消费的队列名，去重的Map名，用户ID  
//-- 返回值：nil 或者 json字符串，包含用户ID：userId，红包ID：id，红包金额：money  
//  
//-- 如果用户已抢过红包，则返回nil  
//if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then  
//  return nil  
//else  
//  -- 先取出一个小红包  
//  local hongBao = redis.call('rpop', KEYS[1]);  
//  if hongBao then  
//    local x = cjson.decode(hongBao);  
//    -- 加入用户ID信息  
//    x['userId'] = KEYS[4];  
//    local re = cjson.encode(x);  
//    -- 把用户ID放到去重的set里  
//    redis.call('hset', KEYS[3], KEYS[4], KEYS[4]);  
//    -- 把红包放到已消费队列里  
//    redis.call('lpush', KEYS[2], re);  
//    return re;  
//  end  
//end  
//return nil

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月22日
 */
public class RedisLua {
	
	static String host = "192.168.119.147";
	
	static int hongBaoCount = 100000;
	static int threadCount = 20;
	
	static String hongBaoListKey = "hongBaoList";
	static String hongBaoConsumedListKey = "hongBaoConsumeedList";
	static String hongBaoConsumedMapKey = "hongBaoConsumedMap";
	
	static Random random = new Random();
	static StopWatch watch = new StopWatch();
	
	static String tryGetHongBaoScript =   
//          "local bConsumed = redis.call('hexists', KEYS[3], KEYS[4]);\n"  
//          + "print('bConsumed:' ,bConsumed);\n"  
            "if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then\n"  
            + "return nil\n"  
            + "else\n"  
            + "local hongBao = redis.call('rpop', KEYS[1]);\n"  
//          + "print('hongBao:', hongBao);\n"  
            + "if hongBao then\n"  
            + "local x = cjson.decode(hongBao);\n"  
            + "x['userId'] = KEYS[4];\n"  
            + "local re = cjson.encode(x);\n"  
            + "redis.call('hset', KEYS[3], KEYS[4], KEYS[4]);\n"  
            + "redis.call('lpush', KEYS[2], re);\n"  
            + "return re;\n"  
            + "end\n"  
            + "end\n"  
            + "return nil"; 
	
	static public void generateTestData() throws InterruptedException {
		Jedis jedis = new Jedis(host);
		final CountDownLatch latch = new CountDownLatch(threadCount);
		for(int i = 0; i < threadCount; i++) {
			final int temp = i;
			Thread thread = new Thread () {
				@Override
				public void run () {
					Jedis jedis = new Jedis(host);
					int per = hongBaoCount / threadCount;
					JSONObject object = new JSONObject();
					for(int j = temp * per; j < (temp + 1) * per; j++) {
						object.put("id", j);
						object.put("money", j);
						jedis.lpush(hongBaoListKey, object.toString());
					}
					latch.countDown();
				}
			};
			thread.start();	
		}
		latch.await();
	}
	
	static public void testTryGetHongBao() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch (threadCount);
		System.err.println("start:" + System.currentTimeMillis()/1000);  
		
		watch.start();
		for(int i = 0; i < threadCount; ++i) {
			final int temp = i;
			Thread thread = new Thread() {
				@Override
				public void run () {
					Jedis jedis = new Jedis(host);
					String sha = jedis.scriptLoad(tryGetHongBaoScript);
					int j = hongBaoCount / threadCount * temp;
					while(true) {
						Object object = jedis.eval(tryGetHongBaoScript, 4, hongBaoListKey,hongBaoConsumedListKey, hongBaoConsumedMapKey, "" + j);
						j ++;
						if(object != null) {
							System.out.println("get hongBao:" + object);  
						} else {
							if(jedis.llen(hongBaoListKey) == 0) {
								break;
							}
						}
					}
					latch.countDown();
				}
			};
			thread.start();
		}
		latch.await();
		watch.stop();
		
		System.out.println("time:" + watch.getTotalTimeSeconds());  
        System.out.println("speed:" + hongBaoCount / watch.getTotalTimeSeconds());  
        System.out.println("end:" + System.currentTimeMillis()/1000);  
	}
	
	public static void main(String[] args) throws InterruptedException {  
//        generateTestData();  
        testTryGetHongBao();  
    }  
}

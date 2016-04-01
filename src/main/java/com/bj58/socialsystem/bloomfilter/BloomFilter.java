package com.bj58.socialsystem.bloomfilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.BitSet;

/**
 * BloomFilter based on java
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月21日
 */
public class BloomFilter {
	
	//布隆过滤器的比特长度  
	private static final int DEFAULT_SIZE = 2 << 24;
	
	/*
	 * 这里要选取质数，能很好的降低错误率 
	 * 不同哈希函数的种子，一般取质数 
	 * seeds数组共有8个值，则代表采用8种不同的哈希函数 
	 */
	private static final int[] seeds = {3, 5, 7, 11, 13, 31, 37, 61};
	
	/* 
     * 初始化一个给定大小的位集 
     * BitSet实际是由“二进制位”构成的一个Vector。 
     * 假如希望高效率地保存大量“开－关”信息，就应使用BitSet. 
     */  
	private static BitSet bits = new BitSet(DEFAULT_SIZE);
	
	//构建hash函数对象
	private static SimpleHash[] hashFuns = new SimpleHash[seeds.length];
	
	/**
	 * 将字符串value哈希为8个或多个整数，然后在这些整数的bit上变为1  
	 * @param value
	 */
	public synchronized void add(String value) {
		if(value != null) {
			for(SimpleHash f : hashFuns) {
				bits.set(f.hash(value), true);
			}
		}
	}
	
	public synchronized void delete(String value) {
		if(value != null) {
			for(SimpleHash f : hashFuns) {
				bits.set(f.hash(value), false);
			}
		}
	}
	
	
	/**
	 * 判断给定的字符串是否已经存在在bloofilter中，如果存在返回true，不存在返回false 
	 * @param value
	 * @return
	 */
	public synchronized boolean isExit(String value) {
		if(value == null) {
			return false;
		}
		
		for(SimpleHash hashFun : hashFuns) {
			if(!bits.get(hashFun.hash(value))) {
				//如果判断8个hash函数值中有一个位置不存在即可判断为不存在Bloofilter中  
				return false;
			}
		}
		
		return true;
	}
	
	public void init(String path) {
		/** 
         *  给出所有的hash值，共计seeds.length个hash值。共8位。 
         *  通过调用SimpleHash.hash(),可以得到根据8种hash函数计算得出hash值。   
         *  传入DEFAULT_SIZE(最终字符串的长度），seeds[i](一个指定的质数)即可得到需要的那个hash值的位置。         
         */  
		for(int i = 0; i < seeds.length; i++) {
			hashFuns[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
		}
		
		
		InputStream is = null;
		BufferedReader read = null;
		File file = new File(path);
		
		try {
			is = new FileInputStream(file);
			read = new BufferedReader(new InputStreamReader(is));
			
			String word = null;
			while((word = read.readLine()) != null) {
				if(word != null && word.trim().equals("")) {
					add(word);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(read != null) {
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}

class SimpleHash {
	
	// cap为DEFAULT_SIZE，即用于结果的最大字符串的值
	private int cap;
	// seed为计算hash值的一个key值，具体对应上文中的seeds数组 
	private int seed;
	
	public SimpleHash(int cap, int seed) {
		this.cap = cap;
		this.seed = seed;
	}
	
	public int hash(String value) {
		int result = 0;
		int len = value.length();
		
		for(int i = 0; i < len; i++) {
			result = seed * result + value.charAt(i);
		}
		return (cap - 1) & result;
	}
}

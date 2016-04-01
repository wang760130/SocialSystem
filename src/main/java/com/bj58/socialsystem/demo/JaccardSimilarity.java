package com.bj58.socialsystem.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 利用jaccard similarity 计算文本相似度
 * http://blog.csdn.net/ygrx/article/details/12748857
 * https://github.com/wyh267/myCodeLib/blob/master/Src/textDiff/NewJaccardSimilarity.py
 * https://github.com/wyh267/myCodeLib/blob/master/Src/textDiff/JaccardSimilarity.py
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月18日
 */
public class JaccardSimilarity {
	
	private static final int K_SHINGLE = 5;
	
	public String readFile(String fileName) {
		
		InputStream is = null;
		BufferedReader read  = null;
		File file = new File(fileName);
		
		String content = "";
		try {
			is = new FileInputStream(file);
			read = new BufferedReader(new InputStreamReader(is));
			
			String temp = null;
			while((temp = read.readLine()) != null) {
				content = content + temp;
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
		
		return content;
	}
	
	/**
	 * 分割字符串，使用k-shingle方式进行分割
	 * @param content 分割好的字符串，存入数组中
	 */
	public String[] splitContents(String content) {
		String[] contents = new String[]{};
		for(int i = 0; i < contents.length - K_SHINGLE; i++) {
			contents[i] = content.substring(i, i + K_SHINGLE);
		}
		return contents;
	}
	
	/**
	 * 将数据保存到hash表中，也就是某个集合
	 * @param contents 已经分隔好的数据
	 * @return hash表
	 */
	public Map<String, Integer> hashContentsList(String[] contents) {
		Map<String, Integer> hashContent = new HashMap<String, Integer>();
		
		for(String content : contents) {
			if(hashContent.containsKey(content)) {
				int count = hashContent.get(content);
				hashContent.put(content, count + 1);
			} else {
				hashContent.put(content, 1);
			}
		}
		
		return hashContent;
	}
	
	/**
	 * 计算交集
	 * @param hashContent_A 两个hash表
	 * @param hashContent_B 交集的整数
	 * @return
	 */
	public int calcIntersection(Map<String, Integer> hashContent_A, Map<String, Integer> hashContent_B) {
		int intersection = 0;
		
		Map<String, Integer> minHashContent = null;
		Map<String, Integer> maxHashContent = null;
		
		if(hashContent_A.size() <= hashContent_B.size()) {
			minHashContent = hashContent_A;
			maxHashContent = hashContent_B;
		} else {
			minHashContent = hashContent_B;
			maxHashContent = hashContent_A;
		}
		
		for(Entry<String, Integer> minEntery : minHashContent.entrySet()) {
			for(Entry<String, Integer> maxEntry : maxHashContent.entrySet()) {
				if(minEntery.getValue() <= maxEntry.getValue()) {
					intersection = intersection + minEntery.getValue();
				} else {
					intersection = intersection + maxEntry.getValue();
				}
			}
		}
		
		return intersection;
 	}
	
	/**
	 * 计算并集
	 * @param hashContent_A
	 * @param hashContent_B
	 * @param intersection 
	 * @return 并集的整数
	 */
	public int calcUnionSet(Map<String, Integer> hashContent_A, Map<String, Integer> hashContent_B, int intersection) {
		int union = 0;
		
		for(Entry<String, Integer> entry : hashContent_A.entrySet()) {
			union = union + entry.getValue();
		}
		
		for(Entry<String, Integer> entry : hashContent_B.entrySet()) {
			union = union + entry.getValue();
		}

		return union - intersection;
	}
	
	/**
	 * 计算相似度
	 * @param intersection
	 * @param union
	 * @return
	 */
	public float calcSimilarity(int intersection, int union) {
		if(union > 0) {
			return (float) intersection / (float) union; 
		}
		return 0.0f;
	}
	
	/**
	 * 从某个文本文件获取一个集合，该集合保存了文本中单词的出现频率
	 * @param fileName 文件名
	 * @return 一个词频的hash表
	 */
	public Map<String, Integer> getHashInfoFromFile(String fileName) {
		String content = this.readFile(fileName);
		String[] contents = this.splitContents(content);
		return this.hashContentsList(contents);
	}
	
	public static void main(String[] args) {
		
	}
} 

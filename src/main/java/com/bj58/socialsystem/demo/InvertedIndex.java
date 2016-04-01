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

/**
 * Java实现倒排索引
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月18日
 */
public class InvertedIndex {
	
	/**
	 * 	key:is , line : 5 , count :1
		key:Shanghai , line : 2 , count :2
		key:Shanghai , line : 4 , count :1
		key:beautiful , line : 5 , count :1
		key:I , line : 1 , count :1
		key:I , line : 2 , count :2
		key:I , line : 3 , count :1
		key:in , line : 2 , count :1
		key:in , line : 4 , count :1
		key:and , line : 2 , count :1
		key:love , line : 2 , count :1
		key:love , line : 3 , count :1
		key:life , line : 4 , count :1
		key:eriol , line : 1 , count :1
		key:travelling , line : 3 , count :1
		key:am , line : 1 , count :1
		key:also , line : 3 , count :1
		key:live , line : 2 , count :1
	 */
	public static Map<String, Map<Integer, Integer>> createIndex(String filePath) {
		
		Map<String, Map<Integer, Integer>> index = new HashMap<String, Map<Integer, Integer>>();
		
		Map<Integer, Integer> subIndex = null;
		
		InputStream is = null;
		BufferedReader read  = null;
		File file = new File(filePath);
		try {
			is = new FileInputStream(file);
			read = new BufferedReader(new InputStreamReader(is));
			
			String temp = null;
			int line = 1;
			while((temp = read.readLine()) != null) {
				String[] words = temp.split(" ");
				for(String word : words) {
					if(!index.containsKey(word)) {
						subIndex = new HashMap<Integer, Integer>();
						subIndex.put(line, 1);
						index.put(word, subIndex);
					} else {
						subIndex = index.get(word);
						if(subIndex.containsKey(line)) {
							int count = subIndex.get(line);
							subIndex.put(line, count + 1);
						} else {
							subIndex.put(line, 1);
						}
					}
				}
				line ++;
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
		
		return index;
	}
	
	public static void printIndex(Map<String, Map<Integer, Integer>> index) {
		for(Map.Entry<String, Map<Integer, Integer>> indexEntry : index.entrySet()) {
			String indexKey = indexEntry.getKey();
			
			Map<Integer, Integer> subIndex = index.get(indexKey);
			for(Map.Entry<Integer, Integer> sub : subIndex.entrySet()) {
				System.out.println("key:" + indexKey + " , line : " + sub.getKey() + " , count :" + sub.getValue());
			}
		}
	}
	
	public static void find(Map<String, Map<Integer, Integer>> index, String str) {
		String[] words = str.split(" ");
		for(String word : words) {
			StringBuilder sb = new StringBuilder();
			if(index.containsKey(word)) {
				sb.append("word:" + word + " in ");
				Map<Integer, Integer> temp = index.get(word);
				for(Map.Entry<Integer, Integer> e : temp.entrySet()) {
					sb.append(" line " + e.getKey() + "[" + e.getValue() + "]");
				}
			} else {
				sb.append("word:" + word + "not found");
			}
			System.out.println(sb);
		}
	}
	
	public static void main(String[] args) {
		Map<String, Map<Integer, Integer>> index = InvertedIndex.createIndex("D:\\My Workspace\\SocialSystem\\text\\InvertedIndex.txt");
		/**
		 *  word:I in  line 1[1] line 2[2] line 3[1]
			word:love in  line 2[1] line 3[1]
			word:Shanghai in  line 2[2] line 4[1]
			word:todaynot found
		 */
		InvertedIndex.find(index, "I love Shanghai today");
//		InvertedIndex.printIndex(index);
	}
}

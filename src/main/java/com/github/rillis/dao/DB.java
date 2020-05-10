package com.github.rillis.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONObject;

public class DB {
	private File f = null;
	
	public DB(String name) {
		File temp = new File(name);
		
		if(exists(temp)) {
			f=temp;
		}else {
			f=create(temp);
			write("{}");
		}
		
	}
	
	private boolean exists(File f) {
		return f.exists();
	}
	
	private File create(File f) {
			if(!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			if(!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return f;
	}
	
	private void write(String text) {
	    try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(f, false));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String read(File file) {
		try {
		BufferedReader br = new BufferedReader(new FileReader(file));
		    String everything = "";
		    String line = br.readLine();
		    while (line != null) {
		        if(!everything.equals("")) {
		        	everything+="\n";
		        }
		        everything+=line;
		        line = br.readLine();
		    }
		    br.close();
		return everything;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private JSONObject getJSON() {
		return new JSONObject(read(f));
	}
	
	
	public void set(String key, Object value) {
		JSONObject json = getJSON();
		json.put(key, value);
		write(json.toString());
	}
	
	public Object get(String key) {
		JSONObject json = getJSON();
		return json.get(key);
	}
	
	public void remove(String key) {
		JSONObject json = getJSON();
		json.remove(key);
		write(json.toString());
	}
	
	public boolean has(String key) {
		JSONObject json = getJSON();
		return json.has(key);
	}
	
	public Iterator<String> getKeys() {
		JSONObject json = getJSON();
		return json.keys();
	}
	
	
}

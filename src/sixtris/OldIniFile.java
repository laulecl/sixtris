/*
 * OldIniFile.java
 * 
 * Insipré de 
 * 
 * ConfigurationFile.java
 *
 * Created on 28 juillet 2006, 10:01
 *
 * Author: Sebastien HERTZ (sebastien.hertz@gmail.com)
 *
 * Author : Laurent LECLUSE (laurent.lecluse@gmail.com)
 */

package sixtris;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author laurent
 */
public class OldIniFile {
	private static final String END_OF_FILE = null;
	private File m_file; // handler of the file
	private String m_commentString; // prefix for comment
	private Hashtable<String, Hashtable<String, String>> m_table; // table of the sections/keys/values
	private boolean m_upToDate;
	
	
	
	
	
	/**
	 * Creates a new instance of ConfigurationFile
	 *
	 * @param filePath : name of the file
	 */
	public OldIniFile(String filePath) throws Exception{
		m_table = new Hashtable<String, Hashtable<String, String>>();
		m_upToDate = true;
		m_file = new File(filePath);
		m_commentString = "#";
		if( ! m_file.exists() ){
			throw new IOException("this file does not exist.");
		}

		if( ! m_file.isFile() ){
			throw new IOException("this file is not a file.");
		}

		// constructs the table
		loadFile();

	}

	
	
	public OldIniFile(File f) throws Exception{
		m_table = new Hashtable<String, Hashtable<String, String>>();
		m_upToDate = true;
		m_file = f;
		m_commentString = "#";

		if( ! m_file.exists() ){
			throw new IOException("this file does not exist.");
		}

		if( ! m_file.isFile() ){
			throw new IOException("this file is not a file.");
		}

		// constructs the table
		loadFile();

	}



	/**
	 * Set the comment prefix
	 *
	 * @param commentPrefix : new comment prefix
	 */
	public void setCommentString(String commentPrefix){
		m_commentString = commentPrefix;
	}



	/**
	 * Load the table of sections/keys/values from the configuration file
	 */
	public void loadFile() throws Exception{
		if( ! m_file.canRead() ){
			throw new Exception("this file cannot be read.");
		}

		BufferedReader readBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(m_file)));

		String line = null;
		String currentSection = null;

		try{
			while((line = readBuffer.readLine()) != END_OF_FILE){
				if(!line.startsWith(m_commentString)){
					if(line.startsWith("[")){ // section
						if(!line.endsWith("]")){
							throw new Exception("Invalid format: data found outside section");
						}else{
							currentSection = line.trim().substring(1, line.length()-1);
						}
					}else{ // data
						line = line.trim();
						int pos = -1;
						if((pos = line.indexOf('=')) != -1 && currentSection != null){
							String key = line.substring(0, pos);
							String value = line.substring(pos+1,line.length());
							putProperty(currentSection, key, value);
						}
					}
				}
			}
		}catch (IOException ex){
			ex.printStackTrace();
		}
		readBuffer.close();
	}

	
	
	/**
	 * Save the table to the file
	 */
	public void saveFile() throws Exception{
		if(!m_file.canWrite()){
			throw new Exception("this file cannot be written.");
		}

		BufferedWriter writeBuffer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(m_file)));
		Enumeration<String> sections = m_table.keys();
		while(sections.hasMoreElements()){
			String section = sections.nextElement();
			writeBuffer.write("["+section+"]");
			writeBuffer.newLine();
			Enumeration<String> keys = m_table.get(section).keys();
			while(keys.hasMoreElements()){
				String key = keys.nextElement();
				String value = m_table.get(section).get(key);

				writeBuffer.write(key+"="+value);
				writeBuffer.newLine();
			}
		}
		writeBuffer.close();
	}



	/**
	 * Retrieves the value of the associated section/key
	 */
	public String getProperty(String section, String key){
		Hashtable<String, String> keys = m_table.get(section);
		if(section != null && keys != null){
			return keys.get(key);
		}else{
			return null;
		}
	}



	/**
	 * Puts a new property in the table
	 *
	 * @param section : section of the key
	 * @param key : key of the value
	 * @param value : value to put in the table
	 */
	public void putProperty(String section, String key, String value){
		if(!sectionExists(section)){ // we add the new section
			m_table.put(section, new Hashtable<String, String>());
		}

		Hashtable<String, String> keys = m_table.get(section);
		keys.put(key, value);
	}



	/**
	 * Determines if a section exists
	 *
	 * @param section : name of the section
	 */
	public boolean sectionExists(String section){
		return m_table.containsKey(section);
	}



	/**
	 * Determines if a key exists
	 *
	 * @param key name of the key
	 */
	public boolean keyExists(String key){
		boolean result = false;
		Enumeration<Hashtable<String, String>> elements = m_table.elements();
		while(elements.hasMoreElements() && !result){
			Hashtable<String, String> keys = elements.nextElement();
			result = keys.containsKey(key);
		}
		return result;
	}



	/**
	 * Determines if a key exists in the section
	 *
	 * @param section : name of the section
	 * @param key : name of the key
	 */
	public boolean keyExists(String section, String key){
		if(m_table.containsKey(section)){
			return m_table.get(section).containsKey(key);
		}
		return false;
	}

}

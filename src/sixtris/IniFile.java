/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sixtris;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 *
 * @author laurent
 */
public class IniFile {

	/**
	 * Nom du fichier
	 */
	private String fileName;
	
	/**
	 * Données
	 */
	private HashMap<String,HashMap> data;


	/**
	 * Constructeur par défaut
	 */
	public IniFile(){
		init();
	}



	/**
	 * Constructeur
	 * Charge automatiquement le fichier transmis
	 * 
	 * @param fileName 
	 */
	public IniFile( String fileName ) throws Exception {
		init();
		load(fileName);
	}

	
	
	/**
	 * Initialisation !
	 */
	private void init(){
		data = new HashMap<String,HashMap>();
	}



	/**
	 * Charge le fichier
	 * 
	 * @param fileName
	 * @return boolean
	 */
	public boolean load( String fileName ) throws Exception {
		File file = new File(fileName);
		
		if( ! file.exists() ){
			throw new IOException("Le fichier \""+fileName+"\" n'existe pas.");
		}

		if( ! file.isFile() ){
			throw new IOException("Le chemin donné \""+fileName+"\" ne correspond pas à un fichier.");
		}

		this.fileName = fileName;

		BufferedReader buffer = new BufferedReader(new FileReader(fileName));
		String line;

		data.clear();
		HashMap currentSection = new HashMap<String,String>();
		data.put( null, currentSection );
		
		while ((line = buffer.readLine()) != null){
			line = line.trim();

			if (line.startsWith("[")){
				if (! line.endsWith("]")){
					throw new Exception("Format de fichier incorrect");
				}else{
					currentSection = new HashMap<String,String>();
					data.put( line.substring(1, line.length()-1), currentSection );
				}
			}else if (line.length() > 0 && ! line.startsWith("#")){
				int pos = -1;
				if((pos = line.indexOf('=')) != -1){
					String key = line.substring(0, pos).trim();
					String value = line.substring(pos+1,line.length()).trim();
					currentSection.put( key, value );
					//System.out.println( currentSection+"."+key+" = "+value);
				}
			}
		}
		buffer.close();


		return true;
	}



	/**
	 * Sauvegarde le fichier
	 * 
	 * @return boolean
	 */
	public boolean save( String fileName ){
		/** @todo Implémentation */
		this.fileName = fileName;
		return save();
	}
	
	
	
	/**
	 * Sauvegarde le fichier
	 * @return 
	 */
	public boolean save(){
		/** @todo Implémentation */
		return true;
	}



	/**
	 * Retourne le nom du fichier de configuration
	 * 
	 * @return String
	 */
	public String getFileName(){
		return fileName;
	}
	
	
	
	/**
	 * Retourne une prpriété d'une section
	 * 
	 * @param section
	 * @param key
	 * @return String
	 */
	public String get( String section, String key ){
		
		if (! data.containsKey(section)){
			return null;
		}else{
			HashMap hs = data.get(section);
			return (String)hs.get(key);
		}
	}



	/**
	 * Retourne une propriété globale
	 * 
	 * @param key
	 * @return String
	 */
	public String get( String key ){
		return get( null, key );
	}
	
	
	
	/**
	 * Définie une propriété dans une section
	 * 
	 * @param section
	 * @param key
	 * @param value 
	 */
	public void set( String section, String key, String value ){
		HashMap hs;
		if (! data.containsKey(section)){
			hs = new HashMap<String,String>();
			data.put(section, hs);
		}else{
			hs = data.get(section);
		}
		hs.put(key, value);
	}
	
	
	
	/**
	 * Définie une propriété globale
	 *
	 * @param key
	 * @param value 
	 */
	public void set( String key, String value ){
		set( null, key, value );
	}
	
	
	
	/**
	 * Retourne true si la clé existe dans la section donnée, sinon false
	 * 
	 * @param section
	 * @param key
	 * @return boolean
	 */
	public boolean isset( String section, String key ){
		HashMap hs;
		if (! data.containsKey(section)){
			return false;
		}else{
			hs = data.get(section);
			return hs.containsKey(key);
		}
	}



	/**
	 * Retourne true si la clé globale donnée existe, sinon false
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean isset( String key ){
		return isset( null, key);
	}
	
	
	
	/**
	 * Retourne true si la section donnée existe, sinon false
	 */
	public boolean sectionIsset( String section ){
		return data.containsKey(section);
	}
}

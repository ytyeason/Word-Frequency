import java.util.*;
import java.io.*;

public class DocumentFrequency {
  
  public static void main(String[] args) {
    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    dfs = extractDocumentFrequencies(dir, 40);
   writeDocumentFrequencies(dfs, "freqs.txt");
  }
  
  public static HashMap<String, Integer> extractDocumentFrequencies(String pass, int nDocs) {

    HashMap<String, Integer> search = new HashMap<String, Integer>();
    int frequency = 0;//overall frequency
    String check;//store the selected word
    ArrayList<String> dic = new ArrayList<String>();
    
    for(int i = 1; i<= nDocs; i++){
      for(String p : extractWordsFromDocument(pass+"/"+i+".txt")){
        dic.add(p);
      }
    }
    String value;
    for(int i = 0; i< dic.size(); i++){
      value = dic.get(i);
          for(int p = 0; p< dic.size(); p++){
            if((dic.get(p)).equals(dic.get(i))){
              frequency = frequency +1;
            }
          }
          search.put(value,frequency);
      frequency = 0;
    }
    return search;
  }
  
  public static HashSet<String> extractWordsFromDocument(String name){
    HashSet<String> elements = new HashSet<String>();
    try{
      FileReader fr = new FileReader(name);
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();
      String doc = line+" ";
      while(line != null){
        line = br.readLine();
        if(line != null)
          doc = doc + line+" ";
      }
      br.close();
      fr.close();
      
      String nor = normalize(doc);
      String[] parts = nor.split(" ");
      for(int i = 0; i < parts.length; i++){
          elements.add(parts[i]);
      }
      elements.remove("");
    }catch(IOException e){
      System.out.println("io error");
    }
    return elements;
  }
  
  
  public static void writeDocumentFrequencies(HashMap<String, Integer> dfs, String filename) {
    String content;
    try{
      FileWriter fw = new FileWriter(filename);
      BufferedWriter bw = new BufferedWriter(fw);
      
      List<String> list = new ArrayList<String>(dfs.keySet());
      Collections.sort(list);
      for(String key : list){
        content = key+" "+dfs.get(key);
        bw.write(content+"\r\n");//should specified in confession
      }
      bw.close();
      fw.close();
    }catch(IOException e){
      System.out.println("io error");
    }
  }
  
  /*
   * This method "normalizes" a word, stripping extra whitespace and punctuation.
   * Do not modify.
   */
  public static String normalize(String word) {
    return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
  }
  
}
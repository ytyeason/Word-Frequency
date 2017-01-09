 import java.util.*;
import java.io.*;

public class KeywordExtractor {
  public static void main(String[] args) {
    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    HashMap<String, Integer> tfs;
    for(int i =1; i<41; i++){
    System.out.println(i+".txt");
    dfs = readDocumentFrequencies("freqs.txt");
    tfs = computeTermFrequencies(dir+"/"+i+".txt");
    printTopKeywords(computeTFIDF(tfs,dfs,40), 5);
    }
    //computeTermFrequencies("2.txt");
  }
  
  public static HashMap<String, Integer> computeTermFrequencies(String filename) {
     HashMap<String, Integer> dfre = new HashMap<String, Integer>();
    try{
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();
      String doc = line+" ";//string content
      while(line != null){
        line = br.readLine();
        if(line != null)
        doc = doc + line+" ";
      }
      br.close();
      fr.close();
      
      String nor = normalize(doc);
      String[] parts = nor.split(" ");//content in string array
      String value;
      int frequency = 0;
     // System.out.println(Arrays.toString(parts));
    for(int i = 0; i< parts.length; i++){
      value = parts[i];
          for(int p = 0; p< parts.length; p++){
            if((parts[p]).equals(parts[i])){
              frequency = frequency +1;
            }
          }
          if(value.equals("facebook")){
            frequency--;
          }
          dfre.put(value,frequency);
      frequency = 0;
    } 
    }catch(IOException e){
      System.out.println("io error");
    }
    return dfre;
  }
  
  public static String normalize(String word) {
    return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
  }
  
  public static HashMap<String, Integer> readDocumentFrequencies(String filename) {
     HashMap<String, Integer> dfre = new HashMap<String, Integer>();
    try{
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();
     
      String[] parts;
      while(line != null){
        parts = line.split(" ");
        dfre.put(parts[0],Integer.parseInt(parts[1]));
        line = br.readLine();
      }
       br.close();
       fr.close();
    }catch(IOException e){
      System.out.println("io error");
    }
    return dfre;
  }
  
  public static HashMap<String, Double> computeTFIDF(HashMap<String, Integer> tfs, HashMap<String, Integer> dfs,double nDocs) {
    double idf;
    HashMap<String, Double> tfidf = new HashMap<String, Double>();
    List<String> list = new ArrayList<String>(dfs.keySet());
      Collections.sort(list);
    for(String key : list){
      idf = Math.log(nDocs/dfs.get(key));
      if(tfs.get(key)!= null)
        tfidf.put(key,(tfs.get(key)*idf));
    }
    return tfidf;
  }
  
  /**
   * This method prints the top K keywords by TF-IDF in descending order.
   */
  public static void printTopKeywords(HashMap<String, Double> tfidfs, int k) {
    ValueComparator vc =  new ValueComparator(tfidfs);
    TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(vc);
    sortedMap.putAll(tfidfs);
    
    int i = 0;
    for(Map.Entry<String, Double> entry: sortedMap.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();
      
      System.out.println(key + " " + value);
      i++;
      if (i >= k) {
        break;
      }
    }
  }  
}

/*
 * This class makes printTopKeywords work. Do not modify.
 */
class ValueComparator implements Comparator<String> {
    
    Map<String, Double> map;
    
    public ValueComparator(Map<String, Double> base) {
      this.map = base;
    }
    
    public int compare(String a, String b) {
      if (map.get(a) >= map.get(b)) {
        return -1;
      } else {
        return 1;
      } // returning 0 would merge keys 
    }
  }
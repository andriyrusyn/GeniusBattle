package test2;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class GetWordFrequencies {
	private Map<String, Integer> map; 

	public GetWordFrequencies(Page[] pages) {
		map = new HashMap<String, Integer>();
		for (int i = 0; i < pages.length; i++) {
			getCount(pages[i].getLyrics(), map);
		}
		
		Set<Map.Entry<String, Integer>> s = map.entrySet();
		for (Map.Entry<String, Integer> m : s) {
			System.out.println(m.getKey() + ": " + m.getValue());
		}
	}
	
	private static void getCount(String str, Map<String, Integer> map) {
		String[] arr = str.split("[\\s\\.\\?,]");
		//String[] arr = str.split("[^\\w]");
		System.out.println(arr.length);
		for (int i = 0; i < arr.length; i++) {
			String sTemp = arr[i].toLowerCase();
			System.out.println(sTemp);
			Integer iTemp = map.get(sTemp);
			if (iTemp == null)
				map.put(sTemp, 1);
			else map.put(sTemp, iTemp+1);
		}
	}
	
	public static void main(String[] args) {
		String link1 = "http://rapgenius.com/Eminem-rap-god-lyrics";
		String link2 = "http://rapgenius.com/Eminem-rap-god-lyrics";
		String link3 = "http://rapgenius.com/Eminem-rap-god-lyrics";
		Page[] pgArr = {new Page(link1), new Page(link2), new Page(link3)};
		GetWordFrequencies gw = new GetWordFrequencies(pgArr);
	}
	
}

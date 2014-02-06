package Lab4;

import java.util.Collections;
import java.util.HashMap;

public interface Neat1337Stuff 
{
	static final HashMap<Character, String> l33ctionary = (HashMap)Collections.unmodifiableMap(new HashMap<Character,String>()
	{{
		put('a',"@");
		put('b',"8");
		put('e',"3");
		put('g',"6");
		put('h',"#");
		put('l',"1");
		put('o',"0");
		put('t',"7");
		put('s',"$");
		
	}
	});
	
	abstract char l337ify(char c);
}

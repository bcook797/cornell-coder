package mars.rover;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public interface Directions {
	
	// Movement arrays based direction
	int[] north = {0,1};
	int[] south = {0,-1};
	int[] east = {1,0};
	int[] west = {-1,0};
	
	// Handles all rotations based on heading and direction
	@SuppressWarnings("serial")
	Map<String, String> DIRECTIONS =
		Collections.unmodifiableMap(
				new HashMap<String, String>() {
					{
						put("NL", "W");
						put("NR", "E");
						put("SL", "E");
						put("SR", "W");
						put("EL", "N");
						put("ER", "S");
						put("WL", "S");
						put("WR", "N");
					}
				});
	
	// Handles all movements based on direction
	@SuppressWarnings("serial")
	Map<String, int[]> MOVE =
		Collections.unmodifiableMap(
				new HashMap<String, int[]>() {
					{
						put("N", north);
						put("S", south);
						put("E", east);
						put("W", west);						
					}
				});
}

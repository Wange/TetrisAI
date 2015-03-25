package tetris;

public class Tools {
	
	public static void copy2DArray(int[][] source,int[][] destination){
		for (int a=0;a<source.length;a++){
			System.arraycopy(source[a],0,destination[a],0,source[a].length);
		}
	}
	
	
}

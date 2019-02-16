import java.io.IOException;

public class X {
	public static void main(String[] args) throws IOException{
		String path = args[0];
		
		path= path.replace("%5C", "\\");
		path= path.substring(13,path.length());
		Runtime.getRuntime().exec(path);
///*Beispiel:*/	Runtime.getRuntime().exec("C:\\games\\icytower151\\icytower15.exe");

	}
}
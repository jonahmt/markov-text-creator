import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class FileReader {

	private File file;
	private Scanner sc;
	
	private String text = "";
	
	public FileReader(String fileName) {
		try {
			file = new File(fileName);
			sc = new Scanner(file);
			
			while(sc.hasNextLine()) {
				text += adjustLine(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	
	public String getText() {
		return this.text;
	}
	
	private String adjustLine(String str) {
		if (str.length() == 0) str += " ";
		if (str.charAt(str.length() - 1) != ' ') str += " ";
		return str;
	}
	
	public void writeToFile(String fileName, String input) {
		File outputFile = new File(fileName);
		try {
			outputFile.createNewFile();
	
			FileWriter writer = new FileWriter(outputFile);
			writer.write(input);
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

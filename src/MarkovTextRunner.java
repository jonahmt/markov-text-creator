import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MarkovTextRunner {

	private static int outputLength = 500; //words
	private static int approxLineLength = 100; //characters
	
	public static void main(String[] args) {
		
		FileReader reader = new FileReader("C:\\Users\\jonah\\Desktop\\MarkovText.txt");
		String text = reader.getText();
		
		ArrayList<String> key = makeKeyArray(text);
		float[][] matrix = makeMatrix(key, text);
		
		String startWord = key.get((int)(key.size()*Math.random()));
		
		String output = createOutput(matrix, key, outputLength, startWord);
		
		reader.writeToFile("C:\\Users\\jonah\\Desktop\\MarkovOutput.txt", adjust(output));
		
	}

	private static ArrayList<String> makeKeyArray(String str) {
		String[] words = str.split(" ");
		ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(words));
		ArrayList<String> uniqueWords = new ArrayList<String>();
		while(wordList.size() > 0) {
			uniqueWords.add(wordList.get(0));
			wordList.removeAll(Collections.singleton(wordList.get(0)));
		}
		return uniqueWords;
	}
	
	private static float[][] makeMatrix(ArrayList<String> key, String str) {
		//Setup the matrix
		String[] text = str.split(" ");
		float[][] matrix = new float[key.size()][key.size()];
		//Add the initial numbers to the matrix
		for (int i = 0; i < text.length - 1; i++) {
			int thisWord = key.indexOf(text[i]);
			int nextWord = key.indexOf(text[i + 1]);
			matrix[thisWord][nextWord]++;
		}
		//Divide each row so the sum is 1
		for (int i = 0; i < matrix.length; i++) {
			int sum = 0;
			for (int j = 0; j < matrix[i].length; j++) {
				sum += matrix[i][j];
			}
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] /= sum;
			}
		}
		//Return the matrix
		return matrix;
	}
	
	private static String createOutput(float[][] matrix, ArrayList<String> key, int length, String startWord) {
		String result = "";
		String currentWord = startWord;
		for (int i = 0; i < length; i++) {
			result += currentWord + " ";
			
			float rand = (float) Math.random();
			float help = 0;
			int nextWordIndex = 0;
			for (int j = 0; help < rand; j++) {
				help += matrix[key.indexOf(currentWord)][j];
				nextWordIndex = j;
			}
			
			currentWord = key.get(nextWordIndex);
		}
		return result;
	}
	
	private static String adjust(String str) {
		//Capitalize
		for (int i = 0; i < str.length() - 2; i++) {
			if (str.charAt(i) == '.' || str.charAt(i) == '?' || str.charAt(i) == '!') {
				str = str.substring(0, i) + Character.toUpperCase(str.charAt(i)) + str.substring(i+1);
			}
		}
		str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
		//Add Period
		str = str.substring(0, str.length() - 1);
		str.trim();
		str += '.';
		//Split into lines 
		int i = approxLineLength;
		int j = 0;
		while(i < str.length() - 1) {
			j = i;
			while (str.charAt(j) != ' ' && j < str.length() - 2) {
				j++;
			}
			str = str.substring(0, j+1) + '\n' + str.substring(j+1);
			i +=  i - j + approxLineLength;
		}
		return str;
	}
	
}

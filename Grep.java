package poormansgrep.BonusProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import poormansgrep.BonusProject.Search;

public class Grep {
	/**
	 * Implements grep options -l and -i and uses singleSearch and multiSearch of class search.
	 * Returns nothing but a console output regarding the options and given textfile/s 
	 * as input.
	 * 
	 * @param commands
	 * 			input as array given via console
	 * @throws FileNotFoundException
	 * 			when given path is wrong or file does not exist
	 * @throws IOException
	 */
	public static void GrepTest(String[] commands) throws FileNotFoundException, IOException{
		
		if (!commands[0].equals("grep")){
			System.out.println("Wrong command");
		}
		else {
			String[] str = new String[2], fileName = new String[2];
			String key = "";
			Boolean l = false,i = false;
			int j2 = 1, fileCount = 0;
			Path p;
			FileInputStream fin,fin2;
			Scanner sc,sc2;
			
			for(int j = 1; j< commands.length; j++){
				if (commands[j].equals("-l")){
					l = true;
					j2++;
				} else if (commands[j].equals("-i")){
					i = true;
					j2++;
				} else if (j==j2) {
					key = commands[j];
				} else if (commands[j].contains(".txt")){
					fileCount++;
				}
			}
			try {
				switch (fileCount) {
				case 1:
					fin = new FileInputStream(new File(commands[commands.length-1]));
					sc = new Scanner(fin, "UTF-8");
					while(sc.hasNextLine()){
						str[0] = str[0] + (sc.hasNextLine() ? sc.nextLine() : "") + "\n";
					}
					sc.close();
					System.out.println(Search.singleSearch(key, str[0], i));
					break;
				case 2:
					// extract file name
					for(int f = 0; f<2; f++){
						p = Paths.get(commands[commands.length-2 + f]);
						fileName[f] = p.getFileName().toString();
					}
					// first file input
					fin = new FileInputStream(new File(commands[commands.length-2]));
					sc = new Scanner(fin, "UTF-8");
					while(sc.hasNextLine()){
						str[0] = str[0] + (sc.hasNextLine() ? sc.nextLine() : "") + "\n";
					}
					sc.close();
					fin.close();
					// second file input
					fin2 = new FileInputStream(new File(commands[commands.length-1]));
					sc2 = new Scanner(fin2, "UTF-8");
					while(sc2.hasNextLine()){
						str[1] = str[1] + (sc2.hasNextLine() ? sc2.nextLine() : "") + "\n";
					}
					sc2.close();
					fin2.close();
					System.out.println(Search.multiSearch(key, str, i,l, fileName));
					break;
				default:
					System.out.println("No file was given");
					break;
				}
			} catch (FileNotFoundException e){
				System.out.println("File not Found");
			} catch (Exception e) {
				System.out.println("Something went wrong *display dinosaur*");
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[] commands = args;
		GrepTest(commands);

		//following code is only for testing purpose
		int i = 0;
		for(String s: args){
			if(i==0){
				System.out.println("The following commands were given to GrepTest");
			}
			System.out.println(s);
			commands[i] = s;
			i++;
		}
	}
}

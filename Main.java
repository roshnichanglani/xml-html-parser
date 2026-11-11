import java.io.*;

public class Main{
	public static void main(String[] args) {
		try {
			String inputFile;
			String tempFile = "temp-" + String.valueOf(System.currentTimeMillis())+ ".temp";
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter XML File Name: ");
			inputFile = br.readLine();

			new XmlPreProcessor(inputFile, tempFile).convertSelfClosingTags();
			HTMLParser parser = new HTMLParser(tempFile);
			if(parser.parseHTMLFile()){
				System.out.println("File is Perfect");
			}

			File f = new File(tempFile);

			if(f.delete()){
				System.out.println("Completed Processing..");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
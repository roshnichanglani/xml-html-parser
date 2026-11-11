import java.util.*;
import java.io.*;

public class HTMLParser {
    private String fileName;

    public HTMLParser(String fileName) {
        this.fileName = fileName;
    }

    public boolean parseHTMLFile() {
        try {
            Stack<String> tagStack = new Stack<String>();
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            int lineNumber = 1;
            boolean flag = true;
            
            while((line = br.readLine()) != null) {
                int startIndex = -1;
                while((startIndex = line.indexOf("<", startIndex + 1)) != -1) {
                    String tagElement = line.substring(startIndex, line.indexOf(">", startIndex+1) + 1);

                    int endIndex = tagElement.indexOf(" ") == -1 ? tagElement.indexOf(">") : tagElement.indexOf(" ");

                    String tagName = tagElement.substring(1, endIndex);

                    if(tagName.startsWith("/")) {
                        tagName = tagName.substring(1);
                        String stackTop = tagStack.peek();
                        if(stackTop.equalsIgnoreCase(tagName)) {
                            tagStack.pop();
                        } else {
                            System.out.println("Error at line no : " + lineNumber + " for tag : " + tagName + " !");
                            flag = false;
                        }
                    } else {
                        tagStack.push(tagName);
                    }
                }
                lineNumber++;
            }
            br.close();
            return flag;
        } catch(IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
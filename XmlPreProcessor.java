import java.io.*;

public class XmlPreProcessor {
	private String inputFileName;
	private String outputFileName;

	public XmlPreProcessor(String inputFileName, String outputFileName) {
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
	}

	public void convertSelfClosingTags() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFileName));
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));

			String line;
			
			while((line = br.readLine()) != null) {
				String modifiedLine = convertSelfClosingTagsInALine(line);
				bw.write(modifiedLine + "\n");
			}
			
			br.close();
			bw.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	private String convertSelfClosingTagsInALine(String line) {
		StringBuilder outputLine = new StringBuilder();

		int index = -1;
		int previousIndexToCopyFrom = 0;
		while((index = line.indexOf("<", index + 1)) != -1){
			int startIndex = index;
			int closeIndex = line.indexOf(">", index + 1);
			String tag = line.substring(startIndex, closeIndex + 1);

			if(isSelfClosing(tag)) {
				String tagElement = tag.substring(1, tag.length() - 2);

				int tagEndIndex = tagElement.indexOf(" ") == -1 ? tagElement.length() : tagElement.indexOf(" ");
				String tagName = tagElement.substring(0, tagEndIndex);
				String startTag = "<" + tagElement + ">";
				String endTag = "</" + tagName + ">";
				String replaceTag = startTag + endTag;
				outputLine.append(line.substring(previousIndexToCopyFrom, startIndex));
				outputLine.append(replaceTag);
				previousIndexToCopyFrom = closeIndex + 1;
			}
		}
		outputLine.append(line.substring(previousIndexToCopyFrom));
		return outputLine.toString();
	}

	private boolean isSelfClosing(String tag) {
		return tag.endsWith("/>");
	}
}
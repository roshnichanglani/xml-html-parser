# xml-html-parser

A Java command-line tool that checks whether the tags in an XML/HTML file are properly nested. It uses a stack and reports the line number where a mismatch is found.

## How it works

1. **Preprocessing (`XmlPreProcessor`)**: self-closing tags such as `<img src="a.png"/>` are rewritten as `<img src="a.png"></img>` and written to a temporary file. This is done line by line, so line numbers stay the same.
2. **Validation (`HTMLParser`)**: the temporary file is read line by line with `BufferedReader`. Each opening tag is pushed onto a `Stack`. Each closing tag is compared with the tag on top of the stack (case-insensitive). If they match, the tag is popped. If not, the line number and tag name are printed.
3. **Cleanup (`Main`)**: asks for the file name, runs both steps, prints the result, and deletes the temporary file.

Time complexity is O(n) in the size of the file. Memory use is O(d), where d is the maximum nesting depth.

## How to run

```
javac *.java
java Main
```

When prompted, enter the file name, for example `test.html`.

## Sample

Valid file (`test.html`):

```
Enter XML File Name:
test.html
File is Perfect
Completed Processing..
```

File with a mismatched tag:

```
<html>
<body>
<div>
</p>
</div>
</body>
</html>
```

```
Enter XML File Name:
broken.html
Error at line no : 4 for tag : p !
Completed Processing..
```

## Known limitations

- A closing tag with no open tag, or tags still open at the end of the file, are not handled yet.
- A tag that spans more than one line is not supported.
- Comments, `<!DOCTYPE>` and void tags written without a slash (such as `<br>`) are not handled.
- Comparison ignores case, which suits HTML. XML itself is case-sensitive.


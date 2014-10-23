Apache Crunch XML Source
===================



## Build and Installation



## Usage

Add the tzolov GitHub maven repository to the POM file of the project:

    <repository>
        <id>git-tzolov</id>
        <name>tzolov's Git based repo</name>
        <url>https://github.com/tzolov/maven-repo/raw/master/</url>
    </repository>


Sample code:

```java
	XmlSource xmlSource = new XmlSource(xmlInFile, "<PLANT", "</PLANT>");

	MRPipeline p = new MRPipeline(XmlSourceIT.class, tmpDir.getDefaultConfiguration());

	PCollection<String> in = p.read(xmlSource);

	PTable<String, String> out = in.by(new MapFn<String, String>() {

		@Override
		public String map(String input) {
			xmlElementCount++;
			return input;
		}
	}, Writables.strings());

	out.write(To.textFile(outFile));

	p.done();
```
  

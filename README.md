Apache Crunch XML Source
===================

The [Apache Crunch](https://crunch.apache.org) Java library provides a framework for writing, testing, and running MapReduce and Apache Spark pipelines. Its goal is to make pipelines that are composed of many user-defined functions simple to write, easy to test, and efficient to run.

Crunch supports various input formats via its [Source](https://crunch.apache.org/user-guide.html#sources) abstraction. The  XmlSource employs the Mahout's [XmlInputFormat](https://github.com/apache/mahout/blob/ad84344e4055b1e6adff5779339a33fa29e1265d/examples/src/main/java/org/apache/mahout/classifier/bayes/XmlInputFormat.java) to adds XML read capabilities to Crunch. 

## Build and Installation

```
   mvn clean install
```

## Usage

Add the tzolov GitHub maven repository to the POM file of the project:

```xml
    <repository>
        <id>git-tzolov</id>
        <name>tzolov's Git based repo</name>
        <url>https://github.com/tzolov/maven-repo/raw/master/</url>
    </repository>
```

Add the crunch XML source dependecy:
```xml
	<dependency>
		<groupId>org.apache.crunch.io.xml</groupId>
		<artifactId>crunch-xmlsource</artifactId>
		<version>0.0.1</version>	
	</dependency>
```

Sample data:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<CATALOG>
	<PLANT>
		<COMMON>Bloodroot</COMMON>
		<BOTANICAL>Sanguinaria canadensis</BOTANICAL>
	</PLANT>
	.......
	<PLANT>
		<COMMON>Columbine</COMMON>
		<BOTANICAL>Aquilegia canadensis</BOTANICAL>
	</PLANT>
</CATALOG>
```

Sample code:

```java
	XmlSource xmlSource = new XmlSource(xmlInFile, "<PLANT", "</PLANT>");

	MRPipeline pipeline = new MRPipeline(XmlSourceIT.class);

	PCollection<String> in = pipeline.read(xmlSource);

	PTable<String, String> out = in.by(new MapFn<String, String>() {
		@Override
		public String map(String input) {
			return input;
		}
	}, Writables.strings());

	out.write(To.textFile(outFile));

	pipeline.done();
```
  

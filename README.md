Apache Crunch XML Source
===================



## Build and Installation



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
		<ZONE>4</ZONE>
		<LIGHT>Mostly Shady</LIGHT>
		<PRICE>$2.44</PRICE>
		<AVAILABILITY>031599</AVAILABILITY>
	</PLANT>
	.......
	<PLANT>
		<COMMON>Columbine</COMMON>
		<BOTANICAL>Aquilegia canadensis</BOTANICAL>
		<ZONE>3</ZONE>
		<LIGHT>Mostly Shady</LIGHT>
		<PRICE>$9.37</PRICE>
		<AVAILABILITY>030699</AVAILABILITY>
	</PLANT>
</CATALOG>
```

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
  

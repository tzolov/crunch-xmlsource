Apache Crunch XML Source
===================



## Build and Installation



## Usage



Sample code:

		PType<String> ptype = Writables.strings();
		
		
		String xmlInFile = tmpDir.copyResourceFileName("sample1.xml");
		String outFile = tmpDir.getFileName("out");

		xmlElementCount = 0;

		XmlSource xmlSource = new XmlSource(xmlInFile, "<PLANT", "</PLANT>");

		MRPipeline p = new MRPipeline(XmlSourceIT.class,
				tmpDir.getDefaultConfiguration());

		p.enableDebug();
		
		PCollection<String> in = p.read(xmlSource);

		PTable<String, String> out = in.by(new MapFn<String, String>() {

			@Override
			public String map(String input) {
				xmlElementCount++;
				return input;
			}
		}, ptype);

		out.write(To.textFile(outFile));

		p.done();

  

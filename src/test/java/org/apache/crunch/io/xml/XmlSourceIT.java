/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.crunch.io.xml;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;

import org.apache.crunch.MapFn;
import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.impl.mr.run.RuntimeParameters;
import org.apache.crunch.io.To;
import org.apache.crunch.test.TemporaryPath;
import org.apache.crunch.types.PType;
import org.apache.crunch.types.writable.Writables;
import org.junit.Rule;
import org.junit.Test;

@SuppressWarnings("serial")
public class XmlSourceIT implements Serializable {

	@Rule
	public transient TemporaryPath tmpDir = new TemporaryPath(
			RuntimeParameters.TMP_DIR, "hadoop.tmp.dir");

	private static int xmlElementCount = 0;

	@Test
	public void testCompressText() throws Exception {
		
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

		assertEquals(36, xmlElementCount);
	}
}

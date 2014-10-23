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

import org.apache.crunch.io.FormatBundle;
import org.apache.crunch.io.impl.FileSourceImpl;
import org.apache.crunch.types.writable.Writables;
import org.apache.hadoop.fs.Path;

public class XmlSource extends FileSourceImpl<String> {

	public XmlSource(String inputPath, String tagStart, String tagEnd) {
		super(new Path(inputPath), Writables.strings(), createFormatBundle(
				tagStart, tagEnd));
	}

	private static FormatBundle<XmlInputFormat> createFormatBundle(
			String tagStart, String tagEnd) {

		FormatBundle<XmlInputFormat> formatBundle = FormatBundle
				.forInput(XmlInputFormat.class)
				.set(XmlInputFormat.START_TAG_KEY, tagStart)
				.set(XmlInputFormat.END_TAG_KEY, tagEnd);

		return formatBundle;
	}
}

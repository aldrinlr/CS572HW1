package com.usc.csci572;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class TSVParser extends AbstractParser {
	
	private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.application("computrabajo-ar-20130526"));
	public static final String MIME_TYPE = "text/tab-separated-values"; 
	
	@Override
	public Set<MediaType> getSupportedTypes(ParseContext arg0) {
		return SUPPORTED_TYPES;
	}

	@Override
	public void parse(InputStream stream, ContentHandler handler, Metadata metadata,
			ParseContext context) throws IOException, SAXException, TikaException {
		
		BufferedReader isr = null;
		String str;
		metadata = new Metadata();
		
		isr = new BufferedReader(new InputStreamReader(stream));
		
		while((str = isr.readLine())!=null){
			String[] values = str.split("\t");
			System.out.println(values);
			//The values should be inserted into an ArrayList/HashSet or something which is convenient
			metadata.add("postedDate", values[0]);
			metadata.add("location", values[1]);
			metadata.add("department", values[2]);
			metadata.add("title", values[3]);
			metadata.add("salary",values[4]);
			metadata.add("start",values[5]);
			metadata.add("duration",values[6]);
			metadata.add("jobtype",values[7]);
			metadata.add("applications",values[8]);
			metadata.add("company",values[9]);
			metadata.add("contactPerson",values[10]);
			metadata.add("phoneNumber",values[11]);
			metadata.add("faxNumber",values[12]);
			metadata.add("location",values[13]);
			metadata.add("latitude",values[14]);
			metadata.add("longitude",values[15]);
			metadata.add("firstSeenDate",values[16]);
			metadata.add("url",values[17]);
			metadata.add("lastSeenDate",values[18]);
		}
		
		//Write a handler to convert your values into XHTML
		AttributesImpl attrs = new AttributesImpl();
		handler.startDocument();
		handler.startElement("ol","", "", attrs);
		
         XHTMLContentHandler xhtml = new XHTMLContentHandler(handler, metadata);
         xhtml.startDocument();
         xhtml.endDocument();
	}
	
	public static void main(String[] args) throws IOException, SAXException, TikaException{
		File file = new File("src/computrabajo-ar-20130526.tsv");
		InputStream stream = new FileInputStream(file);
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		TSVParser p = new TSVParser();
		metadata.add("MIME TYPE", MIME_TYPE);
		p.parse(stream,handler,metadata,new ParseContext());
	}
}


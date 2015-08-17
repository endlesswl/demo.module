package com.lingcaibao.weixin.core.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Document工具类
 * 
 * @author weil
 * 
 */
public class DocumentUtil {
	/**
	 * Document 转成 xml string
	 * 
	 * @param doc
	 * @return
	 */
	public static String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();

			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * xml String转Document
	 * @param xmlStr
	 * @return
	 */
	public static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String xmlstr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><xml><Content>Iphone6?&lt;a href='https://www.jinshuju.net/f/dS9iMH'&gt;预约点击&lt;/a&gt;专员为您服务！</Content><MsgType>text</MsgType></xml>";
		Document document=convertStringToDocument(xmlstr);
		System.out.println(convertDocumentToString(document));
	}
}

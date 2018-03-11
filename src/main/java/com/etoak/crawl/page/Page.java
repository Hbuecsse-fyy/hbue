package com.etoak.crawl.page;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.etoak.crawl.util.CharsetDetector;

/*
* page
*   1: 保存获取到的响应的相关内容;
* */
public class Page {

    private byte[] content ;
    private String html ;  //网页源码字符串
    private Document doc  ;//网页Dom文档
    private String charset ;//字符编码
    private String url ;//url路径
    private String contentType ;// 内容类型


    public Page(byte[] content , String url , String contentType){
        this.content = content ;
        this.url = url ;
        this.contentType = contentType ;
    }

    public String getCharset() {
        return charset;
    }
    public String getUrl(){return url ;}
    public String getContentType(){ return contentType ;}
    public byte[] getContent(){ return content ;}

    /**
     * 返回网页的源码字符串
     *
     * @return 网页的源码字符串
     */
    public String getHtml() {
        if (html != null) {
            return html;
        }
        if (content == null) {
            return null;
        }
        if(charset==null){
            charset = CharsetDetector.guessEncoding(content); // 根据内容来猜测 字符编码
        }
        try {
            this.html = new String(content, charset);
            return html;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*
    *  得到文档
    * */
    public Document getDoc(){
        if (doc != null) {
            return doc;
        }
        try {
            this.doc = Jsoup.parse(getHtml(), url);
            return doc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

//   public static void main(String []args) throws Exception {
//	   String s= "http://xinjinqiao.tprtc.com/admin/main/pro!lrprolist.do";
//		 URL url = new URL(s);
//		 HttpURLConnection http = (HttpURLConnection) url.openConnection();
//		 http.setDoOutput(true);  
//		 http.setDoInput(true);  
//       http.setRequestMethod("POST");  
//		 http.connect();  
//		 OutputStreamWriter out = new OutputStreamWriter(http.getOutputStream(), "UTF-8"); 
//		
//		 String input = "name=flr&nowpage=1&pagesize=10"; 
//		 
//		 out.append(input);  
//       out.flush();  
//       out.close();  
//       int length = (int) http.getContentLength();
//       System.out.println(length);
//   	 BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
//		 String line;
//		 StringBuffer buffer = new StringBuffer();
//		 while ((line = reader.readLine()) != null) {
//			 buffer.append(line);
//		 }
//		 reader.close();
//		 http.disconnect();
//		 System.out.println(buffer.toString());
//   }
}

package cn.psw.youwenbida.consumer.utils;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lucence {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    // 索引保存目录
    private static String indexDir = System.getProperty("user.dir")+"/youwenbida-consumer/src/main/resources/lucence/";

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/youwenbida?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) throws Exception {
//        String sql = "select pid, ptitle from problem";
        String sql = "select aid, ahd from answer";
        System.out.println("====="+indexDir);
        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery(sql);
            // 给数据库创建索引,此处执行一次，不要每次运行都创建索引
            // 以后数据有更新可以后台调用更新索引
//            createIndex(rs,"pro");
            createIndex(rs,"ans");
            // 显示查询结果
//            showSearchResults("故事专心球","pro");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<Map<String,Object>> showSearchResults(String keyword, String path)
            throws Exception {
        //创建或打开索引目录
        Directory directory = FSDirectory.open(Paths.get(new File(indexDir+path).getPath()));
        SmartChineseAnalyzer smartChineseAnalyzer = new SmartChineseAnalyzer();

        //创建indexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建indexSearcher对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //创建查询
        Query query = new QueryParser("text", smartChineseAnalyzer).parse(keyword);
        //执行查询
        //参数一  查询对象    参数二  查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println("找到 " + topDocs.scoreDocs.length + " 个命中.");
        System.out.println("序号\t匹配度得分\tid\t\t\ttext");

        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 0; i < topDocs.scoreDocs.length; ++i) {
            Map<String,Object> map =new HashMap<>();
            ScoreDoc scoreDoc= topDocs.scoreDocs[i];
            int docId = scoreDoc.doc;
            Document d = indexSearcher.doc(docId);
            System.out.print((i + 1));
            System.out.print("\t" + scoreDoc.score);
            System.out.print("\t" + d.get("id"));
            map.put("id",d.get("id"));
            List<IndexableField> fields = d.getFields();
            TokenStream tokenStream = smartChineseAnalyzer.tokenStream(fields.get(1).name(), new StringReader(d.get(fields.get(1).name())));
            String fieldContent = highlighter.getBestFragment(tokenStream, d.get(fields.get(1).name()));
            System.out.print("\t" + fieldContent);
            map.put("text",fieldContent);
            System.out.println("<br>");
            list.add(map);
        }
        indexReader.close();
        return list;
    }

    private static void createIndex(ResultSet rs,String path)  throws Exception {
        Directory directory = FSDirectory.open(Paths.get(new File(indexDir+path).getPath()));
        SmartChineseAnalyzer smartChineseAnalyzer = new SmartChineseAnalyzer();
        //创建indexwriterConfig(参数分词器)
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(smartChineseAnalyzer);
        //创建indexwrite 对象(文件对象，索引配置对象)
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
        indexWriter.deleteAll();
        // 遍历ResultSet创建索引
        while (rs.next()) {
            // 创建document并添加field
            Document doc = new Document();
            doc.add(new TextField("id", rs.getString("aid"), Field.Store.YES));
            doc.add(new TextField("text", rs.getString("ahd"),
                    Field.Store.YES));
            // 将doc添加到索引中
            System.out.println(rs.getString("ahd"));
            indexWriter.addDocument(doc);
        }
        indexWriter.close();
    }
}
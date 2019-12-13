package com.ly.demo.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 统计项目中文件数量和代码总行数
 *
 * @author xinre
 */
public class CodeTotal {


    public static void main(String[] args) throws IOException {

        // 路径
        String path = "C:\\F\\IdeaProjects\\fportal-trunk\\WebRoot\\fportal\\page";
        //String path="C:\\F\\IdeaProjects\\fportal-trunk\\src";
        List<File> list = total(path);
        System.out.println("文件数量：" + list.size());

        //统计代码行数
        Integer row = 0;

        for (File file : list) {
            System.out.println(file.getName());
            FileReader fr = new FileReader(file);//创建文件输入流
            BufferedReader in = new BufferedReader(fr);//包装文件输入流，可整行读取
            String line = "";
            while ((line = in.readLine()) != null) {
                row++;
            }
        }
        System.out.println("代码行数：" + row);
    }

    private static List<File> total(String path) {
        List<File> files = new ArrayList<File>();
        File file = new File(path);
        File[] files2 = file.listFiles();
        if (files2 != null) {
            for (File file3 : files2) {
                if (file3.isFile()) {
                    files.add(file3);
                } else {
                    files.addAll(files.size(), total(file3.getPath()));
                }
            }
        }
        return files;
    }
}

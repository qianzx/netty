package com.bilibili.netty.nio.cp1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.nio.cp1
 * @ClassName TestFileWalkTree
 * @Description TODO
 * @Author qianzx
 * @Date 2022/6/22-21:52
 * @Version 1.0
 */
public class TestFileWalkTree {
    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("E:\\wowback"),new SimpleFileVisitor<Path>(){

            @Override
            //进入文件夹前执行
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("===> 进入" + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            //遍历到某个文件时执行
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            //退出文件夹前执行
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });
    }
}

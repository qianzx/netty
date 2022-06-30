package com.bilibili.netty.nio.cp1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.nio.cp1
 * @ClassName TestFileCopy
 * @Description TODO
 * @Author qianzx
 * @Date 2022/6/21-22:50
 * @Version 1.0
 */
public class TestFileCopy {

    public static void main(String[] args) throws IOException {
        String source = "E:\\wow";
        String target = "E:\\wowback";

        List<Path> walk = Files.walk(Paths.get(source)).collect(Collectors.toList());
        walk.forEach(path -> {
            try {
                String tragetName = path.toString().replace(source, target);
                Path targetPath = Paths.get(tragetName);
                if(Files.isDirectory(path)){
                    Files.createDirectory(targetPath);
                }else if(Files.isRegularFile(path)){
                    Files.copy(path,targetPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

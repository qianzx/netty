package com.bilibili.netty.chat.protocl;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.protocl
 * @ClassName Serializer
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/15-15:54
 * @Version 1.0
 */
public interface Serializer {
    //<T>方法级别的泛型声明
    <T> T deserialize(Class<T> clazz,byte[] bytes);

    <T> byte[] serialize(T object);

    enum Algorithm implements Serializer{
        JAVA{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes){
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    T t = (T) objectInputStream.readObject();
                    return t;
                } catch (Exception e) {
                    throw new RuntimeException("序列化失败");
                }
            }

            @Override
            public <T> byte[] serialize(T object){
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(object);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    return bytes;
                } catch (Exception e) {
                    throw new RuntimeException("反序列化失败");
                }
            }
        },
        JSON{

           @Override
            public  <T> T deserialize(Class<T> clazz,byte[] bytes){
               Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
               String s = new String(bytes, StandardCharsets.UTF_8);
               //Gson gson = new Gson();
               T t = gson.fromJson(s, clazz);
               return t;
           }
           @Override
           public  <T> byte[] serialize(T object){
               Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
               //Gson gson = new Gson();
               String s = gson.toJson(object);
               byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
               return bytes;
           }
        }

    }
    static class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>>{

        @Override
        public Class<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                String asString = jsonElement.getAsString();
                Class<?> aClass = Class.forName(asString);
                return aClass;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public JsonElement serialize(Class<?> aClass, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(aClass.getName());
        }
    }
 }

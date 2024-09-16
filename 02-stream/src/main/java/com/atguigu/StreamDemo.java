package com.atguigu;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * ClassName: StreamDemo
 * Package: com.atguigu
 */
public class StreamDemo {

    public static void main(String[] args) {
        test05();
    }

    private static void test05() {
        List<Author> authors = getAuthors();
        // Book Category 原本格式是:哲學,愛情，現在改成用 array 的方式表示
        authors.stream()
                .flatMap((Function<Author, Stream<Book>>) author -> author.getBooks().stream())
                .distinct()
                .flatMap((Function<Book, Stream<String>>) book -> Arrays.stream(book.getCategory().split(",")))
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println(s);
                    }
                });
    }

    private static void test04() {
        List<Author> authors = getAuthors();
        authors.stream()
                .flatMap((Function<Author, Stream<Book>>) author -> author.getBooks().stream())
                .forEach(book -> System.out.println(book));
    }

    private static void test03() {
        List<Author> authors = getAuthors();
        authors.stream()
                .map(author -> author.getName()) // 將後續 Stream 裡的 element 從 Authors 變成 author.name
                .forEach(s -> System.out.println(s));
    }

    private static void test02() {
        Map<String,Integer> map = new HashMap<>();
        map.put("蜡笔小新",19);
        map.put("黑子",17);
        map.put("日向翔阳",16);
        Stream<Map.Entry<String, Integer>> stream = map.entrySet().stream();
        stream.filter(new Predicate<Map.Entry<String, Integer>>() {
            @Override
            public boolean test(Map.Entry<String, Integer> stringIntegerEntry) {
                return stringIntegerEntry.getValue() > 16;
            }
        }).forEach(new Consumer<Map.Entry<String, Integer>>() {
            @Override
            public void accept(Map.Entry<String, Integer> stringIntegerEntry) {
                System.out.println(stringIntegerEntry.getKey() + " : " + stringIntegerEntry.getValue());
            }
        });
    }

    public static void test01(){
        // 題目: Print 所有 age < 18 的 author 的名字，並且要去重
        List<Author> authors = getAuthors();
        authors.stream() // 需要先將集合轉換成 Stream Class 才能去調用 Stream API
                .distinct()
//                .filter(new Predicate<Author>() {
//                    @Override
//                    public boolean test(Author author) {
//                        return author.getAge() < 18;
//                    }
//                })
                .filter(author -> author.getAge() < 18)
                .forEach(new Consumer<Author>() {
                    @Override
                    public void accept(Author author) {
                        System.out.println("Author: " + author.getName());
                    }
                });
    }

    private static List<Author> getAuthors() {
        //数据初始化
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        Author author2 = new Author(2L,"亚拉索",15,"狂风也追逐不上他的思考速度",null);
        Author author3 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);
        Author author4 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);

        //书籍列表
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L,"刀的两侧是光明与黑暗","哲学,爱情",88,"用一把刀划分了爱恨"));
        books1.add(new Book(2L,"一个人不能死在同一把刀下","个人成长,爱情",99,"讲述如何从失败中明悟真理"));

        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(4L,"吹或不吹","爱情,个人传记",56,"一个哲学家的恋爱观注定很难把他所在的时代理解"));

        books3.add(new Book(5L,"你的剑就是我的剑","爱情",56,"无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));

        author.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        List<Author> authorList = new ArrayList<>(Arrays.asList(author,author2,author3,author4));
        return authorList;
    }
}

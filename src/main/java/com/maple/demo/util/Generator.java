package com.maple.demo.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.maple.demo.config.bean.BaseEntity;

/**
 * MyBatis-Plus代码生成工具
 *
 * @author 笑小枫
 * @date 2022/4/25
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
public class Generator {

    public static void main(String[] args) {

        // 设置作者
        String auth = "笑小枫";
        // 设置父包名
        String packageName = "com.maple.demo";
        // 设置父包模块名
        String moduleName = "";
        // 指定输出目录
        String path = "D:";
        String url = "jdbc:mysql://127.0.0.1:3306/maple?useUnicode=true&useSSL=false&characterEncoding=utf8";
        String username = "root";
        String password = "Zhang123";
        // 设置需要生成的表名
        String table = "sys_operate_log";
        // 设置过滤表前缀
        String[] tablePrefix = {"usc_", "sys_"};
        generateTest(auth, packageName, path, moduleName, url, username, password, table, tablePrefix);
    }

    private static void generateTest(String auth, String packageName, String path, String moduleName,
                                     String url, String username, String password, String table, String[] tablePrefix) {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> builder.author(auth)
                        // 开启 swagger 模式
                        .enableSwagger()
                        .outputDir(path))
                .packageConfig(builder -> builder.parent(packageName)
                                .moduleName(moduleName)
                        // 设置mapperXml生成路径
                        //.pathInfo(Collections.singletonMap(OutputFile.xml, path))
                )
                .strategyConfig(builder -> builder.addInclude(table)
                        .addTablePrefix(tablePrefix)
                        .entityBuilder().superClass(BaseEntity.class).enableLombok().logicDeleteColumnName("delete_flag")
                        .controllerBuilder().enableRestStyle()
                )
                .execute();
    }
}
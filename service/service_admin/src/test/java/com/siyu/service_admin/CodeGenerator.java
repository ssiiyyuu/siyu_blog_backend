package com.siyu.service_admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


import org.junit.jupiter.api.Test;

public class CodeGenerator {

    @Test
    public void run() {

       FastAutoGenerator.create("jdbc:mysql://localhost:3306/table?characterEncoding=UTF-8&useUnicode=true&useSSL=false", 
                                    "root",
                                    "123456")

                .globalConfig(builder -> {
                    builder.author("MybatisPlusGenerator")
                            .commentDate("yyyy-MM-dd hh:mm:ss")
                            .outputDir(System.getProperty("user.dir") + "/src/main/java")
                            .enableSwagger()
                            .dateType(DateType.TIME_PACK)
                            .disableOpenDir()
                    ;
                })

                .packageConfig(builder -> {
                    builder.parent("com.siyu")
                           .moduleName("service_admin")
                           .service("service")
                           .controller("controller")
                           .mapper("mapper")
                           .entity("entity");
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("tableName") //表名
                            .addTablePrefix("sys_")

                            // Entity 策略配置
                            .entityBuilder()
                            .idType(IdType.ASSIGN_ID) //主键分配
                            .enableLombok()
                            .enableFileOverride()
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命

                            // Mapper 策略配置
                            .mapperBuilder()
                            .enableFileOverride()

                            // Service 策略配置
                            .serviceBuilder()
                            .enableFileOverride()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")

                            // Controller 策略配置
                            .controllerBuilder()
                            .enableFileOverride()
                    ;
                })
                .templateConfig(builder -> {
                    //不生成controller
                    builder.disable(TemplateType.CONTROLLER);
                })
                .execute();

    }
}
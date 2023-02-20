package com.zxy.swaycamp.utils;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 代码生成器
 *
 * @author XinYuan Zhao
 * @date 2023/1/23
 * @apiNote
 */
public class CodeGenerator {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {

        String projectPath = System.getProperty("user.dir");

        // XML文件的路径
        String mapperXmlPath = projectPath + "/src/main/resources/mapper/";


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Xinyuan Zhao");
        gc.setIdType(IdType.AUTO);
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setFileOverride(false);
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        //数据源配置
        mpg.setDataSource(mioDataSourceConfig());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.zxy.swaycamp");
        pc.setEntity("domain.entity");
        mpg.setPackageInfo(pc);

        // 调整 xml 生成目录
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return mapperXmlPath + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig tc = new TemplateConfig();
        //关闭默认 xml 生成，调整生成 至 根目录
        tc.setXml(null);
        mpg.setTemplate(tc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);

        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setLogicDeleteFieldName("is_deleted");
        mpg.setStrategy(strategy);

        mpg.execute();
    }


    /**
     * 数据源配置
     *
     * @return
     */
    private static DataSourceConfig mioDataSourceConfig() {
        return new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://123.56.93.248:3306/sway_camp?serverTimezone=GMT%2b8")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("zxy.0207");
    }

}

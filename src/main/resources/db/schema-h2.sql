drop schema if exists `test`;
create schema `test`;
use `test`;
DROP TABLE IF EXISTS `AHI_DATASOURCE_MANAGE`;
CREATE TABLE `AHI_DATASOURCE_MANAGE`
(
    `ID`           int          NOT NULL AUTO_INCREMENT,
    `NAME`         varchar(255) NOT NULL COMMENT '数据源名称',
    `DS_CONFIG`    text COMMENT '脚本',
    `CREATED_AT`   datetime              DEFAULT NULL COMMENT '创建时间',
    `UPDATED_AT`   timestamp    NOT NULL COMMENT '更新时间',
    `DS_EFFECTIVE` int          NOT NULL DEFAULT '0' COMMENT '是否有效 0 数据源连接无效   1 数据源可以使用',
    `DS_ENABLE`    int          NOT NULL DEFAULT '0' COMMENT '是否启用  0 不启用  1 启用',
    `DS_TYPE`      varchar(30)  NOT NULL COMMENT '数据源类型，枚举值',
    PRIMARY KEY (`ID`)
) COMMENT ='数据源配置表';
CREATE <#if table.temporary == true> TEMPORARY </#if>
<#if table.external?? && table.external == true> EXTERNAL </#if>
TABLE IF NOT EXISTS
<#if table.dbName?? && table.dbName !=""> ${table.dbName}.</#if> ${table.tableName}
(
<#list columns as column>
${column.name} ${column.dataType}<#--<#if column.isNullable?? && column.isNullable == false> not null </#if>--><#if column.comment?? && column.comment != "">COMMENT ${column.comment}</#if><#if column_has_next>,</#if>
</#list>
)
<#if table.comment?? && table.comment != "">
COMMENT '${table.comment}'
</#if>
<#if partitionColumnList?? && (partitionColumnList?size > 0)>
PARTITIONED BY
(
    <#list partitionColumns as column>
        ${column.name} ${column.dataType} <#if column.comment?? && column.comment != "">COMMENT '${column.comment}'</#if><#if column_has_next>,</#if>
    </#list>
)
</#if>
<#--
[CLUSTERED BY (col_name, col_name, ...) [SORTED BY (col_name [ASC|DESC], ...)] INTO num_buckets BUCKETS]
[SKEWED BY (col_name, col_name, ...)
ON ((col_value, col_value, ...), (col_value, col_value, ...), ...)

[STORED AS DIRECTORIES]
[
[ROW FORMAT row_format]
[STORED AS file_format]
| STORED BY 'storage.handler.class.name' [WITH SERDEPROPERTIES (...)]
]
[LOCATION hdfs_path]
[TBLPROPERTIES (property_name=property_value, ...)]
-->

ROW FORMAT DELIMITED
    FIELDS TERMINATED BY '${table.fieldsTerminate}'
    <#--LINES TERMINATED BY '${table.linesTerminate}'-->
STORED AS ${table.storeType} LOCATION '${table.location}'


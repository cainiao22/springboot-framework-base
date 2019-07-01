{
    "job": {
        "setting": {
            "speed": {
                 "byte": 1048576
            }
        },
        "content": [
            {
                "reader": {
                    "name": "mysqlreader",
                    "parameter": {
                        "username": "${dbConfig.userName}",
                        "password": "${dbConfig.password}",
                        "connection": [
                            {
                                "querySql": [
                                    "SELECT <#list columnSchemaList as ss> ${ss.columnName} <#if ss_has_next>,</#if></#list> FROM ${dbSynConfig.sourceDbName}.${dbSynConfig.sourceTableName}"
                                ],
                                "jdbcUrl": [
                                    "${dbConfig.url}",
                                ]
                            }
                        ]
                    }
                },
                "writer": {
                    "name": "hdfswriter",
                    "parameter": {
                        "defaultFS": "hdfs://nameservice1",
                        "fileType": "text",
                        "path": "${hiveTableSchema.location}/${partitionHiveColumn.name}=${partitionValue}",
                        "fileName": "${hiveTableSchema.dbName}_${hiveTableSchema.tableName}",
						"column": [
                            <#list columnSchemaList as a>
                                {
                                "name": "${a.columnName}",
                                "type": "STRING"
                                }<#if a_has_next>,</#if>
                            </#list>
                        ],
                        "fieldDelimiter": "${hiveTableSchema.fieldsTerminate}",
                        "writeMode": "APPEND",
                        "compress":"gzip"
                    },
                    "hadoopConfig":{
                    "dfs.nameservices": "nameservice1",
                    "dfs.ha.namenodes.nameservice1": "namenode953,namenode957",
                    "dfs.namenode.rpc-address.nameservice1.namenode953": "BJ-HOST-112:8020",
                    "dfs.namenode.servicerpc-address.nameservice1.namenode953": "BJ-HOST-112:8022",
                    "dfs.client.failover.proxy.provider.nameservice1": "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider"
                    }
                }
            }
        ]
    }
}
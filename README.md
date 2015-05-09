#複数データソースでの2phase commitテスト

##前提
  - Spring Framework 4.1.0.RELEASE
  - Datasource自体はAPサーバ側で定義(試したのはWildfly9.0)

###設定
####APサーバ側定義
 - Wildfly側でのXAデータソース設定
   - Wildfly側Datasource設定
     - jdbc-driver設定
     　oracle、postgresqlのJDBCドライバをそれぞれ設定する

      ```
        ${JBOSS_HOME}\modules
           +- oracle
           |  +- jdbc
           |    +- main
           |      +- module.xml
           |      +- ojdbc6_g.jar
           |
           +- org
              +- postgresql
                +- main
           |      +- module.xml
                  +- postgresql-9.2-1003-jdbc4.jar
      ```

     - module.xmlのそれぞれの內容は
     
     
       org/postgresql/main/module.xml
     
       ```xml:
       <?xml version="1.0" encoding="UTF-8"?>
       <module xmlns="urn:jboss:module:1.0" name="org.postgresql">
           <resources>
               <resource-root path="postgresql-9.2-1003-jdbc4.jar"/>
           </resources>
           <dependencies>
               <module name="javax.api"/>
               <module name="javax.transaction.api"/>
           </dependencies>
       </module>
       ```
       
       oracle/jdbc/main/module.xml
       
       ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       <module xmlns="urn:jboss:module:1.0" name="oracle.jdbc">
           <resources>
               <resource-root path="ojdbc6_g.jar"/>
           </resources>
           <dependencies>
               <module name="javax.api"/>
               <module name="javax.transaction.api"/>
           </dependencies>
       </module>
       ```
       
       - JBoss CLIでドライバを登録
       
       ```sh
       [disconnected /] connect
       [standalone@localhost:9990 /] /subsystem=datasources/jdbc-driver=oracle:add(driver-name=oracle,driver-module-name=oracle.jdbc,driver-xa-datasource-class-name=oracle.jdbc.xa.client.OracleXADataSource) 
       [standalone@localhost:9990 /] /subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=postgresql.jdbc,driver-xa-datasource-class-name=org.postgresql.xa.PGXADataSource) 
       ```
       
       - ${JBOSS_HOME}/standalone/configuration/standalone.xmlファイルにDatasourceの設定を追加
       
       ```xml
       <subsystem xmlns="urn:jboss:domain:datasources:3.0">
         <datasources>
                <xa-datasource jndi-name="java:/jdbc/XAds1" pool-name="XAds1">
                    <xa-datasource-property name="URL">
                        jdbc:oracle:thin:@localhost:1521:XE
                    </xa-datasource-property>
                    <xa-datasource-property name="User">
                        oratest
                    </xa-datasource-property>
                    <xa-datasource-property name="Password">
                        oratest
                    </xa-datasource-property>
                    <driver>oracle</driver>
                    <xa-pool>
                        <min-pool-size>10</min-pool-size>
                        <max-pool-size>20</max-pool-size>
                        <prefill>true</prefill>
                    </xa-pool>
                </xa-datasource>
                <xa-datasource jndi-name="java:/jdbc/XAds2" pool-name="XAds2">
                    <xa-datasource-property name="DatabaseName">
                        pgtest
                    </xa-datasource-property>
                    <xa-datasource-property name="PortNumber">
                        5432
                    </xa-datasource-property>
                    <xa-datasource-property name="ServerName">
                        localhost
                    </xa-datasource-property>
                    <driver>postgresql</driver>
                    <xa-pool>
                        <min-pool-size>10</min-pool-size>
                        <max-pool-size>20</max-pool-size>
                        <prefill>true</prefill>
                    </xa-pool>
                    <security>
                        <user-name>pgtest</user-name>
                        <password>pgtest</password>
                    </security>
                </xa-datasource>
         </datasources>
        </subsystem>
         
       
       - ここまで設定した上でEclipseから実行

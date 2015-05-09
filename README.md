#複数データソースでの2phase commitテスト

##前提
  - Spring Framework 4.1.0.RELEASE
  - Datasource自体はAPサーバ側で定義(試したのはWildfly9.0)

###設定
####APサーバ側定義
 - 対象データーベース
   - Postgresql 9.2
   - Postgresql 9.2

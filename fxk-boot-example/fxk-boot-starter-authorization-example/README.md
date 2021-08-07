keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore mytest.jks -storepass mypass

keytool -importkeystore -srckeystore mytest.jks -destkeystore mytest.jks -deststoretype pkcs12
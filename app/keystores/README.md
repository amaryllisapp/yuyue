### 生成keystore命令：
keytool -genkey -alias yuyue -keyalg RSA -validity 365000 -keystore android.keystore
### 获取可以storeMD5校验码命令：
keytool -v -list -keystore android.keystore

### 证书密码：
liucheng1986
liucheng1986

### 证书名称
yuyue

验证码：
MD5: 2C:73:E4:80:1A:B1:4A:40:01:03:0F:56:9A:65:D0:30
     2C73E4801AB14A4001030F569A65D030
SHA1: 48:1D:C3:96:9C:FA:E2:DF:BB:91:B6:22:04:A3:B5:BE:47:9C:86:D8
SHA256: A8:A9:C2:46:3C:2D:EC:20:BA:30:90:8A:C5:63:92:0A:45:73:97:73:5E:C9:25:9F:88:D2:3A:56:38:51:78:39
签名算法名称: SHA256withRSA
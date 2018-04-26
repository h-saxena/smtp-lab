# smtp-lab


# 1. Run this on PG-03 -------------------
java -DserverPort=3000 -DrelayToHost=smtp.gswcm.net -DrelayToPort=25 -cp ./bin edu.hems.RelayServerBootup

## OR below; just to witness hopping without interacting with smtp.gswcm.net
java -DserverPort=3000 -cp ./bin edu.hems.RelayServerBootup

#2. Run this on PG-02 --------------------
java -DserverPort=3000 -DrelayToHost=PG-03 -DrelayToPort=3000 -cp ./bin edu.hems.RelayServerBootup

#3. Run this on PG-01 for command by command interactive session-------------------
java -DrelayHost=PG-02 -DrelayPort=3000 -cp ./bin edu.hems.relay.client.UserClient


#4.  for batch command interaction one can use
java -DrelayHost=PG-02 -DrelayPort=3000 -DmailFrom=<mailFrom-emailaddress> -DmailTo=<mailTo-emailaddress> -cp ./bin edu.hems.smtp.client.SMTPClient

## OR batch command interaction directly to smtp.gswcm.net,

java -DrelayHost=smtp.gswcm.net -DrelayPort=25 -DmailFrom=<mailFrom-emailaddress> -DmailTo=<mailTo-emailaddress> -cp ./bin edu.hems.smtp.client.SMTPClient


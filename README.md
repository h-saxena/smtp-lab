# smtp-lab


#Run this on PG-03 -------------------
java -DserverPort=3000 -DrelayToHost=smtp.gswcm.net -DrelayToPort=25 -cp ./bin edu.hems.RelayServerBootup

## OR below; just to witness hopping without interacting with smtp.gswcm.net
java -DserverPort=3000 -cp ./bin edu.hems.RelayServerBootup

#Run this on PG-02 --------------------
java -DserverPort=3000 -DrelayToHost=PG-03 -DrelayToPort=3000 -cp ./bin edu.hems.RelayServerBootup

# Run this on PG-01 -------------------
java -DrelayHost=PG-02 -DrelayPort=3000 -cp ./bin edu.hems.relay.client.UserClient


# If directly need to test interaction with smtp.gswcm.net
java -cp ./bin edu.hems.smtp.client.SMTPClient


mongod --install --serviceName "VCDB" --serviceDisplayName "VCDB" --port 27020 --dbpath "D:\MongoData\VCDB\data"  --logpath "D:\MongoData\VCDB\data\logs\vcdbDbLog.txt" --serviceDescription "Serviço do banco de dados do mongoDb para o VCDB do MAS-IoT"

mongod --port 27020 --dbpath "D:\MongoData\VCDB\data" --logpath "D:\MongoData\VCDB\data\logs\vcdbDbLog.txt"

net start "VCDB"
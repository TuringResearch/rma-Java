mkdir "@basePath\VCDB\data\logs"
mongod --install --serviceName "VCDB" --serviceDisplayName "VCDB" --port 27020 --dbpath "@basePath\VCDB\data" --logpath "@basePath\VCDB\data\logs\vcdbDbLog.txt" --serviceDescription "Serviço do banco de dados do mongoDb para o VCDB"
net start "VCDB"
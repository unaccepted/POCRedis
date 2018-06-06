# POC for Redis
# Fail-over test of Redis using Redis-Sentinel
# Fail-over and High Availability

We define high availability as the ability for the system to continue functioning after failure of one or more of the servers. A part of high availability is fail-over which we define as the ability for client connections to migrate from one server to another in event of server failure so client requests can continue to operate.

# Important Points Mentioned Below :-
Computing environments configured to provide nearly full-time availability are known as high availability systems.
When failures occur, the fail-over process moves processing performed by the failed component to the backup component. This process  recovers partial or failed transactions, and restores the system to normal.  The more transparent that fail-over is to clients, the higher the availability of the system.
High availability refers to a system or component that is continuously operational for a desirably long length of time. 

Fail-over is a means of achieving high availability (HA). Think of HA as a feature and fail-over as one possible implementation of that feature. 
Fail-over is the process of moving active services from the Master server to the slave server when the Master server fails. Usually the slave server continues these services until the master server has come back up and running. When a server fails another server takes over this process which is referred to as a fail-over. The services fail-over to the slave server which will continue from where the master server left off.

# Configuration for this POC :-
Redis sentinel configuration is made with 1 master, 2 slaves and 3 sentinels. Redis Sentinel provides high availability for Redis.  
In practical terms this means that using Sentinel, a Redis deployment can be created that resists without human intervention to certain kind of failures.  
All the redis servers including sentinel should be started before running the main program(App.java).  
Now while main program is sending request(put & get), stop the master server. After some time, one of the slave will become master and the request will successfully being responded by the server.  

You can find the above-mentioned configuration from below link:  
https://github.com/unaccepted/Redis-Sentinel_ConfSetup

# Windows  
cd redis-config/sentinel3/windows  
start-all.cmd  
->It will show that which port is master and which are slave of it  
cd redis-config/sentinel3/windows/<master-server>  
stop.cmd  

# Linux
cd redis-config/sentinel3/linux  
./start-all.sh  
->It will show that which port is master and which are slave of it  
start the application  
cd redis-config/sentinel3/linux/<master-server>  
./stop.sh




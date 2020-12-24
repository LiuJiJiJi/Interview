# Park Slot

## Base environment

+ redis: 
    + url: `127.0.0.1:6380`  
    + password: `123456`
    > sudo docker run --restart always -d --name sunary-redis -p 6380:6379 redis:5.0 --requirepass "123456" --appendonly yes
+ java 8+
+ OS: Linux/Ubuntu/Mac


## Command list

+ create_parking_lot: create_parking_lot 6                                                                 [status: finished]
+ park : park KA-01-HH-9999 White                                                                          [status: finished]
+ leave : leave 4                                                                                          [status: finished]
+ status : status                                                                                          [status: finished]
+ exit : exit                                                                                              [status: finished]
+ registration_numbers_for_cars_with_colour: registration_numbers_for_cars_with_colour White               [status: finished]
+ slot_numbers_for_cars_with_colour: slot_numbers_for_cars_with_colour White                               [status: finished]
+ slot_number_for_registration_number: slot_number_for_registration_number KA-01-HH-9999                   [status: finished]

### How to run project?

1. update **CODE_BASE_PATH** value to **/xxxx/parkingLot/** on `/parkingLot/bin/setup.sh`
2. update **APP_DEPLOY_PATH** value to **/xxxx/parkingLot/bin/** on  `/parkingLot/bin/setup.sh` & `/parkingLot/bin/parking_lot.sh`
3. sudo docker run --restart always -d --name sunary-redis -p 6380:6379 redis:5.0 --requirepass "123456" --appendonly yes
4. **sh /xxxx/parkingLot/bin/setup.sh**
5. **sh /xxxx/parkingLot/bin/parking_lot.sh  /xxxx/parkingLot/bin/file_inputs.txt**
> ps: "/xxxx" is the root path of the project

### How to improve? 

#### How to improve(Code Architecture)
+ Use SpringBoot Framework:  bean, orm(use mysql as db, use jpa as orm), log(logback),
+ Deploy to cloud: Support multiple parking lots; One service to multiple parking lots.
+ Long link/Socket: Can get real-time
status about parking lot.
+ Dashboard: Support Operation-staff/Security-guard manage his parking lot.

#### How to improve(Product)t device:
+ Can research the similar project; ETC, [科拓](http://keytop.com.cn/), [捷顺](https://www.jieshun.cn/).

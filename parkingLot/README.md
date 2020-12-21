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
+ slot_numbers_for_cars_with_colour
+ slot_number_for_registration_number

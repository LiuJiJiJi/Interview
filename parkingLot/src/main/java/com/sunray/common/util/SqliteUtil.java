package com.sunray.common.util;

import com.sunray.common.constant.ColorEnum;
import com.sunray.common.constant.DBConstant;
import com.sunray.entity.modal.ParkSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUtil {

    private static Connection connection;
    static {
        try {
            Class.forName(DBConstant.MYSQL_DRIVER_CLASS);
            connection = DriverManager.getConnection(DBConstant.MYSQL_CONNECT_URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }


    public static void main(String[] args) throws ClassNotFoundException {

        try {

            Statement statement = connection.createStatement();

            // 建表
            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer , name text)");

            // 插入数据
            statement.executeUpdate("insert into person(id, name) values(1, '刘备')");
            statement.executeUpdate("insert into person(id, name) values(2, '关羽')");
            statement.executeUpdate("insert into person(id, name) values(3, '张飞')");

            // 更新数据
            statement.executeUpdate("update person set name='诸葛亮' where id = 2");

            // 删除数据
            statement.executeUpdate("delete from person where id = 3");


            // 批处理
            String  sql = "insert into person(id, name) values(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            List<ParkSlot> list = new ArrayList<>();
            list.add(new ParkSlot(1, "Tom", ColorEnum.WHITE.value));
            list.add(new ParkSlot(2, "11", ColorEnum.WHITE.value));
            list.add(new ParkSlot(3, "Tom", ColorEnum.WHITE.value));

            for(ParkSlot person: list){
                preparedStatement.setInt(1, person.getId());
                preparedStatement.setString(2, person.getCarNumber());
                preparedStatement.setString(2, person.getColor());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            preparedStatement.close();

            // 查询数据
            ResultSet resultSet = statement.executeQuery("select * from person");
            while (resultSet.next()){
                System.out.println(resultSet.getInt("id"));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if( connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}

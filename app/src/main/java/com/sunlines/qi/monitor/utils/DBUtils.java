package com.sunlines.qi.monitor.utils;

import android.content.Context;
import android.util.Log;

import com.sunlines.qi.monitor.dao.impl.CommErrorDaoImpl;
import com.sunlines.qi.monitor.dao.impl.CoordinateDaoImpl;
import com.sunlines.qi.monitor.dao.impl.EnergyDaoImpl;
import com.sunlines.qi.monitor.dao.impl.GlobalParameterDaoImpl;
import com.sunlines.qi.monitor.dao.impl.PowerUsageEffectivenessDaoImpl;
import com.sunlines.qi.monitor.entity.CommError;
import com.sunlines.qi.monitor.entity.Energy;
import com.sunlines.qi.monitor.entity.GlobalParameter;
import com.sunlines.qi.monitor.entity.PowerUsageEffectiveness;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by temporary on 2016/11/17.
 */

public class DBUtils {
    /**
     *
     */
    public static final int CONN_ERROR_SUCCEED = 0;
    public static final int CONN_ERROR_SERVER = 1;
    public static final int CONN_ERROR_DEVICE = 2;
   /* public static final int CONN_ERROR_ENERGY = 1;
    public static final int CONN_ERROR_TEMP_CONFIG = 1;
    public static final int CONN_ERROR_TEMP = 1;
    public static final int CONN_ERROR_DEVICE_STATE = 1;*/
    /**
     *
     */
    private Context mContext;
//    private
//    private
    /**
     *
     */
    public static final int PUE_DAY = 1;
    public static final int PUE_MON = 2;
    public static final int PUE_YEAR = 3;
    public static final int PUE_PAST_DAY = 4;
    public static final int PUE_PAST_MON = 5;
    public static final int PUE_PAST_YEAR = 6;


    public static final int ENERGY_DAY_TOTAL = 1;
    public static final int ENERGY_DAY_IT = 2;
    public static final int ENERGY_MON_TOTAL = 3;
    public static final int ENERGY_MON_IT = 4;
    public static final int ENERGY_YEAR_TOTAL = 5;
    public static final int ENERGY_YEAR_IT = 6;
    public static final int ENERGY_PAST_DAY_TOTAL = 7;
    public static final int ENERGY_PAST_DAY_IT = 8;
    public static final int ENERGY_PAST_MON_TOTAL = 9;
    public static final int ENERGY_PAST_MON_IT = 10;
    public static final int ENERGY_PAST_YEAR_TOTAL = 11;
    public static final int ENERGY_PAST_YEAR_IT = 12;

    private static final int TEMP_NORMAL = 0;
    private static final int TEMP_HIGHT = 1;

    /**
     *
     */
    private String ip = "192.168.1.109";
    private String port = "1433";
    private String databaseName = "Sunlines";
    private String driver = "net.sourceforge.jtds.jdbc.Driver";
    private String url = "";
    private String username = "sa";
    private String password = "tiger";

    /**
     * @param context
     */
    public DBUtils(Context context) {
        mContext = context;
    }

    // TODO: 2017-01-09 查询服务器数据库中的PUE数据
    public void queryServerPUEData() {
        //new ConnectTask(mContext).execute();
        ip = GlobalParameterDaoImpl.queryGlobalParameter().getIp();
        url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + databaseName + "";
       // Log.e("TAG",url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResultSet rs = null;
                Connection conn = null;
                PreparedStatement statement = null;
                try {
                    Class.forName(driver);
                    conn = DriverManager.getConnection(url, username, password);
                    /**
                     *
                     */
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SUCCEED);
                    String sql = "SELECT top 1 * FROM pue WHERE floor = ? and room = ? order by dt desc";
                    /**
                     * 获得查询结果集
                     */
                    statement = conn.prepareStatement(sql);
                    GlobalParameter param = GlobalParameterDaoImpl.queryGlobalParameter();
                    statement.setObject(1, param.getFloor());
                    statement.setObject(2, param.getRoom());
                    rs = statement.executeQuery();

                    /**
                     * 更新到本地PUE表
                     */
                    update2LocalPUEData(rs);
                } catch (ClassNotFoundException e) {
                } catch (SQLException e) {
                    //e.printStackTrace();
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SERVER);
                } finally {
                    closeConn(rs, conn, statement);
                }
            }
        }).start();
    }

    // TODO: 2017-01-09 查询服务器数据库中的电能数据
    public void queryServerEnergyData() {
        //new ConnectTask(mContext).execute();
        ip = GlobalParameterDaoImpl.queryGlobalParameter().getIp();
        url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + databaseName + "";
        Log.e("TAG",url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResultSet rs = null;
                Connection conn = null;
                PreparedStatement statement = null;
                try {
                    Class.forName(driver);
                    conn = DriverManager.getConnection(url, username, password);
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SUCCEED);
                    /**
                     * 查询能耗
                     */
                    String sql = "SELECT top 1 * FROM pueDegrees WHERE floor = ? and room = ? order by dt desc";
                    /**
                     * 获得查询结果集
                     */

                    statement = conn.prepareStatement(sql);
                    GlobalParameter param = GlobalParameterDaoImpl.queryGlobalParameter();
                    statement.setObject(1, param.getFloor());
                    statement.setObject(2, param.getRoom());
                    rs = statement.executeQuery();
                    /**
                     * 更新到本地Energy表
                     */
                    update2LocalEnergyData(rs);
                } catch (ClassNotFoundException e) {
                } catch (SQLException e) {
                    //e.printStackTrace();
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SERVER);
                } finally {
                    closeConn(rs, conn, statement);
                }
            }
        }).start();
    }

    // TODO: 2017-01-09 查询服务器中最近的温度数据
    public void queryServerTemperatureData() {
        ip = GlobalParameterDaoImpl.queryGlobalParameter().getIp();
        url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + databaseName + "";
        ResultSet rs = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                ResultSet rs = null;
                PreparedStatement statement = null;
                try {
                    Class.forName(driver);
                    conn = DriverManager.getConnection(url, username, password);
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SUCCEED);
                    /**
                     * 查询对应Server中的设备名称
                     */
                    //String SQL_DISTINCT_ENAME = "SELECT DISTINCT " + CoordinateDao.Properties.SName.columnName + " FROM " + CoordinateDao.TABLENAME;

                    List<String> list = CoordinateDaoImpl.findAllDeviceNames();
                    /**
                     * 遍历设备名称，查询服务器中对应设备名称的最后一条温度记录
                     */
                    if (null != list){
                        for (String name : list) {
                            rs = null;
                            String sql = "select top 1 * from Temperature WHERE fk = '" + name + "' ORDER BY dt desc";
                            /**
                             * 获得查询结果集
                             */
                            statement = conn.prepareStatement(sql);
                            rs = statement.executeQuery();

                            /**
                             * 更新到本地温度表
                             */
                           // updateLocalTemperatureData(rs, name);
                        }
                    }
                } catch (ClassNotFoundException e) {
                } catch (SQLException e) {
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SERVER);
                } finally {
                    closeConn(rs, conn, statement);
                }
            }
        }).start();
//        return flag;
    }

    // TODO: 2017-01-09 查询服务器数据库中监测温度设备的配置表
    public void initCoordinateTable() {
        ip = GlobalParameterDaoImpl.queryGlobalParameter().getIp();
        url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + databaseName + "";
//        Log.e("TAG",url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                ResultSet rs = null;
                PreparedStatement statement = null;
                try {
                    Class.forName(driver);
                    conn = DriverManager.getConnection(url, username, password);
                    /**
                     *
                     */
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SUCCEED);
                    String sql = "select name from Equipment where temperatureswitch = 1";
                    /**
                     * 获得查询结果集
                     */
                    statement = conn.prepareStatement(sql);
                    rs = statement.executeQuery();

//                    Log.e("TAG","null == conn ? "+(null == conn));
//                    Log.e("TAG","null == statement ? "+(null == statement));
//                    Log.e("TAG","null == rs ? "+(null == rs));

                    List<String> list = new ArrayList<>();
                    while (rs.next()) {
                        String fk = rs.getString("name");
                        list.add(fk);
                    }
                    //parseServerDevices(list);

                } catch (ClassNotFoundException e) {
                } catch (SQLException e) {
                    //Log.e("TAG", "访问服务器数据库设备配置表失败");
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SERVER);
                } finally {
                    closeConn(rs, conn, statement);
                }
            }
        }).start();
//        return true;
    }

    // TODO: 2017-01-09 查询服务器数据库中所有设备状态表
    public void initCommErrorTable() {
        ip = GlobalParameterDaoImpl.queryGlobalParameter().getIp();
        url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + databaseName + "";
//        Log.e("TAG",url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                ResultSet rs = null;
                PreparedStatement statement = null;
                try {
                    Class.forName(driver);
                    conn = DriverManager.getConnection(url, username, password);
                    /**
                     *
                     */
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SUCCEED);
                    String sql = "select fk from Connection ";
                    /**
                     * 获得查询结果集
                     */
                    statement = conn.prepareStatement(sql);
                    rs = statement.executeQuery();

//                    Log.e("TAG","null == conn ? "+(null == conn));
//                    Log.e("TAG","null == statement ? "+(null == statement));
//                    Log.e("TAG","null == rs ? "+(null == rs));

                    List<String> list = new ArrayList<>();
                    while (rs.next()) {
                        String fk = rs.getString("fk");
                        list.add(fk);
                    }
                    write2CommErrorTable(list);
                } catch (ClassNotFoundException e) {
                } catch (SQLException e) {
                    //Log.e("TAG", "访问服务器数据库设备状态表失败2");
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SERVER);
                } finally {
                    closeConn(rs, conn, statement);
                }
            }
        }).start();
//        return true;
    }

    // TODO: 2017-01-09 查询服务器数据库中所有设备的配置表
    public void queryServerAllDeviceState() {
        ip = GlobalParameterDaoImpl.queryGlobalParameter().getIp();
        url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + databaseName + "";
//        Log.e("TAG",url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                ResultSet rs = null;
                PreparedStatement statement = null;
                try {
                    Class.forName(driver);
                    /**
                     *
                     */
                    CommErrorDaoImpl.disConnectServer();
                    conn = DriverManager.getConnection(url, username, password);

                    /**
                     *
                     */
                    CommErrorDaoImpl.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SUCCEED);
                    String sql = "select * from Connection ";
                    /**
                     * 获得查询结果集
                     */
                    statement = conn.prepareStatement(sql);
                    rs = statement.executeQuery();

                    while (rs.next()) {
                        String fk = rs.getString("fk");
                        int count = rs.getInt("count");
                        if (0 < count) {
                            CommErrorDaoImpl.updateCommError(fk,CONN_ERROR_DEVICE);
                        } else {
                            CommErrorDaoImpl.updateCommError(fk,CONN_ERROR_SUCCEED);
                        }
                    }


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                   // Log.e("TAG", "访问服务器数据库设备状态表失败1");
                    CommErrorDaoImpl.disConnectServer();
                    /*CommErrorUtils.updateCommError("E" + CONN_ERROR_SERVER,
                            CONN_ERROR_SERVER);*/
                } finally {
                    closeConn(rs, conn, statement);
                }

            }
        }).start();

//        return true;
    }

    private void write2CommErrorTable(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            if (name.startsWith("AC0")) {
                CommErrorDaoImpl.addCommError(new CommError(
                        IDUtils.generateId(),
                        0,
                        list.get(i),
                        "多路电表_" + name,
                        new Date()
                ));
            } else if (name.startsWith("ACT")) {
                CommErrorDaoImpl.addCommError(new CommError(
                        IDUtils.generateId(),
                        0,
                        list.get(i),
                        "电能监测仪_" + name,
                        new Date()
                ));
            }
        }
        CommErrorDaoImpl.addCommError(new CommError(
                IDUtils.generateId(),
                0,
                "E1",
                "服务器数据库",
                new Date()
        ));
    }

    // TODO: 2017-01-09  更新到本地Energy数据表中
    private void update2LocalEnergyData(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int floor = Integer.parseInt(rs.getString("floor").trim());
            int room = Integer.parseInt(rs.getString("room").trim());
//            Log.e("TAG"," floor = "+floor+" room = "+room+" daytotal = "+Float.valueOf(rs.getString("daytotal").trim()));
            Energy daytotal = new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_DAY_TOTAL,
                    rs.getFloat("daytotal"),
                    new Date()
            );
            Energy dayit = new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_DAY_IT,
                    rs.getFloat("dayit"),
                    new Date()
            );
            Energy montotal = new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_MON_TOTAL,
                    rs.getFloat("monthtotal"),
                    new Date()
            );
            Energy monit = new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_MON_IT,
                    rs.getFloat("monthit"),
                    new Date()
            );
            Energy yeartotal = new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_YEAR_TOTAL,
                    rs.getFloat("yeartotal"),
                    new Date()
            );
            Energy yearit = new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_YEAR_IT,
                    rs.getFloat("yearit"),
                    new Date()
            );
            Energy accdaytotal = new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_PAST_DAY_TOTAL,
                    rs.getFloat("accumdaytotal"),
                    new Date()
            );
//            Log.e("TAG","daytotal : value = "+daytotal.getValue()+" floor=  "+daytotal.getFloor()+ " room=  "+daytotal.getRoom()+ " type=  "+daytotal.getType());
//            Log.e("TAG","dayit : value = "+dayit.getValue()+" floor=  "+dayit.getFloor()+ " room=  "+dayit.getRoom()+ " type=  "+dayit.getType());
//            Log.e("TAG","montotal : value = "+montotal.getValue()+" floor=  "+montotal.getFloor()+ " room=  "+montotal.getRoom()+ " type=  "+montotal.getType());
//            Log.e("TAG","monit : value = "+monit.getValue()+" floor=  "+monit.getFloor()+ " room=  "+monit.getRoom()+ " type=  "+monit.getType());
//            Log.e("TAG","yeartotal : value = "+yeartotal.getValue()+" floor=  "+yeartotal.getFloor()+ " room=  "+yeartotal.getRoom()+ " type=  "+yeartotal.getType());
//            Log.e("TAG","yearit : value = "+yearit.getValue()+" floor=  "+yearit.getFloor()+ " room=  "+yearit.getRoom()+ " type=  "+yearit.getType());
//            Log.e("TAG","accdaytotal : value = "+accdaytotal.getValue()+" floor=  "+accdaytotal.getFloor()+ " room=  "+accdaytotal.getRoom()+ " type=  "+accdaytotal.getType());
            EnergyDaoImpl.addEnergy(daytotal);
            EnergyDaoImpl.addEnergy(dayit);
            EnergyDaoImpl.addEnergy(montotal);
            EnergyDaoImpl.addEnergy(monit);
            EnergyDaoImpl.addEnergy(yeartotal);
            EnergyDaoImpl.addEnergy(yearit);
            EnergyDaoImpl.addEnergy(accdaytotal);
            EnergyDaoImpl.addEnergy(new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_PAST_DAY_IT,
                    rs.getFloat("accumdayit"),
                    new Date()
            ));
            EnergyDaoImpl.addEnergy(new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_PAST_MON_TOTAL,
                    rs.getFloat("accummonthtotal"),
                    new Date()
            ));
            EnergyDaoImpl.addEnergy(new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_PAST_MON_IT,
                    rs.getFloat("accummonthit"),
                    new Date()
            ));
            EnergyDaoImpl.addEnergy(new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_PAST_YEAR_TOTAL,
                    rs.getFloat("accumyeartotal"),
                    new Date()
            ));
            EnergyDaoImpl.addEnergy(new Energy(
                    IDUtils.generateId(),
                    floor,
                    room,
                    ENERGY_PAST_YEAR_IT,
                    rs.getFloat("accumyearit"),
                    new Date()
            ));
        }
    }

    // TODO: 2017-01-09 更新到本地PUE数据表中
    private void update2LocalPUEData(ResultSet rs) throws SQLException {
        while (rs.next()) {//<CODE>ResultSet</CODE>最初指向第一行
            int floor = Integer.parseInt(rs.getString("floor").trim());
            int room = Integer.parseInt(rs.getString("room").trim());
            PowerUsageEffectivenessDaoImpl.addPowerUsageEffect(new PowerUsageEffectiveness(
                    IDUtils.generateId(),
                    floor,
                    room,
                    PUE_DAY,
                    rs.getFloat("daypue"),
                    new Date()
            ));
            PowerUsageEffectivenessDaoImpl.addPowerUsageEffect(new PowerUsageEffectiveness(
                    IDUtils.generateId(),
                    floor,
                    room,
                    PUE_MON,
                    rs.getFloat("monthpue"),
                    new Date()
            ));
            PowerUsageEffectivenessDaoImpl.addPowerUsageEffect(new PowerUsageEffectiveness(
                    IDUtils.generateId(),
                    floor,
                    room,
                    PUE_YEAR,
                    rs.getFloat("yearpue"),
                    new Date()
            ));
            PowerUsageEffectivenessDaoImpl.addPowerUsageEffect(new PowerUsageEffectiveness(
                    IDUtils.generateId(),
                    floor,
                    room,
                    PUE_PAST_DAY,
                    rs.getFloat("accumdaypue"),
                    new Date()
            ));
            PowerUsageEffectivenessDaoImpl.addPowerUsageEffect(new PowerUsageEffectiveness(
                    IDUtils.generateId(),
                    floor,
                    room,
                    PUE_PAST_MON,
                    rs.getFloat("accummonthpue"),
                    new Date()
            ));
            PowerUsageEffectivenessDaoImpl.addPowerUsageEffect(new PowerUsageEffectiveness(
                    IDUtils.generateId(),
                    floor,
                    room,
                    PUE_PAST_YEAR,
                    rs.getFloat("accumyearpue"),
                    new Date()
            ));
        }
    }

    // TODO: 2017-01-09 关闭数据库连接
    private void closeConn(ResultSet rs, Connection conn, PreparedStatement statement) {
        try {
            if (null != rs) {
                rs.close();
                rs = null;
            }
            if (null != conn) {
                conn.close();
                conn = null;
            }
            if (null != statement) {
                statement.close();
                statement = null;
            }
        } catch (SQLException e) {
           // Log.e("TAG", "数据库关闭失败");
        }
    }


    /* // TODO: 2017-01-09 查询服务器中对应的设备名称
    private List<String> listEName(DaoSession session, String sql) {
        ArrayList<String> result = new ArrayList<String>();
        Cursor c = session.getDatabase().rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }


   // TODO: 2017-01-09 初始化温度界面的行与列
    private boolean parseServerDevices(List<String> list) {
        *//***
         * 查询数据库中的表是否已经初始化
         *//*
        for (int i = 0; i < list.size(); i++) {
            String sName = list.get(i).trim();
            //Log.e("TAG","sName = "+sName);
            int lastRow = CoordinateDaoImpl.findMaxRowNum();
            int existRows = CoordinateDaoImpl.existRows(sName);
            if (sName.endsWith("_10")) {
                if (existRows < 2) {
                    lastRow++;
                    write2CoordinateTable(lastRow, sName, 11);
                }
            } else {
                if (existRows <= 2) {
                    for (int j = 1; j < 3; j++) {
                        *//**
                         * 查询数据库中的表是否已经初始化
                         *//*
                        lastRow++;
                        if (j == 1) {
                            write2CoordinateTable(lastRow, sName, 12);
                        } else {
                            write2CoordinateTable(lastRow, sName, 11);
                        }
                    }
                }
            }


        }
        return true;
    }

    // TODO: 2016-12-26  往设备配置表中写入行数据
    private void write2CoordinateTable(int row, String sName, int len) {
        *//**
         *
         *//*
        Coordinate coordinate = new Coordinate();
        coordinate.setRow(row);
        coordinate.setCol(0);
        coordinate.setName("二楼IDC机房IDF-" + row + "列设备柜");
        coordinate.setSName(sName);
        coordinate.setId(IDUtils.generateId());
        CoordinateDaoImpl.addCoordinate(coordinate);
        *//**
         *
         *//*
        for (int col = 1; col < len; col++) {
            Coordinate coordinate1 = new Coordinate();
            coordinate1.setRow(row);
            coordinate1.setCol(col);
            coordinate1.setName("二楼IDC机房IDF-" + row + "列设备柜IDF" + col);
            coordinate1.setSName(sName);
            *//**
             * 生成设备配置表中的数据的ID
             *//*
            long id = IDUtils.generateId();
            coordinate1.setId(id);
            CoordinateDaoImpl.addCoordinate(coordinate1);
            *//**
             * 初始化本地温度告警数据
             *//*
            initLocalWarning(id);
            *//**
             * 初始化本地温度缓存数据
             *//*
            initLocalTemperature(id);
        }

    }

    // TODO: 2016-12-26 给对应外键初始化四个告警温度
    private void initLocalWarning(long id) {
        for (int i = 0; i < 4; i++) {
            WarningSetting setting = new WarningSetting();
            setting.setFk(id);
            setting.setRid(i + 1);
            setting.setTemp(60.0f);
            setting.setState(0);
            setting.setId(IDUtils.generateId());
            setting.setMDate(new Date());
            WarningSettingDaoImpl.addWarningSetting(setting);
        }
    }

    // TODO: 2016-12-26 给对应外键初始化四个温度
    private void initLocalTemperature(long fk) {
        String prefix = "";
        for (int i = 0; i < 4; i++) {
            Temperature temperature = new Temperature();
            temperature.setFk(fk);
            temperature.setRid(i + 1);
            temperature.setTemp(30.0f);
            temperature.setId((long) (Math.random() * (Integer.MAX_VALUE)));
            if (i == 0) {
                prefix = "进风一";
            } else if (i == 1) {
                prefix = "进风二";
            } else if (i == 2) {
                prefix = "出风一";
            } else if (i == 3) {
                prefix = "出风二";
            }
            temperature.setName(prefix);
            TemperatureDaoImpl.updateTemperatureTable(temperature);
        }
    }

    // TODO: 2017-01-09 处理从服务器得到的温度数据，更新到本地数据库
    private void updateLocalTemperatureData(ResultSet rs, String name) {
        try {
            while (rs.next()) {
                float t1_1 = rs.getFloat(3);
                float t1_2 = rs.getFloat(4);
                float t1_3 = rs.getFloat(5);
                float t1_4 = rs.getFloat(6);
                float t1_5 = rs.getFloat(7);
                float t1_6 = rs.getFloat(8);
                float t1_7 = rs.getFloat(9);
                float t1_8 = rs.getFloat(10);
                float t2_1 = rs.getFloat(11);
                float t2_2 = rs.getFloat(12);
                float t2_3 = rs.getFloat(13);
                float t2_4 = rs.getFloat(14);
                float t2_5 = rs.getFloat(15);
                float t2_6 = rs.getFloat(16);
                float t2_7 = rs.getFloat(17);
                float t2_8 = rs.getFloat(18);
                float t3_1 = rs.getFloat(19);
                float t3_2 = rs.getFloat(20);
                float t3_3 = rs.getFloat(21);
                float t3_4 = rs.getFloat(22);
                float t3_5 = rs.getFloat(23);
                float t3_6 = rs.getFloat(24);
                float t3_7 = rs.getFloat(25);
                float t3_8 = rs.getFloat(26);
                float t4_1 = rs.getFloat(27);
                float t4_2 = rs.getFloat(28);
                float t4_3 = rs.getFloat(29);
                float t4_4 = rs.getFloat(30);
                float t4_5 = rs.getFloat(31);
                float t4_6 = rs.getFloat(32);
                float t4_7 = rs.getFloat(33);
                float t4_8 = rs.getFloat(34);
                float t5_1 = rs.getFloat(35);
                float t5_2 = rs.getFloat(36);
                float t5_3 = rs.getFloat(37);
                float t5_4 = rs.getFloat(38);
                float t5_5 = rs.getFloat(39);
                float t5_6 = rs.getFloat(40);
                float t5_7 = rs.getFloat(41);
                float t5_8 = rs.getFloat(42);
                float t6_1 = rs.getFloat(43);
                float t6_2 = rs.getFloat(44);
                float t6_3 = rs.getFloat(45);
                float t6_4 = rs.getFloat(46);
                float t6_5 = rs.getFloat(47);
                float t6_6 = rs.getFloat(48);
                float t6_7 = rs.getFloat(49);
                float t6_8 = rs.getFloat(50);
                float t7_1 = rs.getFloat(51);
                float t7_2 = rs.getFloat(52);
                float t7_3 = rs.getFloat(53);
                float t7_4 = rs.getFloat(54);
                float t7_5 = rs.getFloat(55);
                float t7_6 = rs.getFloat(56);
                float t7_7 = rs.getFloat(57);
                float t7_8 = rs.getFloat(58);
                float t8_1 = rs.getFloat(59);
                float t8_2 = rs.getFloat(60);
                float t8_3 = rs.getFloat(61);
                float t8_4 = rs.getFloat(62);
                float t8_5 = rs.getFloat(63);
                float t8_6 = rs.getFloat(64);
                float t8_7 = rs.getFloat(65);
                float t8_8 = rs.getFloat(66);
                float t9_1 = rs.getFloat(67);
                float t9_2 = rs.getFloat(68);
                float t9_3 = rs.getFloat(69);
                float t9_4 = rs.getFloat(70);
                float t9_5 = rs.getFloat(71);
                float t9_6 = rs.getFloat(72);
                float t9_7 = rs.getFloat(73);
                float t9_8 = rs.getFloat(74);
                float t10_1 = rs.getFloat(75);
                float t10_2 = rs.getFloat(76);
                float t10_3 = rs.getFloat(77);
                float t10_4 = rs.getFloat(78);
                float t10_5 = rs.getFloat(79);
                float t10_6 = rs.getFloat(80);
                float t10_7 = rs.getFloat(81);
                float t10_8 = rs.getFloat(82);
                float t11_1 = rs.getFloat(83);
                float t11_2 = rs.getFloat(84);
                float t11_3 = rs.getFloat(85);
                float t11_4 = rs.getFloat(86);
                if (!name.endsWith("_10")) {
                    int row1 = CoordinateDaoImpl.findMaxRowNumByName(name);
                    for (int i = 0; i < 10; i++) {
                        long fk =CoordinateDaoImpl.findId(row1, i + 1);
                        if (-1 != fk) {
                            for (int j = 0; j < 4; j++) {
                                Temperature temp = TemperatureDaoImpl.findTemperature(fk, j + 1);
                                if (null != temp) {
                                    WarningSetting setting = WarningSettingDaoImpl.findWarningSetting(fk, j + 1);
                                    if (i == 0 && j == 0) {
                                        temp.setTemp(t1_1);
                                    } else if (i == 0 && j == 1) {
                                        temp.setTemp(t1_2);
                                    } else if (i == 0 && j == 2) {
                                        temp.setTemp(t1_3);
                                    } else if (i == 0 && j == 3) {
                                        temp.setTemp(t1_4);
                                    } else if (i == 1 && j == 0) {
                                        temp.setTemp(t1_5);
                                    } else if (i == 1 && j == 1) {
                                        temp.setTemp(t1_6);
                                    } else if (i == 1 && j == 2) {
                                        temp.setTemp(t1_7);
                                    } else if (i == 1 && j == 3) {
                                        temp.setTemp(t1_8);
                                    } else if (i == 2 && j == 0) {
                                        temp.setTemp(t2_1);
                                    } else if (i == 2 && j == 1) {
                                        temp.setTemp(t2_2);
                                    } else if (i == 2 && j == 2) {
                                        temp.setTemp(t2_3);
                                    } else if (i == 2 && j == 3) {
                                        temp.setTemp(t2_4);
                                    } else if (i == 3 && j == 0) {
                                        temp.setTemp(t2_5);
                                    } else if (i == 3 && j == 1) {
                                        temp.setTemp(t2_6);
                                    } else if (i == 3 && j == 2) {
                                        temp.setTemp(t2_7);
                                    } else if (i == 3 && j == 3) {
                                        temp.setTemp(t2_8);
                                    } else if (i == 4 && j == 0) {
                                        temp.setTemp(t3_1);
                                    } else if (i == 4 && j == 1) {
                                        temp.setTemp(t3_2);
                                    } else if (i == 4 && j == 2) {
                                        temp.setTemp(t3_3);
                                    } else if (i == 4 && j == 3) {
                                        temp.setTemp(t3_4);
                                    } else if (i == 5 && j == 0) {
                                        temp.setTemp(t3_5);
                                    } else if (i == 5 && j == 1) {
                                        temp.setTemp(t3_6);
                                    } else if (i == 5 && j == 2) {
                                        temp.setTemp(t3_7);
                                    } else if (i == 5 && j == 3) {
                                        temp.setTemp(t3_8);
                                    } else if (i == 6 && j == 0) {
                                        temp.setTemp(t4_1);
                                    } else if (i == 6 && j == 1) {
                                        temp.setTemp(t4_2);
                                    } else if (i == 6 && j == 2) {
                                        temp.setTemp(t4_3);
                                    } else if (i == 6 && j == 3) {
                                        temp.setTemp(t4_4);
                                    } else if (i == 7 && j == 0) {
                                        temp.setTemp(t4_5);
                                    } else if (i == 7 && j == 1) {
                                        temp.setTemp(t4_6);
                                    } else if (i == 7 && j == 2) {
                                        temp.setTemp(t4_7);
                                    } else if (i == 7 && j == 3) {
                                        temp.setTemp(t4_8);
                                    } else if (i == 8 && j == 0) {
                                        temp.setTemp(t5_1);
                                    } else if (i == 8 && j == 1) {
                                        temp.setTemp(t5_2);
                                    } else if (i == 8 && j == 2) {
                                        temp.setTemp(t5_3);
                                    } else if (i == 8 && j == 3) {
                                        temp.setTemp(t5_4);
                                    } else if (i == 9 && j == 0) {
                                        temp.setTemp(t5_5);
                                    } else if (i == 9 && j == 1) {
                                        temp.setTemp(t5_6);
                                    } else if (i == 9 && j == 2) {
                                        temp.setTemp(t5_7);
                                    } else if (i == 9 && j == 3) {
                                        temp.setTemp(t5_8);
                                    }
                                    if (temp.getTemp() > 100.0f) {
                                        temp.setTemp(0);
                                    }
                                    TemperatureDaoImpl.updateTemperatureTable(temp);

                                    if (setting.getTemp() <= temp.getTemp() && setting.getState() == TEMP_NORMAL) {
                                        setting.setState(TEMP_HIGHT);
                                    } else if (setting.getTemp() > temp.getTemp() && setting.getState() == TEMP_HIGHT) {
                                        setting.setState(TEMP_NORMAL);
                                    }
                                    WarningSettingDaoImpl.updateWarningSettingTable(setting);
                                }
                                //Log.e("TAG", " fk = " + temp.getFk() + " row = " + row1 + " col = " + (i + 1) + " rid = " + (j + 1) + " temp.rid = " + temp.getRid() + " value = " + temp.getTemp());
                            }
                        }
                    }
                    int row2 = CoordinateDaoImpl.findMinRowNumByName(name);
                    for (int i = 0; i < 11; i++) {
                        long fk = CoordinateDaoImpl.findId(row2, i + 1);
                        if (-1 != fk) {
                            for (int j = 0; j < 4; j++) {
                                Temperature temp = TemperatureDaoImpl.findTemperature(fk, j + 1);
                                if (null != temp) {
                                    WarningSetting setting = WarningSettingDaoImpl.findWarningSetting(fk, j + 1);
                                    if (i == 0 && j == 0) {
                                        temp.setTemp(t11_1);
                                    } else if (i == 0 && j == 1) {
                                        temp.setTemp(t11_2);
                                    } else if (i == 0 && j == 2) {
                                        temp.setTemp(t11_3);
                                    } else if (i == 0 && j == 3) {
                                        temp.setTemp(t11_4);
                                    } else if (i == 1 && j == 0) {
                                        temp.setTemp(t10_5);
                                    } else if (i == 1 && j == 1) {
                                        temp.setTemp(t10_6);
                                    } else if (i == 1 && j == 2) {
                                        temp.setTemp(t10_7);
                                    } else if (i == 1 && j == 3) {
                                        temp.setTemp(t10_8);
                                    } else if (i == 2 && j == 0) {
                                        temp.setTemp(t10_1);
                                    } else if (i == 2 && j == 1) {
                                        temp.setTemp(t10_2);
                                    } else if (i == 2 && j == 2) {
                                        temp.setTemp(t10_3);
                                    } else if (i == 2 && j == 3) {
                                        temp.setTemp(t10_4);
                                    } else if (i == 3 && j == 0) {
                                        temp.setTemp(t9_5);
                                    } else if (i == 3 && j == 1) {
                                        temp.setTemp(t9_6);
                                    } else if (i == 3 && j == 2) {
                                        temp.setTemp(t9_7);
                                    } else if (i == 3 && j == 3) {
                                        temp.setTemp(t9_8);
                                    } else if (i == 4 && j == 0) {
                                        temp.setTemp(t9_1);
                                    } else if (i == 4 && j == 1) {
                                        temp.setTemp(t9_2);
                                    } else if (i == 4 && j == 2) {
                                        temp.setTemp(t9_3);
                                    } else if (i == 4 && j == 3) {
                                        temp.setTemp(t9_4);
                                    } else if (i == 5 && j == 0) {
                                        temp.setTemp(t8_5);
                                    } else if (i == 5 && j == 1) {
                                        temp.setTemp(t8_6);
                                    } else if (i == 5 && j == 2) {
                                        temp.setTemp(t8_7);
                                    } else if (i == 5 && j == 3) {
                                        temp.setTemp(t8_8);
                                    } else if (i == 6 && j == 0) {
                                        temp.setTemp(t8_1);
                                    } else if (i == 6 && j == 1) {
                                        temp.setTemp(t8_2);
                                    } else if (i == 6 && j == 2) {
                                        temp.setTemp(t8_3);
                                    } else if (i == 6 && j == 3) {
                                        temp.setTemp(t8_4);
                                    } else if (i == 7 && j == 0) {
                                        temp.setTemp(t7_5);
                                    } else if (i == 7 && j == 1) {
                                        temp.setTemp(t7_6);
                                    } else if (i == 7 && j == 2) {
                                        temp.setTemp(t7_7);
                                    } else if (i == 7 && j == 3) {
                                        temp.setTemp(t7_8);
                                    } else if (i == 8 && j == 0) {
                                        temp.setTemp(t7_1);
                                    } else if (i == 8 && j == 1) {
                                        temp.setTemp(t7_2);
                                    } else if (i == 8 && j == 2) {
                                        temp.setTemp(t7_3);
                                    } else if (i == 8 && j == 3) {
                                        temp.setTemp(t7_4);
                                    } else if (i == 9 && j == 0) {
                                        temp.setTemp(t6_5);
                                    } else if (i == 9 && j == 1) {
                                        temp.setTemp(t6_6);
                                    } else if (i == 9 && j == 2) {
                                        temp.setTemp(t6_7);
                                    } else if (i == 9 && j == 3) {
                                        temp.setTemp(t6_8);
                                    } else if (i == 10 && j == 0) {
                                        temp.setTemp(t6_1);
                                    } else if (i == 10 && j == 1) {
                                        temp.setTemp(t6_2);
                                    } else if (i == 10 && j == 2) {
                                        temp.setTemp(t6_3);
                                    } else if (i == 10 && j == 3) {
                                        temp.setTemp(t6_4);
                                    }
                                    if (temp.getTemp() > 100.0f) {
                                        temp.setTemp(0);
                                    }
                                    TemperatureDaoImpl.updateTemperatureTable(temp);

                                    if (setting.getTemp() <= temp.getTemp() && setting.getState() == TEMP_NORMAL) {
                                        setting.setState(TEMP_HIGHT);
                                    } else if (setting.getTemp() > temp.getTemp() && setting.getState() == TEMP_HIGHT) {
                                        setting.setState(TEMP_NORMAL);
                                    }
                                    WarningSettingDaoImpl.updateWarningSettingTable(setting);
                                }
                                //Log.e("TAG", " fk = " + temp.getFk() + " row = " + row2 + " col = " + (i + 1) + " rid = " + (j + 1) + " temp.rid = " + temp.getRid() + " value = " + temp.getTemp());
                            }
                        }
                    }
                } else {
                    int row1 = CoordinateDaoImpl.findMaxRowNumByName(name);
                    for (int i = 0; i < 10; i++) {
                        long fk = CoordinateDaoImpl.findId(row1, i + 1);
                        if (-1 != fk) {
                            for (int j = 0; j < 4; j++) {
                                Temperature temp = TemperatureDaoImpl.findTemperature(fk, j + 1);
                                if (null != temp) {
                                    WarningSetting setting = WarningSettingDaoImpl.findWarningSetting(fk, j + 1);
                                    if (i == 0 && j == 0) {
                                        temp.setTemp(t1_1);
                                    } else if (i == 0 && j == 1) {
                                        temp.setTemp(t1_2);
                                    } else if (i == 0 && j == 2) {
                                        temp.setTemp(t1_3);
                                    } else if (i == 0 && j == 3) {
                                        temp.setTemp(t1_4);
                                    } else if (i == 1 && j == 0) {
                                        temp.setTemp(t1_5);
                                    } else if (i == 1 && j == 1) {
                                        temp.setTemp(t1_6);
                                    } else if (i == 1 && j == 2) {
                                        temp.setTemp(t1_7);
                                    } else if (i == 1 && j == 3) {
                                        temp.setTemp(t1_8);
                                    } else if (i == 2 && j == 0) {
                                        temp.setTemp(t2_1);
                                    } else if (i == 2 && j == 1) {
                                        temp.setTemp(t2_2);
                                    } else if (i == 2 && j == 2) {
                                        temp.setTemp(t2_3);
                                    } else if (i == 2 && j == 3) {
                                        temp.setTemp(t2_4);
                                    } else if (i == 3 && j == 0) {
                                        temp.setTemp(t2_5);
                                    } else if (i == 3 && j == 1) {
                                        temp.setTemp(t2_6);
                                    } else if (i == 3 && j == 2) {
                                        temp.setTemp(t2_7);
                                    } else if (i == 3 && j == 3) {
                                        temp.setTemp(t2_8);
                                    } else if (i == 4 && j == 0) {
                                        temp.setTemp(t3_1);
                                    } else if (i == 4 && j == 1) {
                                        temp.setTemp(t3_2);
                                    } else if (i == 4 && j == 2) {
                                        temp.setTemp(t3_3);
                                    } else if (i == 4 && j == 3) {
                                        temp.setTemp(t3_4);
                                    } else if (i == 5 && j == 0) {
                                        temp.setTemp(t3_5);
                                    } else if (i == 5 && j == 1) {
                                        temp.setTemp(t3_6);
                                    } else if (i == 5 && j == 2) {
                                        temp.setTemp(t3_7);
                                    } else if (i == 5 && j == 3) {
                                        temp.setTemp(t3_8);
                                    } else if (i == 6 && j == 0) {
                                        temp.setTemp(t4_1);
                                    } else if (i == 6 && j == 1) {
                                        temp.setTemp(t4_2);
                                    } else if (i == 6 && j == 2) {
                                        temp.setTemp(t4_3);
                                    } else if (i == 6 && j == 3) {
                                        temp.setTemp(t4_4);
                                    } else if (i == 7 && j == 0) {
                                        temp.setTemp(t4_5);
                                    } else if (i == 7 && j == 1) {
                                        temp.setTemp(t4_6);
                                    } else if (i == 7 && j == 2) {
                                        temp.setTemp(t4_7);
                                    } else if (i == 7 && j == 3) {
                                        temp.setTemp(t4_8);
                                    } else if (i == 8 && j == 0) {
                                        temp.setTemp(t5_1);
                                    } else if (i == 8 && j == 1) {
                                        temp.setTemp(t5_2);
                                    } else if (i == 8 && j == 2) {
                                        temp.setTemp(t5_3);
                                    } else if (i == 8 && j == 3) {
                                        temp.setTemp(t5_4);
                                    } else if (i == 9 && j == 0) {
                                        temp.setTemp(t5_5);
                                    } else if (i == 9 && j == 1) {
                                        temp.setTemp(t5_6);
                                    } else if (i == 9 && j == 2) {
                                        temp.setTemp(t5_7);
                                    } else if (i == 9 && j == 3) {
                                        temp.setTemp(t5_8);
                                    }
                                    if (temp.getTemp() > 100.0f) {
                                        temp.setTemp(0);
                                    }
                                    TemperatureDaoImpl.updateTemperatureTable(temp);

                                    if (setting.getTemp() <= temp.getTemp() && setting.getState() == TEMP_NORMAL) {
                                        setting.setState(TEMP_HIGHT);
                                    } else if (setting.getTemp() > temp.getTemp() && setting.getState() == TEMP_HIGHT) {
                                        setting.setState(TEMP_NORMAL);
                                    }
                                    WarningSettingDaoImpl.updateWarningSettingTable(setting);
                                }
                                // Log.e("TAG", " fk = " + temp.getFk() + " row = " + row1 + " col = " + (i + 1) + " rid = " + (j + 1) + " temp.rid = " + temp.getRid() + " value = " + temp.getTemp());
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

}


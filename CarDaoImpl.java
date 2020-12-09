import com.sun.xml.internal.ws.wsdl.writer.document.http.Address;

import java.sql.*;

public interface CarDao {
    public List<Vehicle> findAllVehicle(Customer customer) throws Exception;
    public void insertVehicle(String vehicleID,String dealerId,String year,String brand,
            String model,Customer customer) throws Exception;
    public void deleteVehicle(String vehicleID,Customer customer) throws Exception;
}

public class CarDaoImpl extends BaseDao implements CarDao{
    public List<Vehicle> findAllVehicle(Customer customer) throws Exception {    //select无法自行构建vehicle
        Connection conn=BaseDao.getConnection();
        String sql="select vehicleID,dealerId,year,brand,model from Car where customerID ="+customer.getID();
        PreparedStatement stmt= conn.prepareStatement(sql);
        ResultSet rs=  stmt.executeQuery();
        List<Vehicle> vehicleList=new ArrayList<Vehicle>();
        while(rs.next()) {
            Vehicle cars=new Vehicle(
                    vehicleID,   //注意get--- 有问题后续补充
                    dealerId,
                    year,
                    brand,
                    model
            );
            vehicleList.add(cars);
        }
        BaseDao.closeAll(conn, stmt, rs);
        return vehicleList;
    }
    public void insertCars(String vehicleID,String dealerId,String year,String brand,
                           String model,Customer customer) throws Exception{
        Connection conn=BaseDao.getConnection();
        // the mysql insert statement
        String insertCarsItems = "insert into users (customerID,vehicleID,dealerId,year,brand,model)"
                + " values (?, ? ,? ,? , ?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement stmt = conn.prepareStatement(insertCarsItems);
        stmt.setString (1, customer.getID());
        stmt.setString (2, vehicleID);
        stmt.setString   (3, dealerId);
        stmt.setString   (4, year);
        stmt.setString   (5, brand);
        stmt.setString   (6, model);
        stmt.execute();
        BaseDao.closeConnStat(conn,stmt);
    }
    public void deleteCar(String vehicleID,Customer customer) throws Exception{
        Connection conn=BaseDao.getConnection();
        // create the mysql delete statement.
        String deleteCarItems = "delete from Car where customerID = ? and vehicleID = ?";
        PreparedStatement stmt = conn.prepareStatement(deleteCarItems);
        stmt.setString(1, customer.getID());
        stmt.setString(2, vehicleID);
        // execute the preparedstatement
        stmt.execute();
        BaseDao.closeConnStat(conn,stmt);
    }
}

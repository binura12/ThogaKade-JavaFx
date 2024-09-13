package util;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T execute(String SQL, Object... val) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(SQL);

        if(SQL.startsWith("select") || SQL.startsWith("SELECT")){
            return (T) psTm.executeQuery();
        }else{
            for (int i=0;i< val.length;i++){
                psTm.setObject(i+1,val[i]);
            }
        }
        return (T) (Boolean) (psTm.executeUpdate()>0);
    }
}

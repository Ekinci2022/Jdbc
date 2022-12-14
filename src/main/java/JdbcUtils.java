import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {

    public static void main(String[] args) {
        createTable("abc","name VARCHAR(10)","id INT","adress VARCHAR(80)");
    }

    private static Connection connection;
    private static Statement statement;


    //1. Adım: Driver'a kaydol
    //2. Adım: Datbase'e bağlan
    public static Connection connectToDataBase(String hostName, String dbName,String username, String password)  {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            connection = DriverManager.getConnection("jdbc:postgresql://"+hostName+":5432/"+dbName,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(connection!=null){
            System.out.println("Connection Success");
        }else {
            System.out.println("Connection Fail");
        }

        return connection;
    }

    //3. Adım: Statement oluştur.
    public static Statement createStatement(){


        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return statement;
    }

    //4. Adım: Query çalıştır.
    public static boolean execute(String sql){
        boolean isExecute;
        try {
            isExecute = statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isExecute;
    }

    //5. Adım: Bağlantı ve Statement'ı kapat.
    public static void closeConnectionAndStatement(){

        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(connection.isClosed()&&statement.isClosed()){
                System.out.println("Connection and statement closed!");

            }else {
                System.out.println("Connection and statement NOT closed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


// Table olusturan method
    public static void createTable(String tableNames, String... columnName_dataType){

        StringBuilder columnName_dataValue = new StringBuilder("");

        for(String w : columnName_dataType) {


            columnName_dataValue.append(w).append(",");
        }

        columnName_dataValue.deleteCharAt(columnName_dataValue.length()-1);
        System.out.println(columnName_dataValue);
        try {
            statement.execute( "CREATE TABLE "+tableNames+"("+columnName_dataValue+")");
            System.out.println("Table "+tableNames+" successfully created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }







}
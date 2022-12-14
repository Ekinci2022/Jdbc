import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Execute01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1. Adım: Driver'a kaydol
        Class.forName("org.postgresql.Driver");

        //2. Adım: Datbase'e bağlan
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "tornado");

        //3. Adım: Statement oluştur.
        Statement st = con.createStatement();

        //4. Adım: Query çalıştır.
                                /*
execute() methodu DDL(create,drop,alter table) ve DQL(select) icin kullanilabilir
1) Eger execute() methodu DDL icin kullanilirsa 'false' return eder
2) Eger execute() methodu DQL icin kullanilirsa ResultSet alindiginda 'true' aksi halde 'false' return eder


 */
        //1.ornek

        boolean sql1 = st.execute(" CREATE TABLE workers(worker_id VARCHAR(20), worker_name VARCHAR(20), worker_salary INT)");
        System.out.println("sql1 =" + sql1);//false return eder çünkü data çağırmıyoruz.



        // 2.ornek: table a worker_adress sutunu ekleyerek alter yapiniz
        String sql2 = "alter table workers add worker_adress VARCHAR(80)";
        st.execute(sql2);

        //3.ornek : workers table i silin

        String sql3 = "drop table workers";
        st.execute(sql3);
        //5. Adım: Bağlantı ve Statement'ı kapat.
   con.close();
   st.close();
    }
}

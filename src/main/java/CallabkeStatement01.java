import java.sql.*;

public class CallabkeStatement01 {


    /*
    Java'da method'lar return type sahibi olsa da olmasa da method olarak adlandırılır.
    SQL'de ise data return ediyorsa "function" denir. Return yapmiyorsa "procedure" olarak adlandırilir
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "tornado");
        Statement st = con.createStatement();

        // CallableStatement ile function cagirmayi parametrelendirecegiz.

        //1. Adım: Function kodunu yaz.
        String sql1 = "CREATE OR REPLACE FUNCTION toplamaF(x NUMERIC, y NUMERIC) RETURNS NUMERIC LANGUAGE plpgsql AS $$ BEGIN RETURN x+y; END $$";
//2. Adım: Function'i calistir.
        st.execute(sql1);
//3. Adim : Fonction'i cagir.

        CallableStatement cst1 = con.prepareCall("{? = call toplamaF(?,?)}");

        //4. adim : Return icin registerOurParameter() ,ethodunu, parametreler icin ise set() ... methodlarini uygula

        cst1.registerOutParameter(1,Types.NUMERIC);
        cst1.setInt(2,6);
        cst1.setInt(3,4);

        // 5. Adim : execute () methodu ile CallableStatement i calistir.

        cst1.execute();


        //6.Adim: Sonucu cagirmak icin return data type tipine göre
        System.out.println(cst1.getBigDecimal(1));

        ///2. örnek : Koninin hacmini hesaplayan bir function yazin...
        //1.Adim; Function kodunu yaz

        String sql2 = "CREATE OR REPLACE FUNCTION  toplamaF(x NUMERIC, y NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN\n" +
                "\n" +
                "RETURN 3.14*r*r*h/3;\n" +
                "\n" +
                "END\n" +
                "$$";

        //2.Adim : Functionu callistir
        st.execute(sql2);

        //3.Adim : Functionu cagir

        CallableStatement cst2 = con.prepareCall("{? = call toplamaF(?,?)}");

        //4. Adım: Return için registerOurParameter() methodunu, parametreler için ise set() ... methodlarını uygula.
        cst2.registerOutParameter(1, Types.NUMERIC);
        cst2.setInt(2, 1);
        cst2.setInt(3, 6);

//5. Adım: execute() methodu ile CallableStatement'ı çalıştır.
        cst2.execute();

        //6.Adim : sonucu cagirmak icin return data type tipine gore
        System.out.printf("%.2f", cst2.getBigDecimal(1));

    }
}

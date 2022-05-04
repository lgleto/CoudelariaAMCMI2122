namespace CoudelariaAPI;

using MySql.Data.MySqlClient;

public class DataBaseConnection
{
    private MySqlConnection con;
    
    public DataBaseConnection()
    {
        string cs = @"server=localhost;userid=root;password=12345678;database=coudelaria";
        con = new MySqlConnection(cs);
        con.Open();
    }
    
    public MySqlDataReader DbQuery(String query)
    {
        var cmd = new MySqlCommand(query, con);
        return cmd.ExecuteReader();
    }
    
    public void Close()
    {
        con.Close();
    }
    

}
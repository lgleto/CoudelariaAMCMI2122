namespace CoudelariaAPI.Models;

public class Cavalo
{
    public int      Cod_Cavalo           { get; set;}
    public string   Nome_Cavalo          { get; set;}
    public DateTime Data_Nascimento      { get; set;}
    public string   Genero               { get; set; }
    public int?     Pai                  { get; set; }
    public int?     Mae                  { get; set; }
    public int?     Cod_Coudelaria_Nasc  { get; set; }
    public int      Cod_Coudelaria_Resid { get; set; }

    public Cavalo()
    {
    }

    public static List<Cavalo> GetAllItems()
    {
        List<Cavalo> cavalos = new List<Cavalo>();

        var dbCon = new DataBaseConnection();
        var reader = dbCon.DbQuery("SELECT * FROM cavalos;");
        
        while (reader.Read())
        {
            var cavalo = new Cavalo();
            cavalo.Cod_Cavalo           =  reader.GetInt32 (0);
            cavalo.Nome_Cavalo          = reader.GetString(1);
            //cavalo.Data_Nascimento      = reader.GetString(2);
            cavalo.Genero               = reader.GetString(3);
            cavalo.Pai                  = reader.IsDBNull(4) ? null : reader.GetInt32 (4);
            cavalo.Mae                  = reader.IsDBNull(5) ? null :reader.GetInt32 (5);
            cavalo.Cod_Coudelaria_Nasc  = reader.IsDBNull(6) ? null :reader.GetInt32 (6);
            cavalo.Cod_Coudelaria_Resid = reader.GetInt32 (7);
       
            cavalos.Add(cavalo);
        }

        dbCon.Close();
        return cavalos;
    }

    public static Cavalo? GetItem(string id)
    {
        var dbCon = new DataBaseConnection();
        var reader = dbCon.DbQuery("SELECT * FROM cavalos WHERE Cod_Cavalo = " + id + ";");
        if (reader.Read())
        {
            var cavalo = new Cavalo();
            cavalo.Cod_Cavalo = reader.GetInt32(0);
            cavalo.Nome_Cavalo = reader.GetString(1);
            //cavalo.Data_Nascimento      = reader.GetString(2);
            cavalo.Genero = reader.GetString(3);
            cavalo.Pai = reader.IsDBNull(4) ? null : reader.GetInt32(4);
            cavalo.Mae = reader.IsDBNull(5) ? null : reader.GetInt32(5);
            cavalo.Cod_Coudelaria_Nasc = reader.IsDBNull(6) ? null : reader.GetInt32(6);
            cavalo.Cod_Coudelaria_Resid = reader.GetInt32(7);
            dbCon.Close();
            return cavalo;
        }
        else
        {
            dbCon.Close();
            return null;
        }

        
    }

    public static string Create(Cavalo cavalo)
    {
        var dbCon = new DataBaseConnection();
        var result = dbCon.DBNonQuery(
            "INSERT INTO cavalos (Cod_Cavalo, Nome_Cavalo, Genero, Pai, Mae, Cod_Coudelaria_Nasc, Cod_Coudelaria_Resid) VALUES ('" +
            cavalo.Cod_Cavalo + "', '" +
            cavalo.Nome_Cavalo + "', '" +
            //cavalo.Data_Nascimento.ToString() + "', '" +
            cavalo.Genero + "', '" +
            cavalo.Pai + "', '" +
            cavalo.Mae + "', '" +
            cavalo.Cod_Coudelaria_Nasc + "', '" +
            cavalo.Cod_Coudelaria_Resid +
            "');");
        dbCon.Close();
        if (result > 0)
        {
            return "{ \"status\" :\"ok\" }";
        }
        else
        {
            return "{ \"status\" :\"error\" }";
        }
    }

    public static string Update(string id, Cavalo cavalo)
    {
        var dbCon = new DataBaseConnection();

        
        String strQuery =
            "UPDATE cavalos  SET " +
            "Nome_Cavalo          = '" + cavalo.Nome_Cavalo           +"', "+
            "Data_Nascimento      = '" + cavalo.Data_Nascimento       +"', "+
            "Genero               = '" + cavalo.Genero                +"', "+
            "Pai                  = '" + cavalo.Pai                   +"', "+
            "Mae                  = '" + cavalo.Mae                   +"', "+
            "Cod_Coudelaria_Nasc  = '" + cavalo.Cod_Coudelaria_Nasc   +"', "+
            "Cod_Coudelaria_Resid = '" + cavalo.Cod_Coudelaria_Resid  +"' "+
            "WHERE Cod_Cavalo = '" + id + "'";
        var result = dbCon.DBNonQuery(strQuery);
        
        dbCon.Close();
        if (result > 0)
        {
            return "{ \"status\" :\"ok\" }";
        }
        else
        {
            return "{ \"status\" :\"error\" }";
        }
    }

    public static string Delete(string id)
    {
        var dbCon = new DataBaseConnection();

        
        String strQuery =
            "DELETE FROM cavalos " +
            "WHERE Cod_Cavalo = " + id + ";";

        var result = dbCon.DBNonQuery(strQuery);
        
        dbCon.Close();
        if (result > 0)
        {
            return "{ \"status\" :\"ok\" }";
        }
        else
        {
            return "{ \"status\" :\"error\" }";
        }
    }
}
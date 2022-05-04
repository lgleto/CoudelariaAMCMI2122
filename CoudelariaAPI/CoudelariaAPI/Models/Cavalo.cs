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
}
package ipca.example.coudelaria.models;

import org.json.JSONObject
import java.util.*

class Cavalo {

    var cod_Cavalo           : Int     ? = null
    var nome_Cavalo          : String  ? = null
    var data_Nascimento      : Date?     = null
    var genero               : String  ? = null
    var pai                  : Int?    ? = null
    var mae                  : Int?    ? = null
    var cod_Coudelaria_Nasc  : Int?    ? = null
    var cod_Coudelaria_Resid : Int     ? = null

    constructor(
        cod_Cavalo: Int?,
        nome_Cavalo: String?,
        data_Nascimento: Date?,
        genero: String?,
        pai: Int?,
        mae: Int?,
        cod_Coudelaria_Nasc: Int?,
        cod_Coudelaria_Resid: Int?
    ) {
        this.cod_Cavalo = cod_Cavalo
        this.nome_Cavalo = nome_Cavalo
        this.data_Nascimento = data_Nascimento
        this.genero = genero
        this.pai = pai
        this.mae = mae
        this.cod_Coudelaria_Nasc = cod_Coudelaria_Nasc
        this.cod_Coudelaria_Resid = cod_Coudelaria_Resid
    }


    companion object{
        fun fromJSON(jsonObject: JSONObject) : Cavalo{
            return Cavalo(
                jsonObject["cod_Cavalo"                ] as? Int     ?,
                jsonObject["nome_Cavalo"               ] as? String  ?,
                jsonObject["data_Nascimento"           ] as? Date?    ,
                jsonObject["genero"                    ] as? String  ?,
                jsonObject["pai"                       ] as? Int?    ?,
                jsonObject["mae"                       ] as? Int?    ?,
                jsonObject["cod_Coudelaria_Nasc"       ] as? Int?    ?,
                jsonObject["cod_Coudelaria_Resid"      ] as? Int     ?,
            )
        }
    }
}

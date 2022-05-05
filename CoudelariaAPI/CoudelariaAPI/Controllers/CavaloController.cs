using CoudelariaAPI.Models;
using Microsoft.AspNetCore.Mvc;

namespace CoudelariaAPI.Controllers;


[ApiController]
[Route("[controller]")]
public class CavaloController
{
    [HttpGet(Name = "GetCavalos")]
    public IEnumerable<Cavalo> Get()
    {
        return Cavalo.GetAllItems();
    }

    // GET cavalo/5
    [HttpGet("{id}")]
    public Cavalo Get(string id)
    {
        return Cavalo.GetItem(id);
    }

    [HttpPost]
    public string Post([FromBody]Cavalo cavalo)
    {
        return Cavalo.Create(cavalo);
    }
    
    [HttpPut("{id}")]
    public string Put(string id,[FromBody]Cavalo cavalo)
    {
        return Cavalo.Update(id ,cavalo);
    }
    
    // DELETE 
    [HttpDelete("{id}")]
    public string Delete(string id)
    {
        return Cavalo.Delete( id);
    }
    
}
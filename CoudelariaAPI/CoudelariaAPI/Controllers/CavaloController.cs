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
}
using System;

public static class IpConverter
{
    public static uint IpToUInt(string ip)
    {
        if (ip == null)
            Console.WriteLine("Null IP exception");

        var parts = ip.Split('.');
        if (parts.Length != 4)
            Console.WriteLine("Wrong IP-address format");

        uint result = 0;
        foreach (var part in parts)
        {
            int octet = int.Parse(part);
            if (octet < 0 || octet > 255)
                Console.WriteLine("Octect must be in the range 0-255");

            result = (result << 8) | (uint)octet;
        }
        return result;
    }

    public static string UIntToIp(uint value)
    {
        var octets = new byte[4];
        for (int i = 3; i >= 0; i--)
        {
            octets[i] = (byte)(value & 0xFFu);
            value >>= 8;
        }
        return string.Join(".", octets);
    }

    static void Main()
    {
        uint num1 = IpToUInt("128.32.10.0");
        Console.WriteLine(num1);         // Выведет "2149583360"

        uint num2 = IpToUInt("0.0.0.255");
        Console.WriteLine(num2);         // Выведет "255"

        string ip1 = UIntToIp(2149583360);
        Console.WriteLine(ip1);          // Выведет "128.32.10.0"

        string ip2 = UIntToIp(255);
        Console.WriteLine(ip2);          // Выведет "0.0.0.255"
    }
}



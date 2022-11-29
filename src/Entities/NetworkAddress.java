package Entities;

import Application.Helper;

public class NetworkAddress {
    public String ip;
    public Integer mask;

    public NetworkAddress(String[] address) {
        this.ip = address[0].trim();
        this.mask = Integer.parseInt(address[1].trim());
    }

    public NetworkAddress(String ip, Integer mask) {
        this.ip = ip;
        this.mask = mask;
    }

    public Boolean networkAddressContainsIp(String ipToVerify) {
        Integer diffInBits = 32 - mask;
        Double hostNumber = Math.pow(2, diffInBits);

        long ipLongNetwork = Helper.ipToLong(ip);
        long ipLongToVerify = Helper.ipToLong(ipToVerify);
        long ipLongNetworkFinal = ipLongNetwork + hostNumber.longValue() - 1;
        
        if (ipLongToVerify >= ipLongNetwork && ipLongToVerify <= ipLongNetworkFinal)
            return true;
        return false;
    }
}
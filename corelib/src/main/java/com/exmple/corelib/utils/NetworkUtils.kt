package com.exmple.corelib.utils

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.support.annotation.RequiresPermission
import android.telephony.TelephonyManager
import android.text.format.Formatter
import com.exmple.corelib.base.LibApplication
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : utils about network
</pre> *
 */
object NetworkUtils {


    val isConnected: Boolean
        @RequiresPermission(ACCESS_NETWORK_STATE)
        get() {
            val info = activeNetworkInfo
            return info != null && info.isConnected
        }




    /**
     * Return whether wifi is connected.
     */
    val isWifiConnected: Boolean
        @RequiresPermission(ACCESS_NETWORK_STATE)
        get() {
            val cm = LibApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
        }


    /**
     * Return type of network.
     */
    val networkType: NetworkType
        @RequiresPermission(ACCESS_NETWORK_STATE)
        get() {
            var netType = NetworkType.NETWORK_NO
            val info = activeNetworkInfo
            if (info != null && info.isAvailable) {
                if (info.type == ConnectivityManager.TYPE_ETHERNET) {
                    netType = NetworkType.NETWORK_ETHERNET
                } else if (info.type == ConnectivityManager.TYPE_WIFI) {
                    netType = NetworkType.NETWORK_WIFI
                } else if (info.type == ConnectivityManager.TYPE_MOBILE) {
                    when (info.subtype) {

                        TelephonyManager.NETWORK_TYPE_GSM, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> netType = NetworkType.NETWORK_2G

                        TelephonyManager.NETWORK_TYPE_TD_SCDMA, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> netType = NetworkType.NETWORK_3G

                        TelephonyManager.NETWORK_TYPE_IWLAN, TelephonyManager.NETWORK_TYPE_LTE -> netType = NetworkType.NETWORK_4G
                        else -> {

                            val subtypeName = info.subtypeName
                            if (subtypeName.equals("TD-SCDMA", ignoreCase = true)
                                    || subtypeName.equals("WCDMA", ignoreCase = true)
                                    || subtypeName.equals("CDMA2000", ignoreCase = true)) {
                                netType = NetworkType.NETWORK_3G
                            } else {
                                netType = NetworkType.NETWORK_UNKNOWN
                            }
                        }
                    }
                } else {
                    netType = NetworkType.NETWORK_UNKNOWN
                }
            }
            return netType
        }

    private val activeNetworkInfo: NetworkInfo?
        @RequiresPermission(ACCESS_NETWORK_STATE)
        get() {
            val manager = LibApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return manager.activeNetworkInfo
        }


    /**
     * Return the ip address by wifi.
     */
    val ipAddressByWifi: String
        @RequiresPermission(ACCESS_WIFI_STATE)
        get() {
            @SuppressLint("WifiManagerLeak")
            val wm = LibApplication.mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return Formatter.formatIpAddress(wm.dhcpInfo.ipAddress)
        }

    /**
     * Return the gate way by wifi.
     */
    val gatewayByWifi: String
        @RequiresPermission(ACCESS_WIFI_STATE)
        get() {
            @SuppressLint("WifiManagerLeak")
            val wm = LibApplication.mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return Formatter.formatIpAddress(wm.dhcpInfo.gateway)
        }

    /**
     * Return the net mask by wifi.
     */
    val netMaskByWifi: String
        @RequiresPermission(ACCESS_WIFI_STATE)
        get() {
            @SuppressLint("WifiManagerLeak")
            val wm = LibApplication.mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return Formatter.formatIpAddress(wm.dhcpInfo.netmask)
        }

    /**
     * Return the server address by wifi.
     */
    val serverAddressByWifi: String
        @RequiresPermission(ACCESS_WIFI_STATE)
        get() {
            @SuppressLint("WifiManagerLeak")
            val wm = LibApplication.mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return Formatter.formatIpAddress(wm.dhcpInfo.serverAddress)
        }



    /**
     * Return the ip address.
     *
     * Must hold `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @param useIPv4 True to use ipv4, false otherwise.
     * @return the ip address
     */
    @RequiresPermission(INTERNET)
    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val nis = NetworkInterface.getNetworkInterfaces()
            val adds = LinkedList<InetAddress>()
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement()
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp || ni.isLoopback) {
                    continue
                }
                val addresses = ni.inetAddresses
                while (addresses.hasMoreElements()) {
                    adds.addFirst(addresses.nextElement())
                }
            }
            for (add in adds) {
                if (!add.isLoopbackAddress) {
                    val hostAddress = add.hostAddress
                    val isIPv4 = hostAddress.indexOf(':') < 0
                    if (useIPv4) {
                        if (isIPv4) {
                            return hostAddress
                        }
                    } else {
                        if (!isIPv4) {
                            val index = hostAddress.indexOf('%')
                            return if (index < 0)
                                hostAddress.toUpperCase()
                            else
                                hostAddress.substring(0, index).toUpperCase()
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }

        return ""
    }


    enum class NetworkType {
        NETWORK_ETHERNET,
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }
}

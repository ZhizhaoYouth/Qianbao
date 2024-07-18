// utils.ts
import axios from "axios";
import UAParser from "ua-parser-js";
import API from "@/api";
import { addLoginLogUsingPost } from "@/api/loginLogController";
import { message } from "ant-design-vue";

interface DeviceInfo {
  deviceType: string;
  browser: string;
  operatingSystem: string;
}

interface IpInfo {
  ipAddress: string;
  location: string;
}

const getDeviceInfo = (): DeviceInfo => {
  const parser = new UAParser();
  const userAgent = parser.getResult();
  return {
    deviceType: userAgent.device.type || "Unknown",
    browser: userAgent.browser.name || "Unknown",
    operatingSystem: userAgent.os.name || "Unknown",
  };
};

const getIpInfo = async (): Promise<IpInfo> => {
  const ipResponse = await axios.get<{ ip: string }>(
    "https://api.ipify.org?format=json"
  );
  const ipAddress = ipResponse.data.ip;

  /*  const locationResponse = await axios.get(
      `https://ipapi.co/${ipAddress}/json/`
    );*/
  /*  const locationData = locationResponse.data;
    return {
      ipAddress: ipAddress,
      location: `${locationData.city}, ${locationData.region}, ${locationData.country_name}`,
    };*/
  const locationResponse = await axios.get(`https://ip.011102.xyz/`);
  const locationData = locationResponse.data.IP;
  return {
    ipAddress: ipAddress,
    location: `${locationData.Country},${locationData.Region},${locationData.City}`,
  };
};

const logLogin = async (): Promise<void> => {
  try {
    const deviceInfo = getDeviceInfo();
    const ipInfo = await getIpInfo();

    const loginLog: API.LoginLogAddRequest = {
      browserInfo: deviceInfo.browser,
      deviceType: deviceInfo.deviceType,
      ipAddress: ipInfo.ipAddress,
      location: ipInfo.location,
      loginTime: new Date().toISOString(),
      operatingSystem: deviceInfo.operatingSystem,
    };
    const res = await addLoginLogUsingPost(loginLog);
    if (res.data.code === 0) {
      message.success("登录日志记录成功");
    } else {
      message.error("登录日志记录失败");
    }
  } catch (error) {
    console.error("登录日志记录失败:", error);
  }
};

export { logLogin };

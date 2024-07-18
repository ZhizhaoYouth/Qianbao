import axios from "axios";
import { message } from "ant-design-vue";

export const isDev = process.env.NODE_ENV === "development";

const myAxios = axios.create({
  /*  baseURL: isDev
      ? "http://localhost:8102"
      : "https://finplat-backend-3-114997-5-1327966794.sh.run.tcloudbase.com/",*/
  baseURL: "http://localhost:8102",
  timeout: 10000,
  withCredentials: true,
});

// 添加请求拦截器
myAxios.interceptors.request.use(
  function (config) {
    // 在发送请求之前做些什么
    return config;
  },
  function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 添加响应拦截器
myAxios.interceptors.response.use(
  function (response) {
    console.log(response);
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    const { data } = response;
    //未登录
    if (data.code === 40100) {
      //不是获取用户信息的请求，或者用户目前不在登录页面，跳转到登录页面
      if (
        !response.request.responseURL.includes("user/get/login") &&
        !window.location.pathname.includes("/user/login")
      ) {
        message.warning("请先登录");
        window.location.href = `/user/login?redirect=${window.location.href}`;
      }
    }
    return response;
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);

export default myAxios;

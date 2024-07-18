// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** addLoginLog POST /api/loginLog/add */
export async function addLoginLogUsingPost(
  body: API.LoginLogAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/loginLog/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteLoginLog POST /api/loginLog/delete */
export async function deleteLoginLogUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/loginLog/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getLoginLogVOById GET /api/loginLog/get/vo */
export async function getLoginLogVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getLoginLogVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLoginLogVO_>('/api/loginLog/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listLoginLogByPage POST /api/loginLog/list/page */
export async function listLoginLogByPageUsingPost(
  body: API.LoginLogQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageLoginLog_>('/api/loginLog/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listMyLoginLogVOByPage POST /api/loginLog/my/list/page/vo */
export async function listMyLoginLogVoByPageUsingPost(
  body: API.LoginLogQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageLoginLogVO_>('/api/loginLog/my/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

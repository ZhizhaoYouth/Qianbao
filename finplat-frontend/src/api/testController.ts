// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** hello GET /api/test/hello */
export async function helloUsingGet(options?: { [key: string]: any }) {
  return request<API.BaseResponseString_>('/api/test/hello', {
    method: 'GET',
    ...(options || {}),
  });
}

/** helloAdmin GET /api/test/helloAdmin */
export async function helloAdminUsingGet(options?: { [key: string]: any }) {
  return request<API.BaseResponseString_>('/api/test/helloAdmin', {
    method: 'GET',
    ...(options || {}),
  });
}

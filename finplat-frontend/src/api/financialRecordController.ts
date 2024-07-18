// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** addFinancialRecord POST /api/financialRecord/add */
export async function addFinancialRecordUsingPost(
  body: API.FinancialRecordAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/financialRecord/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteFinancialRecord POST /api/financialRecord/delete */
export async function deleteFinancialRecordUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/financialRecord/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** editFinancialRecord POST /api/financialRecord/edit */
export async function editFinancialRecordUsingPost(
  body: API.FinancialRecordEditRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/financialRecord/edit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getFinancialRecordVOById GET /api/financialRecord/get/vo */
export async function getFinancialRecordVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getFinancialRecordVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseFinancialRecordVO_>('/api/financialRecord/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listFinancialRecordByPage POST /api/financialRecord/list/page */
export async function listFinancialRecordByPageUsingPost(
  body: API.FinancialRecordQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageFinancialRecord_>('/api/financialRecord/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listMyFinancialRecordVOByPage POST /api/financialRecord/my/list/page/vo */
export async function listMyFinancialRecordVoByPageUsingPost(
  body: API.FinancialRecordQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageFinancialRecordVO_>('/api/financialRecord/my/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

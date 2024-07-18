// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** addBudget POST /api/budget/add */
export async function addBudgetUsingPost(
  body: API.BudgetAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/budget/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** checkBudgetExceed GET /api/budget/check */
export async function checkBudgetExceedUsingGet(options?: { [key: string]: any }) {
  return request<API.BaseResponseListString_>('/api/budget/check', {
    method: 'GET',
    ...(options || {}),
  });
}

/** deleteBudget POST /api/budget/delete */
export async function deleteBudgetUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/budget/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** editBudget POST /api/budget/edit */
export async function editBudgetUsingPost(
  body: API.BudgetEditRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/budget/edit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getBudgetVOById GET /api/budget/get/vo */
export async function getBudgetVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getBudgetVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBudgetVO_>('/api/budget/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listBudgetByPage POST /api/budget/list/page */
export async function listBudgetByPageUsingPost(
  body: API.BudgetQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageBudget_>('/api/budget/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listMyBudgetVOByPage POST /api/budget/my/list/page/vo */
export async function listMyBudgetVoByPageUsingPost(
  body: API.BudgetQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageBudgetVO_>('/api/budget/my/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

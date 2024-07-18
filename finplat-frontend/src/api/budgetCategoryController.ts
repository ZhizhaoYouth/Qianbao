// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** addBudgetCategory POST /api/budgetCategory/add */
export async function addBudgetCategoryUsingPost(
  body: API.BudgetCategoryAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/budgetCategory/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteBudgetCategory POST /api/budgetCategory/delete */
export async function deleteBudgetCategoryUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/budgetCategory/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getBudgetCategoryVOById GET /api/budgetCategory/get/vo */
export async function getBudgetCategoryVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getBudgetCategoryVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBudgetCategoryVO_>('/api/budgetCategory/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listBudgetCategoryByPage POST /api/budgetCategory/list/page */
export async function listBudgetCategoryByPageUsingPost(
  body: API.BudgetCategoryQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageBudgetCategory_>('/api/budgetCategory/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listMyBudgetCategoryVOByPage POST /api/budgetCategory/my/list/page/vo */
export async function listMyBudgetCategoryVoByPageUsingPost(
  body: API.BudgetCategoryQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageBudgetCategoryVO_>('/api/budgetCategory/my/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

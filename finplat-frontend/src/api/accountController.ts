// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** addAccount POST /api/account/add */
export async function addAccountUsingPost(
  body: API.AccountAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/account/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getAllMyBalance GET /api/account/balance */
export async function getAllMyBalanceUsingGet(options?: { [key: string]: any }) {
  return request<API.BaseResponseBigdecimal_>('/api/account/balance', {
    method: 'GET',
    ...(options || {}),
  });
}

/** checkAccount POST /api/account/check */
export async function checkAccountUsingPost(
  body: API.CardRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBankInfo_>('/api/account/check', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteAccount POST /api/account/delete */
export async function deleteAccountUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/account/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getAccountDetailsPages POST /api/account/details */
export async function getAccountDetailsPagesUsingPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAccountDetailsPagesUsingPOSTParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseAccountDetailsVO_>('/api/account/details', {
    method: 'POST',
    params: {
      // budgetCurrent has a default value: 1
      budgetCurrent: '1',
      // budgetSize has a default value: 10
      budgetSize: '10',

      // recordCurrent has a default value: 1
      recordCurrent: '1',
      // recordSize has a default value: 10
      recordSize: '10',
      ...params,
    },
    ...(options || {}),
  });
}

/** editAccount POST /api/account/edit */
export async function editAccountUsingPost(
  body: API.AccountEditRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/account/edit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getAccountVOById GET /api/account/get/vo */
export async function getAccountVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAccountVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseAccountVO_>('/api/account/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listAccountByPage POST /api/account/list/page */
export async function listAccountByPageUsingPost(
  body: API.AccountQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageAccount_>('/api/account/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listMyAccountVOByPage POST /api/account/my/list/page/vo */
export async function listMyAccountVoByPageUsingPost(
  body: API.AccountQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageAccountVO_>('/api/account/my/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** doAccountReview POST /api/account/review */
export async function doAccountReviewUsingPost(
  body: API.ReviewRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/account/review', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

// @ts-ignore
/* eslint-disable */
import request from '@/request';

/** getReport POST /api/report/generate */
export async function getReportUsingPost(
  body: API.ReportQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseReportVO_>('/api/report/generate', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

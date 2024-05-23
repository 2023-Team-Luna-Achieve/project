import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // 리다이렉트를 위해 사용

// axios 인스턴스 생성
const instance = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,
});

// 요청 인터셉터 추가: 모든 요청에 자동으로 액세스 토큰 추가
instance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      config.headers['Authorization'] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// 응답 인터셉터 추가: 401 응답을 받을 경우 리프레시 토큰으로 액세스 토큰 갱신
instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      const refreshToken = localStorage.getItem('refreshToken');
      if (!refreshToken) {
        console.log('No refresh token available, redirecting to login.');
        window.location.href = '/login';
        return Promise.reject(error);
      }
      try {
        const res = await instance.post(
          '/api/user/refresh',
          {},
          {
            headers: {
              'Refresh-Token': refreshToken,
            },
          },
        );
        const { accessToken } = res.data;
        if (accessToken) {
          localStorage.setItem('accessToken', accessToken);
          originalRequest.headers['Authorization'] = `Bearer ${accessToken}`;
          return instance(originalRequest);
        }
      } catch (refreshError) {
        console.error('Failed to refresh token:', refreshError);
        window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }
    return Promise.reject(error);
  },
);

export default instance;

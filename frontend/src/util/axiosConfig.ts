import axios from 'axios';

// axios 인스턴스 생성
const instance = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,
});

// 응답 인터셉터를 추가하여 토큰 만료 시 자동으로 액세스 토큰을 갱신
instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        // 서버에서 새 액세스 토큰을 요청
        const refreshToken = localStorage.getItem('refreshToken');
        if (refreshToken) {
          const res = await instance.post('/api/user/refresh', { refreshToken });
          const newAccessToken = res.data.accessToken;
          if (newAccessToken) {
            // 새 토큰을 로컬 스토리지에 저장하고 요청 헤더를 업데이트
            localStorage.setItem('accessToken', newAccessToken);
            instance.defaults.headers.common['Authorization'] = `Bearer ${newAccessToken}`;
            originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
            // 원래 요청을 재시도
            return instance(originalRequest);
          }
        }
      } catch (refreshError) {
        console.error('Unable to refresh the token:', refreshError);
        // window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }
    return Promise.reject(error);
  },
);

export default instance;
